import CarPark.LocalServerPOA;
import CarPark.LocalServers;
import CarPark.Machines;
import CarPark.VehicleEvent;

import java.util.ArrayList;

public class LocalServerImpl extends LocalServerPOA {
    private static String serverName;

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
    public VehicleEvent[] events_log() {
        return (VehicleEvent[])events.toArray();        // Returns arraylist as an array.
    }

    @Override
    public Machines[] machine_log() {
        return (Machines[])machines.toArray();
    }

    @Override
    public void register_server(String name, String ior) {
        serverName = name;

        LocalServers server = new LocalServers();
        server.ior = ior;
        server.name = name;
    }

    @Override
    public void vehicle_in(VehicleEvent event) {
        boolean inCarPark = check_vehicle_in_car_park(event.registration_number);
        if (inCarPark == false) {
            events.add(event);
            System.out.println(event.registration_number + " has entered the carpark");
            System.out.println("Server " + serverName + " events size:  "  + events.size());
        } else {
            System.out.println(event.registration_number + " already in carpark");
        }
    }

    @Override
    public void vehicle_out(VehicleEvent event) {
        boolean inCarPark = check_vehicle_out_car_park(event.registration_number);
        if (inCarPark == false) {
            events.add(event);
            System.out.println(event.registration_number + " has exited the carpark");
            System.out.println("Server " + serverName + " has events size:  "  + events.size());
        } else {
            System.out.println(event.registration_number + "has already left the carpark");
        }
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
        return 0;
    }

    @Override
    public VehicleEvent[] return_log() {
        return new VehicleEvent[0];
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
