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

public class ExitGateClient extends JFrame {
    public static JFrame frame;
    public static JTextField txtReg;
    public static JButton btnSubmit;
    private JLabel lblReg;
    public static JLabel lblServer;

    public static String serverName;
    public static String exitGateName;

    public ExitGateClient() {
        frame = new JFrame("Exit Gate");
        JPanel panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,150);
        lblReg = new JLabel("Enter Registration:");
        txtReg = new JTextField(10);
        btnSubmit = new JButton("Submit");
        lblServer = new JLabel("Connected to Server: ");

        panel.add(lblReg);
        panel.add(txtReg);
        panel.add(btnSubmit);
        panel.add(Box.createHorizontalStrut(100));
        panel.add(lblServer);

        frame.add(panel);
        frame.setResizable(false);
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

            // Register exit gate with local server
            exitImpl.register_gate(exitGateName, stringified_ior, localServer);
            lblServer.setText("Connected to Server: " + serverName);
            frame.setTitle(exitGateName);

            btnSubmit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String registration;

                    if (exitImpl.enabled) {
                        registration = txtReg.getText();

                        if (!registration.isEmpty()) {
                            // inform local server of vehicle exiting car park
                            boolean exited = exitImpl.vehicle_exited(registration);
                            if (exited) {
                                JOptionPane.showMessageDialog(frame, "Vehicle exited car park.", "Success", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(frame, "Vehicle is not in the car park.", "error", JOptionPane.ERROR_MESSAGE);
                            }

                            txtReg.setText("");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Please enter a vehicle registration.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Exit Gate unavailable.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            orb.run();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Exit Gate " + exitGateName + " cannot connect to " + serverName, "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Exit Gate Exception");
            System.err.println(e);
        }
    }
}
