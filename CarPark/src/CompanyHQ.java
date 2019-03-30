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

public class CompanyHQ extends JFrame {
    public static JTable tblMachines;
    public static DefaultTableModel model;
    public static JButton btnRefresh;
    public static JButton btnTurnOn;
    public static JButton btnTurnOff;
    public static JButton btnReset;
    public static JLabel lblCashTotal;

    public static int numberOfServers;
    //    static ArrayList<String> servers = new ArrayList<String>();
    public static String companyHQName = "HQ";
    public static LocalServer localServer;

    public static int row;
    public static int col;

    public CompanyHQ() {
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
        lblCashTotal = new JLabel("Cash Total: ");

        btnReset.setEnabled(false);
        btnTurnOn.setEnabled(false);
        btnTurnOff.setEnabled(false);

        JScrollPane sp = new JScrollPane(tblMachines);
        panel.add(sp);
        panel.add(btnRefresh);
        panel.add(btnTurnOn);
        panel.add(btnTurnOff);
        panel.add(btnReset);
        panel.add(lblCashTotal);

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        CompanyHQ companyHQ = new CompanyHQ();

        try {
            // Initialize the ORB
            ORB orb = ORB.init(args, null);


            // Get a reference to the Naming service
            org.omg.CORBA.Object nameServiceObj = orb.resolve_initial_references ("NameService");
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

            CompanyHQImpl hqImpl = new CompanyHQImpl();

            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(hqImpl);
            CarPark.CompanyHQ cref = CompanyHQHelper.narrow(ref);

            // bind the entry gate object in the Naming service
            NameComponent[] hqName = nameService.to_name(companyHQName);
            nameService.rebind(hqName, cref);

            tblMachines.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    row = tblMachines.getSelectedRow();
                    col = tblMachines.getSelectedColumn();
                    System.out.println("current row: " + tblMachines.getValueAt(row, col));

                    String serverName = tblMachines.getValueAt(row, 0).toString();

                    try {
                        localServer = LocalServerHelper.narrow(nameService.resolve_str(serverName));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    lblCashTotal.setText(tblMachines.getValueAt(row, 0) + "'s Cash Total: £" + localServer.return_cash_total());
                }
            });

            btnRefresh.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.setNumRows(0);

                    for(int i = 0; i < hqImpl.servers.size(); i++) {
                        for(int j = 0; j < hqImpl.return_machines((short)i).length; j++) {
                            if (hqImpl.return_machines((short)i)[j].enabled == true) {
                                model.addRow(new String[]{hqImpl.servers.get(i).name, hqImpl.return_machines((short)i)[j].machine_name,hqImpl.return_machines((short)i)[j].machine_type, "true"});
                            } else {
                                model.addRow(new String[]{hqImpl.servers.get(i).name, hqImpl.return_machines((short)i)[j].machine_name,hqImpl.return_machines((short)i)[j].machine_type, "false"});
                            }
                        }
                    }
                    btnReset.setEnabled(true);
                    btnTurnOn.setEnabled(true);
                    btnTurnOff.setEnabled(true);
                }
            });

            btnTurnOn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String machine_name = tblMachines.getValueAt(row, 1).toString();
                    String machine_type = tblMachines.getValueAt(row, 2).toString();
                    try {
                        if (machine_type.contains("Entry")) {
                            EntryGate entryGate = EntryGateHelper.narrow(nameService.resolve_str(machine_name));
                            entryGate.turn_on();
                        } else if (machine_type.contains("Exit")) {
                            ExitGate exitGate = ExitGateHelper.narrow(nameService.resolve_str(machine_name));
                            exitGate.turn_on();
                        } else if (machine_type.contains("Pay")) {
                            PayStation payStation = PayStationHelper.narrow(nameService.resolve_str(machine_name));
                            payStation.turn_on();
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });

            btnTurnOff.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String machine_name = tblMachines.getValueAt(row, 1).toString();
                    String machine_type = tblMachines.getValueAt(row, 2).toString();
                    try {
                        if (machine_type.contains("Entry")) {
                            EntryGate entryGate = EntryGateHelper.narrow(nameService.resolve_str(machine_name));
                            entryGate.turn_off();
                        } else if (machine_type.contains("Exit")) {
                            ExitGate exitGate = ExitGateHelper.narrow(nameService.resolve_str(machine_name));
                            exitGate.turn_off();
                        } else if (machine_type.contains("Pay")) {
                            PayStation payStation = PayStationHelper.narrow(nameService.resolve_str(machine_name));
                            payStation.turn_off();
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });

            btnReset.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String machine_name = tblMachines.getValueAt(row, 1).toString();
                    String machine_type = tblMachines.getValueAt(row, 2).toString();
                    try {
                        if (machine_type.contains("Entry")) {
                            EntryGate entryGate = EntryGateHelper.narrow(nameService.resolve_str(machine_name));
                            entryGate.reset();
                        } else if (machine_type.contains("Exit")) {
                            ExitGate exitGate = ExitGateHelper.narrow(nameService.resolve_str(machine_name));
                            exitGate.reset();
                        } else if (machine_type.contains("Pay")) {
                            PayStation payStation = PayStationHelper.narrow(nameService.resolve_str(machine_name));
                            payStation.reset();
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            System.out.println("Headquarters Exception");
            e.printStackTrace();
        }
    }
}
