import CarPark.*;
import CarPark.CompanyHQ;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LocalServerImpl extends LocalServerPOA {
    CompanyHQ hqImpl;

    private static String serverName;
    public static double costPerHour = 1.00;
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
    public double cost_per_hour() {
        return costPerHour;
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
        return mach;        // Returns arraylist as an array.
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
    public void change_rate(double cost) {
        costPerHour = cost;
    }

    @Override
    public int return_spaces() {
        System.out.println(spacesAvailable);
        return spacesAvailable;
    }
    @Override
    public void register_server(String name, int spots, CompanyHQ hq) {
        spacesAvailable = spots;
        serverName = name;
        hqImpl = hq;
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


    @Override
    public boolean vehicle_in(VehicleEvent event) {
        boolean inCarPark = check_vehicle_in_car_park(event.registration_number);
        if (inCarPark == false) {
            events.add(event);
            spacesAvailable--;
            System.out.println(event.registration_number + " has entered the carpark");
            System.out.println("Server " + serverName + " events size:  "  + events.size());
            return true;        // vehicle sucessfully added.
        } else {
            return false;       // vehicle already in car park
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
    public boolean vehicle_out(VehicleEvent event) {
        boolean inCarPark = check_vehicle_in_car_park(event.registration_number);
        boolean paid = vehicle_paid_for(event.registration_number);
        boolean leftCarPark = check_vehicle_out_car_park(event.registration_number);

        if (inCarPark == true) {
            if (paid == true) {
                if (leftCarPark == false) {
                    events.add(event);
                    spacesAvailable++;      // vehicle left so increment spaces available.

                    for (int i = 0; i < events.size(); i++) {

                        // Set value exited to true for entered event so same vehicle can re-enter
                        if ((event.registration_number.equals(events.get(i).registration_number) && (events.get(i).operation.contains("Entered")))) {
                            if (events.get(i).exited == false) {
                                events.get(i).exited = true;
                            }
                        }

                        // Checks to see if vehicle hasn't overstayed duration
                        if ((event.registration_number.equals(events.get(i).registration_number)) && (events.get(i).operation.contains("Paid"))) {

                            // Set value exited to true for paid event so same vehicle can re-pay.
                            if (events.get(i).exited == false) {
                                events.get(i).exited = true;
                            }


                            // Get current date & time.
                            LocalDateTime currDateTime = LocalDateTime.now();
                            // Gets the dateTime for when vehicle was paid for.
                            LocalDateTime paidDateTime = getDateTime(events.get(i).date, events.get(i).time);
                            // DateTime vehicle should have left by.
                            LocalDateTime leftByDatetime = paidDateTime.plusHours(events.get(i).duration);

                            // Calculate difference between the current time and the
                            Duration duration = Duration.between(currDateTime, leftByDatetime);
                            int diff = (int) Math.abs(duration.toMinutes());

                            // if the difference is greater than 0 then they have overstayed
                            if (diff > 0) {
                                int hours = diff / 60;
                                int minutes = diff % 60;
                                String overStayedBy =  hours + "hours "  + minutes + "mins";

                                // Create a new alert
                                Alerts alert = new Alerts();
                                alert.alertType = "Overstayed Duration";
                                alert.overStayedBy = overStayedBy;
                                alert.vehicleEvent = event;
                                alert.serverName = serverName;

                                hqImpl.raise_alarm(alert);      // Inform HQ of the alert.
                            }
                        }
                    }
                    return true;        // vehicle has left,
                } else {
                    return false;       // vehicle already left
                }
            } else {    // user has left carpark and not paid.
                events.add(event);
                spacesAvailable++;      // increment spaces available as vehicle left.

                //
                for (int i = 0; i < events.size(); i++) {
                    if ((event.registration_number.equals(events.get(i).registration_number)) && (events.get(i).operation.contains("Entered"))) {
                        // Set value exited to true for entered event so same vehicle can re-enter
                        if (events.get(i).exited == false) {
                            events.get(i).exited = true;
                        }

                        // Get current date & time.
                        LocalDateTime currDateTime = LocalDateTime.now();
                        // Get Date time of vehicle entering car park.
                        LocalDateTime enteredDateTime = getDateTime(events.get(i).date, events.get(i).time);

                        System.out.println("Time now " + currDateTime);
                        System.out.println("Vehicle entered at " + enteredDateTime);

                        Duration duration = Duration.between(currDateTime, enteredDateTime);
                        int diff = (int) Math.abs(duration.toMinutes());

                        if (diff >= 5) {
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
    public boolean check_vehicle_in_car_park(String registration_number) {
        for (int i = 0; i < events.size(); i++) {
            // check if vehicle event matches registration and Entered.
            if ((registration_number.equals(events.get(i).registration_number)) && (events.get(i).operation.equals("Entered") && (events.get(i).exited == false))) {
                return true;        // vehicle has entered car park.
            }
        }
        return false;           // vehicle not in car park.
    }

    @Override
    public boolean vehicle_paid_for(String registration_number) {
        for (int i = 0; i < events.size(); i++) {
            // check if vehicle event matches registration and Paid.
            if((registration_number.equals(events.get(i).registration_number)) && (events.get(i).operation.equals("Paid")) && (events.get(i).exited == false)) {
                return true;        // vehicle has been paid for
            }
        }
        return false;           // vehicle has not been paid for
    }

    @Override
    public boolean check_vehicle_out_car_park(String registration_number) {
        for (int i = 0; i < events.size(); i++) {
            // check if vehicle event matches registration and Exited.
            if ((registration_number.equals(events.get(i).registration_number)) && (events.get(i).operation.equals("Exited")) && (events.get(i).exited == false)) {
                return true;        // vehicle has left car park
            }
        }
        return false;           // vehicle still in car park.
    }

    @Override
    public void change_machine_state(String machine_name, String machine_type, boolean state) {
        for (int i = 0; i < machines.size(); i ++) {
            // Find machine of choice.
            if ((machines.get(i).machine_name.equals(machine_name)) && (machines.get(i).machine_type.equals(machine_type))) {
                // change the found machines state.
                machines.get(i).enabled = state;
            }
        }
    }

    public static LocalDateTime getDateTime(Date date, Time time) {
        // Convert Date and Time to string.
        String temp = date.years + "-" + date.months + "-" + date.days + " " + time.hours + ":" + time.minutes + ":" + time.seconds;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d H:m:s");    // format for LocalDateTime.
        LocalDateTime dateTime = LocalDateTime.parse(temp, formatter);      // Convert string to the format.

        return dateTime;
    }
}
