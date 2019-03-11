import CarPark.PayStation;
import CarPark.PayStationHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class PayStationClient extends JFrame {
    String[] durations = {"", "1 Hour", "2 Hours", "3 Hours", "5 Hours", "6 Hours", "7 Hours", "8 Hours", "9 Hours", "10 Hours", "11 Hours", "12 Hours", "Custom"};

    public static JTextField txtReg;
    public static JTextField txtCustDuration;

    private JLabel lblReg;
    private JLabel lblDuration;
    private JLabel lblCustDuration;
    private JLabel lblCost;

    public static JButton btnPay;
    public static JComboBox<String> cbbDuration;

    public static short duration;

    public PayStationClient() {
        JFrame frame = new JFrame("Pay Station");
        JPanel panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        lblReg = new JLabel("Enter Registration:");
        txtReg = new JTextField(10);
        lblDuration = new JLabel("Select Duration:");
        cbbDuration = new JComboBox<>(durations);
        lblCustDuration = new JLabel("Enter Duration as whole number:");
        txtCustDuration = new JTextField(10);
        lblCost = new JLabel("Cost: ");
        btnPay = new JButton("Pay");

        panel.add(lblReg);
        panel.add(txtReg);
        panel.add(lblDuration);
        panel.add(cbbDuration);
        panel.add(lblCustDuration);
        panel.add(txtCustDuration);
        panel.add(lblCost);
        panel.add(btnPay);
        frame.add(panel);

        lblCustDuration.setVisible(false);
        txtCustDuration.setVisible(false);

        frame.setVisible(true);

        cbbDuration.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String item = cbbDuration.getSelectedItem().toString();
                if (item == "Custom") {
                    lblCost.setText("Cost: ");
                    lblCustDuration.setVisible(true);
                    txtCustDuration.setVisible(true);
                } else {
                    if (!item.isEmpty()) {
                        lblCustDuration.setVisible(false);
                        txtCustDuration.setVisible(false);
                        item = item.replaceAll("[^\\d.]", "");
                        duration = Short.parseShort(item);
                        lblCost.setText("Cost: £" + duration);
                    } else {
                        lblCost.setText("Cost: ");
                    }
                }
            }
        });


        txtCustDuration.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               String duration = txtCustDuration.getText();
               // TODO: Check if string is an integer
            }
        });

    }


    public static void main(String[] args) {
        PayStationClient payClient = new PayStationClient();

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

            String name = "countName";
            PayStation payStation = PayStationHelper.narrow(nameService.resolve_str(name));



            btnPay.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO: Validation on registration.


                    LocalDateTime currDate = LocalDateTime.now();
                    CarPark.Date date = new CarPark.Date();
                    date.days = currDate.getDayOfMonth();
                    date.months = currDate.getMonthValue();
                    date.years = currDate.getYear();

                    CarPark.Time time = new CarPark.Time();
                    time.hours = currDate.getHour();
                    time.minutes = currDate.getMinute();
                    time.seconds = currDate.getSecond();

                    payStation.pay(txtReg.getText(), date, time, duration);
                }
            });

        } catch (Exception e) {
            System.err.println("Exception");
            System.err.println(e);
        }
    }
}