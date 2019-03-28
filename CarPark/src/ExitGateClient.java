import CarPark.CompanyHQ;
import CarPark.CompanyHQHelper;
import CarPark.ExitGate;
import CarPark.ExitGateHelper;
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
        ExitGateClient gateClient = new ExitGateClient();

        for (int i = 0; i < args.length; i ++) {
            if (args[i].equals("-name")) {
                exitGateName = args[i + 1];
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

            String name = exitGateName;
            ExitGate gate = ExitGateHelper.narrow(nameService.resolve_str(name));



            // get reference to rootpoa & activate the POAManager
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // create servant and register it with the ORB
            ExitGateImpl exitImpl = new ExitGateImpl();

            // Get the 'stringified IOR'
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(exitImpl);
            String stringified_ior = orb.object_to_string(ref);

            gate.register_gate(exitGateName, stringified_ior);



            // Get a reference to the Naming service
            org.omg.CORBA.Object nameServiceObjHQ = orb.resolve_initial_references("NameService");
            if (nameServiceObjHQ == null) {
                System.out.println("nameServiceObj = null");
                return;
            }

            // Use NamingContextExt instead of NamingContext. This is
            // part of the Interoperable naming Service.
            NamingContextExt nameServiceHQ = NamingContextExtHelper.narrow(nameServiceObjHQ);
            if (nameServiceHQ == null) {
                System.out.println("nameService = null");
                return;
            }

            // Create the HQ servant object
            HeadquartersImpl hq = new HeadquartersImpl();

            // get object reference from the servant
            org.omg.CORBA.Object hqRef = rootpoa.servant_to_reference(hq);
            CompanyHQ crefHq = CompanyHQHelper.narrow(hqRef);

            NameComponent[] hqName = nameServiceHQ.to_name(exitGateName + "HQ");
            nameServiceHQ.rebind(hqName, crefHq);


            btnSubmit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LocalDateTime currDate = LocalDateTime.now();

                    CarPark.Date date = new CarPark.Date();
                    date.days = currDate.getDayOfMonth();
                    date.months = currDate.getMonthValue();
                    date.years = currDate.getYear();

                    CarPark.Time time = new CarPark.Time();
                    time.hours = currDate.getHour();
                    time.minutes = currDate.getMinute();
                    time.seconds = currDate.getSecond();

                    gate.vehicle_exited(date, time, txtReg.getText());
                }
            });

        } catch (Exception e) {
            System.err.println("Exit Gate Exception");
            System.err.println(e);
        }
    }
}
