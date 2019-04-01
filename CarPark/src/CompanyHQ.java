import CarPark.*;
import CarPark.LocalServer;
import jdk.nashorn.internal.scripts.JO;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CompanyHQ extends JFrame {
    public static JFrame frame;
    public static JTable tblMachines;
    public static DefaultTableModel machModel;

    public static JTable tblAlerts;
    public static DefaultTableModel alertsModel;

    public static JTable tblEvents;
    public static DefaultTableModel eventsModel;

    public static JComboBox<String> cbbServers;
    public static JButton btnUpdateCost;
    public static JButton btnReset;
    public static JButton btnRefresh;
    public static JButton btnResetStation;
    public static JButton btnTurnOn;
    public static JButton btnTurnOff;
    public static JLabel lblCashTotal;
    public static JLabel lblVehiclesEntered;
    public static JLabel lblVehiclesPaid;
    public static JLabel lblVehiclesExited;
    public static JLabel lblSpacesAvailable;

    public static int numberOfServers;


    public static String companyHQName = "HQ";
    public static String selectedServer;
    public static LocalServer localServer;
    public static ArrayList<String> servers;

    public static int row;
    public static int col;

    public static CompanyHQImpl hqImpl;

    public CompanyHQ() {
        frame = new JFrame("Headquarters");
        JPanel panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(510,900);

        String[] machColNames = {"Connected Machine", "Machine Type","Machine Enabled"};
        String[] alertColNames = {"Alert Type", "Registration",  "Overstayed by:" };
        String[] eventsColNames = {"Registration", "Date", "Time", "Operation", "Duration", "Cost"};

        cbbServers = new JComboBox<String>();
        machModel = new DefaultTableModel(null, machColNames);
        tblMachines = new JTable(machModel);
        alertsModel = new DefaultTableModel(null, alertColNames);
        tblAlerts = new JTable(alertsModel);

        eventsModel = new DefaultTableModel(null, eventsColNames);
        tblEvents = new JTable(eventsModel);
        
        btnUpdateCost = new JButton("Update Server Rate");
        btnReset =  new JButton("Get connected servers");
        btnRefresh = new JButton("Refresh");
        btnTurnOn = new JButton("Turn on");
        btnTurnOff = new JButton("Turn off");
        btnResetStation = new JButton("Reset Station");
        lblCashTotal = new JLabel("Cash Total: ");
        lblVehiclesEntered = new JLabel("Vehicles Entered: ");
        lblVehiclesPaid = new JLabel("Vehicles Paid: ");
        lblVehiclesExited = new JLabel("Vehicles Exited: ");
        lblSpacesAvailable = new JLabel("Spaces Available: ");

        btnUpdateCost.setEnabled(false);
        btnResetStation.setEnabled(false);
        btnTurnOn.setEnabled(false);
        btnTurnOff.setEnabled(false);
        btnRefresh.setEnabled(false);

        panel.add(cbbServers);
        panel.add(btnRefresh);
        panel.add(btnReset);

        JScrollPane spMachines = new JScrollPane(tblMachines);
        spMachines.setPreferredSize(new Dimension(450, 200));
        panel.add(spMachines);

        panel.add(btnUpdateCost);
        panel.add(btnTurnOn);
        panel.add(btnTurnOff);
        panel.add(btnResetStation);
        panel.add(lblCashTotal);
        panel.add(Box.createHorizontalStrut(50));
        panel.add(lblVehiclesEntered);
        panel.add(Box.createHorizontalStrut(50));
        panel.add(lblVehiclesPaid);
        panel.add(Box.createHorizontalStrut(50));
        panel.add(lblVehiclesExited);
        panel.add(Box.createHorizontalStrut(50));
        panel.add(lblSpacesAvailable);

        JScrollPane spEvents = new JScrollPane(tblEvents);
        spEvents.setPreferredSize(new Dimension(450, 300));
        panel.add(spEvents);

        JScrollPane spAlerts = new JScrollPane(tblAlerts);
        spAlerts.setPreferredSize(new Dimension(450, 200));
        panel.add(spAlerts);

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

            hqImpl = new CompanyHQImpl();

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

                    btnResetStation.setEnabled(true);
                    btnTurnOn.setEnabled(true);
                    btnTurnOff.setEnabled(true);
                }
            });

            cbbServers.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    selectedServer = cbbServers.getSelectedItem().toString();
                    btnUpdateCost.setEnabled(true);

                    if (selectedServer.equals("")) {
                        return;
                    } else {
                        try {
                            localServer = LocalServerHelper.narrow(nameService.resolve_str(selectedServer));
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }

                        setDetails(selectedServer);
                        btnRefresh.setEnabled(true);
                    }
                    // when this method is called i want to start a timer that calls setDetails every 5 seconds.
                }
            });

            btnReset.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String[] servers = new String[hqImpl.servers.size() + 1];
                    servers[0] = "";
                    for(int i = 0; i < hqImpl.servers.size(); i++) {
                        servers[i + 1] = hqImpl.servers.get(i).name;
                    }
                    cbbServers.setModel(new DefaultComboBoxModel<>(servers));

                    machModel.setNumRows(0);
                    eventsModel.setNumRows(0);
                    alertsModel.setNumRows(0);

                    btnUpdateCost.setEnabled(false);
                    btnResetStation.setEnabled(false);
                    btnTurnOn.setEnabled(false);
                    btnTurnOff.setEnabled(false);
                    btnRefresh.setEnabled(false);

                    lblCashTotal.setText("Cash Total: ");
                    lblVehiclesEntered.setText("Vehicles Entered: ");
                    lblVehiclesPaid.setText("Vehicles Paid: ");
                    lblVehiclesExited.setText("Vehicles Exited: ");
                    lblSpacesAvailable.setText("Spaces Available: ");
                }
            });

            btnRefresh.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    btnTurnOn.setEnabled(false);
                    btnTurnOff.setEnabled(false);
                    btnResetStation.setEnabled(false);
                    try {
                        localServer = LocalServerHelper.narrow(nameService.resolve_str(selectedServer));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    setDetails(selectedServer);
                }
            });

            btnUpdateCost.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //show dialog...

                    try {
                        double rate = Double.parseDouble(JOptionPane.showInputDialog(frame, "Current Rate: £" + localServer.cost_per_hour() + "    Enter new cost: " ));
                        localServer.change_rate(rate);

                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(frame,"Please make sure new cost is a double.");
                    }
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
                            entryGate.turn_on(machine_name, machine_type);
                        } else if (machine_type.contains("Exit")) {
                            ExitGate exitGate = ExitGateHelper.narrow(nameService.resolve_str(machine_name));
                            exitGate.turn_on(machine_name, machine_type);
                        } else if (machine_type.contains("Pay")) {
                            PayStation payStation = PayStationHelper.narrow(nameService.resolve_str(machine_name));
                            payStation.turn_on(machine_name, machine_type);
                        }
                        setDetails(selectedServer);
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
                            entryGate.turn_off(machine_name, machine_type);
                        } else if (machine_type.contains("Exit")) {
                            ExitGate exitGate = ExitGateHelper.narrow(nameService.resolve_str(machine_name));
                            exitGate.turn_off(machine_name, machine_type);
                        } else if (machine_type.contains("Pay")) {
                            PayStation payStation = PayStationHelper.narrow(nameService.resolve_str(machine_name));
                            payStation.turn_off(machine_name, machine_type);
                        }
                        setDetails(selectedServer);

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });

            btnResetStation.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String machine_name = tblMachines.getValueAt(row, 1).toString();
                    String machine_type = tblMachines.getValueAt(row, 2).toString();
                    try {
                        if (machine_type.contains("Entry")) {
                            EntryGate entryGate = EntryGateHelper.narrow(nameService.resolve_str(machine_name));
                            entryGate.reset(machine_name, machine_type);
                        } else if (machine_type.contains("Exit")) {
                            ExitGate exitGate = ExitGateHelper.narrow(nameService.resolve_str(machine_name));
                            exitGate.reset(machine_name, machine_type);
                        } else if (machine_type.contains("Pay")) {
                            PayStation payStation = PayStationHelper.narrow(nameService.resolve_str(machine_name));
                            payStation.reset(machine_name, machine_type);
                        }
                        setDetails(selectedServer);

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

    public static void setDetails(String selectedServer) {
        int vehiclesEntered = 0;
        int vehiclesPaidFor = 0;
        int vehiclesExited = 0;

        machModel.setNumRows(0);
        eventsModel.setNumRows(0);
        alertsModel.setNumRows(0);


        // Get machines connected to selected Server.
        for (int i = 0; i < hqImpl.servers.size(); i++) {
            if (hqImpl.servers.get(i).name.equals(selectedServer)) {
                for (int j = 0; j < hqImpl.return_machines((short) i).length; j++) {
                    if (hqImpl.return_machines((short) i)[j].enabled == true) {
                        machModel.addRow(new String[]{hqImpl.return_machines((short) i)[j].machine_name, hqImpl.return_machines((short) i)[j].machine_type, "true"});
                    } else {
                        machModel.addRow(new String[]{hqImpl.return_machines((short) i)[j].machine_name, hqImpl.return_machines((short) i)[j].machine_type, "false"});
                    }
                }
            }
        }

        lblCashTotal.setText(selectedServer + "'s Cash Total: £" + localServer.return_cash_total());

        eventsModel.setNumRows(0);

        VehicleEvent[] events = localServer.events_log();
        ArrayList<VehicleEvent> vehicleEvents = new ArrayList<VehicleEvent>();

        for (int i = 0; i < events.length; i++) {
            vehicleEvents.add(events[i]);
        }

        for (int j = 0; j < vehicleEvents.size(); j++) {
            if (vehicleEvents.get(j).operation.equals("Entered")) {
                vehiclesEntered++;
            } else if (vehicleEvents.get(j).operation.equals("Paid")) {
                vehiclesPaidFor++;
            } else if (vehicleEvents.get(j).operation.equals("Exited")) {
                vehiclesExited++;
            }
            String date = vehicleEvents.get(j).date.days + "-" + vehicleEvents.get(j).date.months + "-" + vehicleEvents.get(j).date.years;
            String time = vehicleEvents.get(j).time.hours + ":" + vehicleEvents.get(j).time.minutes + ":" + vehicleEvents.get(j).time.seconds;
            eventsModel.addRow(new String[]{vehicleEvents.get(j).registration_number, date, time,
                    vehicleEvents.get(j).operation, vehicleEvents.get(j).duration + " hours", "£" + vehicleEvents.get(j).cost});
        }

        lblVehiclesEntered.setText("Vehicles Entered: " + vehiclesEntered);
        lblVehiclesPaid.setText("Vehicles Paid: " + vehiclesPaidFor);
        lblVehiclesExited.setText("Vehicles Exited: " + vehiclesExited);

        lblSpacesAvailable.setText("Spaces Available: " + localServer.return_spaces());

        alertsModel.setNumRows(0);

        // Get alerts associated with the selected server.
        for (int i = 0; i < hqImpl.alerts.size(); i++) {
            if (hqImpl.alerts.get(i).serverName.equals(selectedServer)) {
                alertsModel.addRow(new String[]{hqImpl.alerts.get(i).alertType,
                        hqImpl.alerts.get(i).vehicleEvent.registration_number, hqImpl.alerts.get(i).overStayedBy});
            }
        }
    }
}
