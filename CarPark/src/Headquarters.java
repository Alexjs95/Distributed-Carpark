import CarPark.*;
import CarPark.LocalServer;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class Headquarters extends JFrame {
    JTable tblMachines;
    public static DefaultTableModel model;
    public static JButton btnRefresh;


    public Headquarters() {
        JFrame frame = new JFrame("Headquarters");
        JPanel panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);

        String[] columnNames = {"Local Server", "LS IOR"};

        model = new DefaultTableModel(null, columnNames);
        tblMachines = new JTable(model);
        btnRefresh = new JButton("Refresh");

        JScrollPane sp = new JScrollPane(tblMachines);
        panel.add(sp);
        panel.add(btnRefresh);

        frame.add(panel);
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        Headquarters headquarters = new Headquarters();



        try {
            // Initialize the ORB
            ORB orb = ORB.init(args, null);

            // get reference to rootpoa & activate the POAManager
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // Create the Entry servant object
            LocalServerImpl localServer = new LocalServerImpl();

            // get object reference from the servant
            org.omg.CORBA.Object serverRef = rootpoa.servant_to_reference(localServer);
            LocalServer crefServer = LocalServerHelper.narrow(serverRef);


            // Get a reference to the Naming service
            org.omg.CORBA.Object nameServiceObj =
                    orb.resolve_initial_references ("NameService");
            if (nameServiceObj == null) {
                System.out.println("nameServiceObj = null");
                return;
            }

            // Use NamingContextExt which is part of the Interoperable
            // Naming Service (INS) specification.
            NamingContextExt nameService = NamingContextExtHelper.narrow(nameServiceObj);
            if (nameService == null) {
                System.out.println("nameService = null");
                return;
            }

            // bind the Local server object in the Naming service
            String serverName = "Local Server";
            NameComponent[] lSeverName = nameService.to_name(serverName);
            nameService.rebind(lSeverName, crefServer);

            btnRefresh.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.setNumRows(0);
                    for(int i = 0; i < HeadquartersImpl.servers.size(); i++) {
                        for(int j = 0; j < LocalServerImpl.machines.size(); j++) {
                            model.addRow(new String[]{HeadquartersImpl.servers.get(i).location, LocalServerImpl.machines.get(j).machine_name });
                        }

                    }
                }
            });

            //  wait for invocations from clients
            orb.run();

        } catch(Exception e) {
            System.err.println(e);
        }


    }
}
