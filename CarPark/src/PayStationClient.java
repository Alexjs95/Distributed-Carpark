import CarPark.*;
import CarPark.LocalServer;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
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
import java.time.format.DateTimeFormatter;

public class PayStationClient extends JFrame {
    String[] durations = {"", "1 Hour", "2 Hours", "3 Hours", "5 Hours", "6 Hours", "7 Hours", "8 Hours", "9 Hours", "10 Hours", "11 Hours", "12 Hours", "Custom"};
    public static double costPerHour;

    public static JFrame frame;
    public static JTextField txtReg;
    public static JTextField txtCustDuration;
    public static JTextArea txtTicket;

    private JLabel lblReg;
    private JLabel lblDuration;
    private static JLabel lblCustDuration;
    private static JLabel lblCost;
    public static JLabel lblServer;

    public static JButton btnPay;
    public static JButton btnReset;

    public static JComboBox<String> cbbDuration;

    public static short duration = 0;
    public static double cost;

    public static String payStationName;
    public static String serverName;

    public PayStationClient() {
        frame = new JFrame("Pay Station");
        JPanel panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(290, 300);
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
        lblServer = new JLabel("Connected to Server: ");

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
        panel.add(Box.createHorizontalStrut(100));
        panel.add(lblServer);

        frame.add(panel);

        lblCustDuration.setVisible(false);
        txtCustDuration.setVisible(false);
        txtTicket.setVisible(false);
        btnReset.setVisible(false);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        PayStationClient payClient = new PayStationClient();

        for (int i = 0; i < args.length; i ++) {
            // Gets name of entry gate from arguments
            if (args[i].equals("-name")) {
                payStationName = args[i + 1];
                payStationName = args[i + 1];
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

            PayStationImpl payImpl = new PayStationImpl();

            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(payImpl);

            String stringified_ior = orb.object_to_string(ref);

            PayStation cref = PayStationHelper.narrow(ref);

            // bind the pay station object in the Naming service
            NameComponent[] pName = nameService.to_name(payStationName);
            nameService.rebind(pName, cref);

            LocalServer localServer = LocalServerHelper.narrow(nameService.resolve_str(serverName));

            payImpl.register_station(payStationName, stringified_ior, localServer);
            lblServer.setText("Connected to Server: " + serverName);
            costPerHour = localServer.cost_per_hour();

            frame.setTitle(payStationName);

            cbbDuration.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String item = cbbDuration.getSelectedItem().toString();
                    costPerHour = localServer.cost_per_hour();

                    if (item == "Custom") {
                        lblCost.setText("Cost: ");
                        btnPay.setEnabled(false);
                        lblCustDuration.setVisible(true);
                        txtCustDuration.setVisible(true);
                    } else {
                        if (!item.isEmpty()) {
                            lblCustDuration.setVisible(false);
                            txtCustDuration.setVisible(false);
                            item = item.replaceAll("[^\\d.]", "");
                            duration = Short.parseShort(item);
                            cost = duration * costPerHour;
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
                        if (duration > 24) {        // limit duration to 24 hours to not exceed max short value.
                            JOptionPane.showMessageDialog(frame,"You are not permitted to park for longer than 24 Hours");
                            btnPay.setEnabled(false);
                            lblCost.setText("Cost: INVALID");

                        } else {
                            cost = duration * costPerHour;
                            lblCost.setText("Cost: £" + cost);
                            btnPay.setEnabled(true);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame,"Custom duration not whole number");
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    String custDuration = txtCustDuration.getText();

                    try {
                        duration = Short.parseShort(custDuration);
                        if (duration > 24) {        // limit duration to 24 hours to not exceed max short value.
                            JOptionPane.showMessageDialog(frame,"You are not permitted to park for longer than 24 Hours");
                            btnPay.setEnabled(false);
                            lblCost.setText("Cost: INVALID");

                        } else {
                            cost = duration * costPerHour;
                            lblCost.setText("Cost: £" + cost);
                            btnPay.setEnabled(true);
                        }
                    } catch (NumberFormatException ex) {
                        lblCost.setText("Cost: ");
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {

                }
            });


            btnPay.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String registration;

                    if (payImpl.enabled) {
                        registration = txtReg.getText();

                        LocalDateTime currDate = LocalDateTime.now();
                        Date date = new CarPark.Date();
                        date.days = currDate.getDayOfMonth();
                        date.months = currDate.getMonthValue();
                        date.years = currDate.getYear();

                        Time time = new CarPark.Time();
                        time.hours = currDate.getHour();
                        time.minutes = currDate.getMinute();
                        time.seconds = currDate.getSecond();

                        if (registration.isEmpty()) {
                            JOptionPane.showMessageDialog(frame, "Please enter a vehicle registration", "Error", JOptionPane.ERROR_MESSAGE);
                        } else if (duration == 0) {
                            JOptionPane.showMessageDialog(frame, "Please select a duration from the combo box or enter a custom duration.", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            boolean paid = payImpl.pay(registration, duration, cost);

                            if (paid == true) {
                                String directoryName = serverName + " Tickets";
                                File directory = new File(directoryName);

                                if (! directory.exists()) {
                                    directory.mkdir();
                                }

                                try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                                        new FileOutputStream(directoryName + "/" + registration + ".txt"), "utf-8"))) {
                                    LocalDateTime enteredTime = getDateTime(date, time);

                                    writer.write("Car Registration: " + registration);
                                    ((BufferedWriter) writer).newLine();
                                    writer.write("Entered at: " + enteredTime);
                                    ((BufferedWriter) writer).newLine();
                                    writer.write("Paid: £" + duration );
                                    ((BufferedWriter) writer).newLine();
                                    writer.write("Ticket expiry: " + enteredTime.plusHours(duration));

                                    writer.close();

                                    txtTicket.setVisible(true);
                                    btnReset.setVisible(true);

                                    try {
                                        File file = new File(directoryName + "/" + registration + ".txt");
                                        BufferedReader in = new BufferedReader(new FileReader(file));
                                        String str;
                                        while ((str = in.readLine()) != null) {
                                            txtTicket.append(str + "\n");
                                        }
                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                    }

                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                                btnPay.setEnabled(false);
                                JOptionPane.showMessageDialog(frame, "Vehicle paid for.", "Success", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(frame, "Vehicle already paid for or not in car park.", "Error", JOptionPane.ERROR_MESSAGE);
                                reset();
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Pay Station unavailable.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            btnReset.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    reset();
                }
            });

            orb.run();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Pay Station " + payStationName + " cannot connect to " + serverName, "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println(e);
            System.err.println("Paystation Exception");
        }
    }

    public static LocalDateTime getDateTime(Date date, Time time) {
        String temp = date.years + "-" + date.months + "-" + date.days + " " + time.hours + ":" + time.minutes + ":" + time.seconds;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d H:m:s");
        LocalDateTime dateTime = LocalDateTime.parse(temp, formatter);
        return dateTime;
    }

    public static void reset() {
        duration = 0;
        cost = 0;
        txtReg.setText("");
        cbbDuration.setSelectedIndex(0);
        txtCustDuration.setText("");
        txtTicket.selectAll();
        txtTicket.replaceSelection("");
        lblCustDuration.setEnabled(false);
        txtCustDuration.setEnabled(false);
        txtTicket.setVisible(false);
        btnReset.setVisible(false);
        btnPay.setEnabled(false);
    }
}
