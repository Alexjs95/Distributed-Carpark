import CarPark.LocalServerPOA;
import CarPark.LocalServers;
import CarPark.Machines;
import CarPark.VehicleEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class LocalServerImpl extends LocalServerPOA {
    private static String serverLocation;

    public static ArrayList<VehicleEvent> events;
    public static ArrayList<Machines> machines;

    HeadquartersImpl impl = new HeadquartersImpl();

    public LocalServerImpl() {
        events = new ArrayList<VehicleEvent>();
        machines = new ArrayList<Machines>();
    }

    @Override
    public String location() {
        return serverLocation;
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
        boolean inCarPark = vehicle_in_car_park(event.registration_number);
        if (inCarPark == false) {
            events.add(event);
            System.out.println(event.registration_number + " has entered the carpark");
        } else {
            System.out.println(event.registration_number + " already in carpark");
        }
    }

    @Override
    public void vehicle_out(VehicleEvent event) {

    }

    @Override
    public boolean vehicle_pay(VehicleEvent event) {
        boolean paid = vehicle_paid_for(event.registration_number);
        if (paid == false) {
            events.add(event);
            System.out.println(event.registration_number + " paid for");
            return true;
        } else {
            System.out.println(event.registration_number + " already paid for");
            return false;
        }
    }

    @Override
    public boolean vehicle_paid_for(String registration_number) {
        for (int i = 0; i < events.size(); i++) {
            if((registration_number.equals(events.get(i).registration_number)) && (events.get(i).operation.equals("Paid"))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean vehicle_in_car_park(String registration_number) {

        // TODO check if the vehicle has exited. if it has then the vehicle is no longer in the carpark.
        for (int i = 0; i < events.size(); i++) {
            if ((registration_number.equals(events.get(i).registration_number)) && (events.get(i).operation.equals("Entered"))) {
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
        }
        return total;
    }

    @Override
    public VehicleEvent[] return_log() {
        return new VehicleEvent[0];
    }

    @Override
    public void register_server(String location, String ior) {
        serverLocation = location;

        LocalServers servers = new LocalServers();
        servers.ior = ior;
        servers.location = serverLocation;
        impl.register_local_server(servers);
    }


    @Override
    public void add_entry_gate(Machines machine) {
        machines.add(machine);
        System.out.println("Machine registered: " + machine.machine_name + "  " + machine.machine_type);
    }

    @Override
    public void add_exit_gate(Machines machine) {
        machines.add(machine);
        System.out.println("Machine registered: " + machine.machine_name + "  " + machine.machine_type);
    }

    @Override
    public void add_pay_station(Machines machine) {
        machines.add(machine);
        System.out.println("Machine registered: " + machine.machine_name + "  " + machine.machine_type);
    }
}
