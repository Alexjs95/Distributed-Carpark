import CarPark.Date;
import CarPark.EntryGate;
import CarPark.EntryGateHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class EntryGateClient extends JFrame {
    public static JTextField txtReg;
    public static JButton btnSubmit;
    private JLabel lblReg;


    public EntryGateClient() {
        JFrame frame = new JFrame("Entry Gate");
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
        EntryGateClient gateClient = new EntryGateClient();

        try {
            // Initialize the ORB
            System.out.println("Initializing the ORB");
            ORB orb = ORB.init(args, null);

            // Get a reference to the Naming service
            org.omg.CORBA.Object nameServiceObj =
                    orb.resolve_initial_references("NameService");
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

            String name = "EntryGate";
            EntryGate gate = EntryGateHelper.narrow(nameService.resolve_str(name));


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

                    gate.vehicle_entered(date, time, txtReg.getText(), "Entered");
                }
            });

        } catch (Exception e) {
            System.err.println("Exception");
            System.err.println(e);
        }
    }
}

