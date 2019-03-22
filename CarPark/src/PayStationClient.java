import CarPark.Date;
import CarPark.PayStation;
import CarPark.PayStationHelper;
import CarPark.Time;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;

public class PayStationClient extends JFrame {
    String[] durations = {"", "1 Hour", "2 Hours", "3 Hours", "5 Hours", "6 Hours", "7 Hours", "8 Hours", "9 Hours", "10 Hours", "11 Hours", "12 Hours", "Custom"};
    public static final double COSTPERHOUR = 1.00;
    public static final String OPERATION = "Paid";


    public static JTextField txtReg;
    public static JTextField txtCustDuration;
    public static JTextArea txtTicket;

    private JLabel lblReg;
    private JLabel lblDuration;
    private JLabel lblCustDuration;
    private JLabel lblCost;

    public static JButton btnPay;
    public static JButton btnReset;

    public static JComboBox<String> cbbDuration;

    public static short duration;
    public static double cost;

    public static String payStationName;


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
        txtTicket = new JTextArea(5, 10);
        btnReset = new JButton("Next Customer");

        panel.add(lblReg);
        panel.add(txtReg);
        panel.add(lblDuration);
        panel.add(cbbDuration);
        panel.add(lblCustDuration);
        panel.add(txtCustDuration);
        panel.add(lblCost);
        panel.add(btnPay);
        panel.add(txtTicket);
        panel.add(btnReset);
        frame.add(panel);

        lblCustDuration.setVisible(false);
        txtCustDuration.setVisible(false);
        txtTicket.setVisible(false);
        btnReset.setVisible(false);

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
                        cost = duration * COSTPERHOUR;
                        lblCost.setText("Cost: £" + cost);
                        btnPay.setEnabled(true);
                    } else {
                        lblCost.setText("Cost: ");
                        btnPay.setEnabled(false);
                    }
                }
            }
        });

        txtCustDuration.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String custDuration = txtCustDuration.getText();

                try {
                    duration = Short.parseShort(custDuration);
                    if (duration > 48) {        // limit duration to 48 hours to not exceed max short value.
                        JOptionPane.showMessageDialog(panel,"You are not permitted to park for longer than 48 Hours");
                        btnPay.setEnabled(false);
                        lblCost.setText("Cost: INVALID");

                    } else {
                        cost = duration * COSTPERHOUR;
                        lblCost.setText("Cost: £" + cost);
                        btnPay.setEnabled(true);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel,"Custom duration not whole number");
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
    }


    public static void main(String[] args) {
        PayStationClient payClient = new PayStationClient();

        for (int i = 0; i < args.length; i ++) {
            if ((!args[i].equals("-ORBInitialPort")) && (!args[i].matches("^[0-9]*$"))) {
                payStationName = args[i];
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

            String name = "PayStation";
            org.omg.CORBA.Object obj = nameService.resolve_str(name);
            PayStation payStation = PayStationHelper.narrow(nameService.resolve_str(name));


            // get reference to rootpoa & activate the POAManager
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // create servant and register it with the ORB
            PayStationImpl payImpl = new PayStationImpl();

            // Get the 'stringified IOR'
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(payImpl);
            String stringified_ior = orb.object_to_string(ref);

            payStation.register_station(payStationName, stringified_ior);


            btnPay.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO: Validation on registration.
                    String reg = txtReg.getText();

                    LocalDateTime currDate = LocalDateTime.now();
                    Date date = new CarPark.Date();
                    date.days = currDate.getDayOfMonth();
                    date.months = currDate.getMonthValue();
                    date.years = currDate.getYear();

                    Time time = new CarPark.Time();
                    time.hours = currDate.getHour();
                    time.minutes = currDate.getMinute();
                    time.seconds = currDate.getSecond();

                    System.out.println("client Reg: " + reg);

                    boolean paid = payStation.pay(reg, date, time, duration, cost);

                    if (paid == true) {
                        System.out.println("Paid");


                        String directoryName = "Tickets";
                        File directory = new File(directoryName);

                        if (! directory.exists()) {
                            directory.mkdir();
                        }

                        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                                new FileOutputStream(directoryName + "/" + reg + ".txt"), "utf-8"))) {
                            writer.write("Car Registration: " + reg);
                            ((BufferedWriter) writer).newLine();
                            writer.write("Entered at: " + getDate(date, time));
                            ((BufferedWriter) writer).newLine();
                            writer.write("Paid: £" + duration );
                            ((BufferedWriter) writer).newLine();
                            writer.write("Ticket expiry: " + currDate.plusHours(duration));

                            writer.close();

                            txtTicket.setVisible(true);
                            btnReset.setVisible(true);

                            try {
                                File file = new File(directoryName + "/" + reg + ".txt");
                                BufferedReader in = new BufferedReader(new FileReader(file));
                                String str;
                                while ((str = in.readLine()) != null) {
                                    txtTicket.append(str + "\n");

                                }
                            }catch (Exception e1) {
                                e1.printStackTrace();
                            }

                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }

                    } else {
                        // TODO: Error message
                    }
                }
            });

            btnReset.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    reset();
                }
            });

        } catch (Exception e) {
            System.out.println(e);
            System.err.println("Paystation Exception");
        }
    }

    public static String getDate(Date date, Time time) {
        String dateTime;
        dateTime = date.days + "-" + date.months + "-" + date.years + " " + time.hours + ":" + time.minutes + ":" + time.seconds;
        return dateTime;
    }

    public static void reset() {
        txtReg.setText("");
        cbbDuration.setSelectedIndex(0);
        txtCustDuration.setText("");
        txtTicket.selectAll();
        txtTicket.replaceSelection("");
        txtTicket.setVisible(false);
        btnReset.setVisible(false);
    }
}