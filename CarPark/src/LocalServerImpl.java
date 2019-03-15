import CarPark.LocalServerPOA;
import CarPark.VehicleEvent;

import java.util.ArrayList;

public class LocalServerImpl extends LocalServerPOA {
    private ArrayList<VehicleEvent> events;

    public LocalServerImpl() {
        events = new ArrayList<VehicleEvent>();
    }

    @Override
    public String location() {
        return null;
    }

    @Override
    public VehicleEvent[] log() {
        return (VehicleEvent[])events.toArray();        // Returns arraylist as an array.
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

        return true;
    }

    @Override
    public boolean vehicle_in_car_park(String registration_number) {
        boolean found = false;

        for (int i = 0; i < events.size(); i++) {
            if ((registration_number == events.get(i).registration_number) && (events.get(i).operation == "Entered")) {
                found = true;
            }
        }

        return found;
    }

    @Override
    public int return_cash_total() {
        return 0;
    }

    @Override
    public VehicleEvent[] return_log() {
        return new VehicleEvent[0];
    }

    @Override
    public void add_entry_gate(String gate_name, String gate_ior) {

    }

    @Override
    public void add_exit_gate(String gate_name, String gate_ior) {

    }

    @Override
    public void add_pay_station(String station_name, String station_ior) {

    }
}
