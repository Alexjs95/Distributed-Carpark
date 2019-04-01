import CarPark.*;
import CarPark.CompanyHQ;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LocalServerImpl extends LocalServerPOA {

    //CompanyHQImpl hqImpl = new CompanyHQImpl();
    CompanyHQ hqImpl;

    private static String serverName;
    private static int spaces;
    private static int spacesAvailable;

    public static ArrayList<VehicleEvent> events;
    public static ArrayList<Machines> machines;

    public LocalServerImpl() {
        events = new ArrayList<VehicleEvent>();
        machines = new ArrayList<Machines>();
    }

    @Override
    public String server_name() {
        return serverName;
    }

    @Override
    public int spaces() {
        return spaces;
    }

    @Override
    public VehicleEvent[] events_log() {
        VehicleEvent[] event = new VehicleEvent[events.size()];
        events.toArray(event);
        return event;        // Returns arraylist as an array.
    }

    @Override
    public Machines[] machine_log() {
        Machines[] mach = new Machines[machines.size()];
        machines.toArray(mach);
        return mach;
    }

    @Override
    public boolean vehicle_in(VehicleEvent event) {
        boolean inCarPark = check_vehicle_in_car_park(event.registration_number);
        if (inCarPark == false) {
            events.add(event);
            spacesAvailable--;
            System.out.println(event.registration_number + " has entered the carpark");
            System.out.println("Server " + serverName + " events size:  "  + events.size());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean vehicle_out(VehicleEvent event) {
        boolean inCarPark = check_vehicle_in_car_park(event.registration_number);
        boolean paid = vehicle_paid_for(event.registration_number);
        boolean leftCarPark = check_vehicle_out_car_park(event.registration_number);

        if (inCarPark == true) {
            if (paid == true) {
                if (leftCarPark == false) {
                    //TODO: once a vehicle leaves check the time against the entrance time + duration of in events with operation paid
                    events.add(event);
                    spacesAvailable++;
                    for (VehicleEvent logs : events) {
                        if ((event.registration_number.equals(logs.registration_number)) && (logs.operation.contains("Paid"))) {
                            // Get current date & time.
                            LocalDateTime currDateTime = LocalDateTime.now();
                            // Gets the dateTime for when vehicle was paid for.
                            LocalDateTime paidDateTime = getDateTime(logs.date, logs.time);



                            System.out.println("Time now " + currDateTime);
                            System.out.println("Vehicle entered at " + paidDateTime);

                            Duration duration = Duration.between(currDateTime, paidDateTime);
                            int diff = (int) Math.abs(duration.toMinutes());

                            if (diff >= 5) {
                                int hours = diff / 60;
                                int minutes = diff % 60;
                                String overStayedBy =  hours + " : "  + minutes;

                                Alerts alert = new Alerts();
                                alert.alertType = "Overstayed Duration";
                                alert.overStayedBy = overStayedBy;
                                alert.vehicleEvent = event;
                                alert.serverName = serverName;
                                hqImpl.raise_alarm(alert);
                            }
                            System.out.println(diff);

                        }
                    }
                    return true;        // vehicle has left,
                } else {
                    return false;       // vehicle already left
                }
            } else {    // user has left carpark and not paid.
                //TODO: once this happens inform HQ if greater than 5 minute period
                events.add(event);
                spacesAvailable++;
                for (VehicleEvent logs : events) {
                    if (event.registration_number.equals(logs.registration_number)) {
                        // Get current date & time.
                        LocalDateTime currDateTime = LocalDateTime.now();
                        // Get Date time of vehicle entering car park.
                        LocalDateTime enteredDateTime = getDateTime(logs.date, logs.time);

                        System.out.println("Time now " + currDateTime);
                        System.out.println("Vehicle entered at " + enteredDateTime);

                        Duration duration = Duration.between(currDateTime, enteredDateTime);
                        int diff = (int) Math.abs(duration.toMinutes());

                        if (diff >= 1) {
                            int hours = diff / 60;
                            int minutes = diff % 60;
                            String overStayedBy =  hours + "hours "  + minutes + "mins";


                            Alerts alert = new Alerts();
                            alert.alertType = "Not Paid";
                            alert.overStayedBy = overStayedBy;
                            alert.vehicleEvent = event;
                            alert.serverName = serverName;

                            hqImpl.raise_alarm(alert);
                        }
                    }
                }
                return true;    // vehicle left but not paid for
            }
        } else {
            return false;       // vehicle not in car park.
        }
    }

    @Override
    public boolean vehicle_pay(VehicleEvent event) {
        boolean paidFor = vehicle_paid_for(event.registration_number);
        boolean inCarPark = check_vehicle_in_car_park(event.registration_number);
        if (inCarPark) {
            if (paidFor == false) {
                events.add(event);
                System.out.println(event.registration_number + " has been paid for");
                System.out.println("Server " + serverName + " events size:  "  + events.size());
                return true;        // paid for.
            } else {
                return false;        // already paid for.
            }
        } else {
            return false;       // not in car park
        }
    }

    @Override
    public boolean vehicle_paid_for(String registration_number) {
        for (int i = 0; i < events.size(); i++) {
            if((registration_number.equals(events.get(i).registration_number)) && (events.get(i).operation.equals("Paid"))) {
                return true;        // vehicle has been paid for
            }
        }
        return false;           // vehicle has not been paid for
    }


    @Override
    public boolean check_vehicle_in_car_park(String registration_number) {
        for (int i = 0; i < events.size(); i++) {
            if ((registration_number.equals(events.get(i).registration_number)) && (events.get(i).operation.equals("Entered"))) {
                return true;        // vehicle has entered car park.
            }
        }
        return false;           // vehicle not in car park.
    }

    @Override
    public boolean check_vehicle_out_car_park(String registration_number) {
        for (int i = 0; i < events.size(); i++) {
            if ((registration_number.equals(events.get(i).registration_number)) && (events.get(i).operation.equals("Exited"))) {
                return true;        // vehicle has left car park
            }
        }
        return false;           // vehicle still in car park.
    }

    @Override
    public double return_cash_total() {
        double total = 0;
        System.out.println("return cash total method called events size : " + events.size());
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).operation.equals("Paid")) {
                LocalDateTime currDate = LocalDateTime.now();
                if ((currDate.getDayOfMonth() == events.get(i).date.days) && (currDate.getMonthValue() == events.get(i).date.months) && (currDate.getYear() == events.get(i).date.years)) {
                    total = total + events.get(i).cost;
                }
            }
        }
        System.out.println("Total " + total);
        return total;
    }

    @Override
    public VehicleEvent[] return_log() {
        return new VehicleEvent[0];
    }

    @Override
    public void change_machine_state(String machine_name, String machine_type, boolean state) {
        for (int i = 0; i < machines.size(); i ++) {
            if ((machines.get(i).machine_name.equals(machine_name)) && (machines.get(i).machine_type.equals(machine_type))) {
                System.out.println("machine state changed");
                machines.get(i).enabled = state;
            }
        }
    }

    @Override
    public void register_server(String name, int spots, CompanyHQ hq) {
        spaces = spots;
        spacesAvailable = spots;
        serverName = name;
        hqImpl = hq;
    }

    @Override
    public int return_spaces() {
        System.out.println(spacesAvailable);
        return spacesAvailable;
    }

    @Override
    public void add_entry_gate(Machines machine) {
        machines.add(machine);
    }

    @Override
    public void add_exit_gate(Machines machine) {
        machines.add(machine);
    }

    @Override
    public void add_pay_station(Machines machine) {
        machines.add(machine);
    }

    public static LocalDateTime getDateTime(Date date, Time time) {
        String temp = date.years + "-" + date.months + "-" + date.days + " " + time.hours + ":" + time.minutes + ":" + time.seconds;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d H:m:s");
        LocalDateTime dateTime = LocalDateTime.parse(temp, formatter);

        return dateTime;
    }
}
