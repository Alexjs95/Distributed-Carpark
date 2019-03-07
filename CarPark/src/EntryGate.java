import javax.swing.*;
import java.awt.EventQueue;
import org.omg.CORBA.*;

public class EntryGate extends JFrame {
    private JTextField txtReg;
    private JButton btnSubmit;


    public EntryGate() {
        JFrame frame = new JFrame("Entry Gate");
        JPanel panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);

        txtReg = new JTextField(30);
        btnSubmit = new JButton("Submit");
        panel.add(txtReg);
        panel.add(btnSubmit); // Adds Button to content pane of frame
        frame.add(panel);
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                (new EntryGate()).setVisible(true);
            }
        });
    }
}
