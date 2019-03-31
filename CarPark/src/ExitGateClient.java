import CarPark.ExitGate;
import CarPark.ExitGateHelper;
import CarPark.LocalServer;
import CarPark.LocalServerHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class ExitGateClient extends JFrame {
    public static JTextField txtReg;
    public static JButton btnSubmit;
    private JLabel lblReg;

    public static String serverName;
    public static String exitGateName;

    public ExitGateClient() {
        JFrame frame = new JFrame("Exit Gate");
        JPanel panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        lblReg = new JLabel("Enter Registration:");
        txtReg = new JTextField(10);
        btnSubmit = new JButton("Submit");

        panel.add(lblReg);
        panel.add(txtReg);
        panel.add(btnSubmit);
        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        ExitGateClient exitGateClient = new ExitGateClient();

        for (int i = 0; i < args.length; i ++) {
            // Gets name of exit gate from arguments
            if (args[i].equals("-name")) {
                exitGateName = args[i + 1];
            } else if (args[i].equals("-server")) {
                // gets name of server to connect to.
                serverName = args[i + 1];
            }
        }

        try {
            // Initialize the ORB
            System.out.println("Initializing the ORB");
            ORB orb = ORB.init(args, null);

            // Get a reference to the Naming service
            org.omg.CORBA.Object nameServiceObj = orb.resolve_initial_references("NameService");
            if (nameServiceObj == null) {
                System.out.println("nameServiceObj = null");
                return;
            }

            // Use NamingContextExt instead of NamingContext. This is
            // part of the Interoperable naming Service.
            NamingContextExt nameService = NamingContextExtHelper.narrow(nameServiceObj);
            if (nameService == null) {
                System.out.println("nameService = null");
                return;
            }

            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            ExitGateImpl exitImpl = new ExitGateImpl();

            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(exitImpl);

            String stringified_ior = orb.object_to_string(ref);

            ExitGate cref = ExitGateHelper.narrow(ref);

            // bind the exit gate object in the Naming service
            NameComponent[] eName = nameService.to_name(exitGateName);
            nameService.rebind(eName, cref);

            LocalServer localServer = LocalServerHelper.narrow(nameService.resolve_str(serverName));

            exitImpl.register_gate(exitGateName, stringified_ior, localServer);


            btnSubmit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String registration;

                    if (exitImpl.enabled) {
                        LocalDateTime currDate = LocalDateTime.now();

                        CarPark.Date date = new CarPark.Date();
                        date.days = currDate.getDayOfMonth();
                        date.months = currDate.getMonthValue();
                        date.years = currDate.getYear();

                        CarPark.Time time = new CarPark.Time();
                        time.hours = currDate.getHour();
                        time.minutes = currDate.getMinute();
                        time.seconds = currDate.getSecond();

                        registration = txtReg.getText();

                        if (!registration.isEmpty()) {
                            exitImpl.vehicle_exited(date, time, txtReg.getText());
                            JOptionPane.showMessageDialog(null, "Vehicle Exited", "Information", JOptionPane.INFORMATION_MESSAGE);
                            txtReg.setText("");
                        } else {
                            JOptionPane.showMessageDialog(null, "Please enter a vehicle registration", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Exit Gate unavailable", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            orb.run();
        } catch (Exception e) {
            System.err.println("Exit Gate Exception");
            System.err.println(e);
        }
    }
}
