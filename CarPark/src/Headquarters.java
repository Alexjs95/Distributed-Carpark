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
import java.util.ArrayList;

public class Headquarters extends JFrame {
    public static JTable tblMachines;
    public static DefaultTableModel model;
    public static JButton btnRefresh;
    public static JButton btnTurnOn;
    public static JButton btnTurnOff;
    public static JButton btnReset;


    public static int numberOfServers;
    static ArrayList<String> servers = new ArrayList<String>();
    public static String hqName;

    public static int row;
    public static int col;


    public Headquarters() {
        JFrame frame = new JFrame("Headquarters");
        JPanel panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,600);

        String[] columnNames = {"Local Server", "Connected Machine", "Machine Type","Machine Enabled"};

        model = new DefaultTableModel(null, columnNames);
        tblMachines = new JTable(model);
        btnRefresh = new JButton("Refresh");
        btnTurnOn = new JButton("Turn on");
        btnTurnOff = new JButton("Turn off");
        btnReset = new JButton("Reset");

        JScrollPane sp = new JScrollPane(tblMachines);
        panel.add(sp);
        panel.add(btnRefresh);
        panel.add(btnTurnOn);
        panel.add(btnTurnOff);
        panel.add(btnReset);

        frame.add(panel);
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        Headquarters headquarters = new Headquarters();

        for (int i = 0; i < args.length; i ++) {
            if (args[i].equals("-name")) {
                hqName = args[i + 1];
            } else if (args[i].equals("-localservers")) {
                numberOfServers = Integer.parseInt((args[i + 1]));
                for (int j = i + 2; j <= i + 1 + numberOfServers; j++) {
                    servers.add(args[j]);
                }
            }
        }

        System.out.println("hq nameL " +hqName);

        System.out.println("num of servers" + numberOfServers);

        for (int i = 0; i <servers.size(); i++) {
            System.out.println("sever " + i + " is named " + servers.get(i));
        }


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
            org.omg.CORBA.Object nameServiceObj = orb.resolve_initial_references ("NameService");
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

            for (int i = 0; i < servers.size(); i++) {
                // bind the Count object in the Naming service
                String serverName = servers.get(i);
                NameComponent[] lSeverName = nameService.to_name(serverName);
                nameService.rebind(lSeverName, crefServer);
            }


            // Register HQ as a client

            // Get a reference to the Naming service
            org.omg.CORBA.Object nameServiceObjHQ = orb.resolve_initial_references("NameService");
            if (nameServiceObjHQ == null) {
                System.out.println("nameServiceObjHQ = null");
                return;
            }

            // Use NamingContextExt instead of NamingContext. This is
            // part of the Interoperable naming Service.
            NamingContextExt nameServiceHQ = NamingContextExtHelper.narrow(nameServiceObjHQ);
            if (nameServiceHQ == null) {
                System.out.println("nameServiceHQ = null");
                return;
            }


            btnRefresh.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String name = "HQ";
                        CompanyHQ companyHQ = CompanyHQHelper.narrow(nameServiceHQ.resolve_str(name));

                        model.setNumRows(0);
                        for(int i = 0; i < HeadquartersImpl.servers.size(); i++) {
                            for(int j = 0; j < companyHQ.return_machines().length; j++) {
                                if (companyHQ.return_machines()[j].enabled == true) {
                                    model.addRow(new String[]{HeadquartersImpl.servers.get(i).location, companyHQ.return_machines()[j].machine_name,companyHQ.return_machines()[j].machine_type, "true"});
                                } else {
                                    model.addRow(new String[]{HeadquartersImpl.servers.get(i).location, companyHQ.return_machines()[j].machine_name,companyHQ.return_machines()[j].machine_type, "false"});
                                }
                            }
                        }
                    } catch (Exception e1 ) {
                        e1.printStackTrace();
                    }
                }
            });


            tblMachines.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    row = tblMachines.getSelectedRow();
                    col = tblMachines.getSelectedColumn();
                    System.out.println("current row: " + tblMachines.getValueAt(row, col));
                }
            });


            btnTurnOn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String machine_name = tblMachines.getValueAt(row, 1).toString();
                    String machine_type = tblMachines.getValueAt(row, 2).toString();
                    try {
                        CompanyHQ hq = CompanyHQHelper.narrow(nameServiceHQ.resolve_str(machine_name + "HQ"));
                        hq.turn_machine_on(machine_type);
                    } catch (Exception e1) {
                        System.out.println(e1);
                    }
                }
            });

            btnTurnOff.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String machine_name = tblMachines.getValueAt(row, 1).toString();
                    String machine_type = tblMachines.getValueAt(row, 2).toString();
                    try {
                        CompanyHQ hq = CompanyHQHelper.narrow(nameServiceHQ.resolve_str(machine_name + "HQ"));
                        hq.turn_machine_off(machine_type);
                    } catch (Exception e1) {
                        System.out.println(e1);
                    }
                }
            });

            btnReset.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String machine_name = tblMachines.getValueAt(row, 1).toString();
                    String machine_type = tblMachines.getValueAt(row, 2).toString();
                    try {
                        CompanyHQ hq = CompanyHQHelper.narrow(nameServiceHQ.resolve_str(machine_name + "HQ"));
                        hq.reset_machine(machine_type);
                    } catch (Exception e1) {
                        System.out.println(e1);
                    }
                }
            });

            //  wait for invocations from clients
            orb.run();

        } catch(Exception e) {
            e.printStackTrace();
            System.err.println(e);
        }
    }
}