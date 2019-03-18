import CarPark.LocalServerPOA;
import CarPark.Machines;
import CarPark.VehicleEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class LocalServerImpl extends LocalServerPOA {
    public static ArrayList<VehicleEvent> events;
    public static ArrayList<Machines> machines;

    public LocalServerImpl() {
        events = new ArrayList<VehicleEvent>();
        machines = new ArrayList<Machines>();
    }



    @Override
    public String location() {
        return null;
    }

    @Override
    public VehicleEvent[] events_log() {
        return (VehicleEvent[])events.toArray();        // Returns arraylist as an array.
    }

    @Override
    public Machines[] machine_log() {
        return (Machines[])machines.toArray();
    }

    @Override
    public void vehicle_in(VehicleEvent event) {
        System.out.println(event.registration_number + "   " + event.time +  "    " + event.date+ "    " + event.operation);
        events.add(event);

        for (int i = 0; i < events.size(); i++) {
            System.out.println(events.get(i).registration_number);
        }
    }

    @Override
    public void vehicle_out(VehicleEvent event) {

    }

    @Override
    public boolean vehicle_paid(VehicleEvent event) {
        System.out.println(event.registration_number + "   " + event.time +  "    " + event.date + "    " + event.operation);
        events.add(event);

        for (int i = 0; i < events.size(); i++) {
            System.out.println(events.get(i).registration_number + "   " + events.get(i).operation);
        }
        System.out.println(return_cash_total());
        return true;
    }

    @Override
    public boolean vehicle_in_car_park(String registration_number) {
        for (int i = 0; i < events.size(); i++) {
            System.out.println("vehicle in car park " + events.get(i).registration_number + "    " + events.get(i).operation);

            if ((registration_number.equals(events.get(i).registration_number)) && (events.get(i).operation.equals("Entered"))) {
                System.out.println("FOUND");
               return true;
            }
        }
        return false;
    }

    @Override
    public double return_cash_total() {
        double total = 0;
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).operation.equals("Paid")) {
                LocalDateTime currDate = LocalDateTime.now();

                if ((currDate.getDayOfMonth() == events.get(i).date.days) && (currDate.getMonthValue() == events.get(i).date.months) && (currDate.getYear() == events.get(i).date.years)) {
                    total = total + events.get(i).cost;
                }
            }
            System.out.println("vehicle in car park " + events.get(i).registration_number + "    " + events.get(i).operation);
        }

        return total;
    }

    @Override
    public VehicleEvent[] return_log() {
        return new VehicleEvent[0];
    }

    @Override
    public Machines[] return_machines() {
        return new Machines[0];
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
}
