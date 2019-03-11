import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public short duration;

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




    }
}