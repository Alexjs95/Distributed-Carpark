import CarPark.LocalServerPOA;
import CarPark.Log_of_vehicle_eventsHelper;
import CarPark.Log_of_vehicle_eventsHolder;
import CarPark.VehicleEvent;
import org.omg.CORBA.Any;

public class LocalServerImpl extends LocalServerPOA {

    @Override
    public String location() {
        return null;
    }

    @Override
    public VehicleEvent[] log() {
        return new VehicleEvent[0];
    }

    @Override
    public void vehicle_in(VehicleEvent event) {
        System.out.println(event.registration_number + "   " + event.time +  "    " + event.date);

        Any a = org.omg.CORBA.ORB.init().create_any();
        VehicleEvent[] events = new VehicleEvent[1];
        events[0] = event;

        Log_of_vehicle_eventsHelper.insert(a, events);


        System.out.println(Log_of_vehicle_eventsHelper.read(a.create_input_stream())[0].registration_number);






//        for (int i = 0; i <= events.length; i++) {
//            System.out.println(log()[0].registration_number);
//        }
    }

    @Override
    public void vehicle_out(VehicleEvent event) {

    }

    @Override
    public void vehicle_paid(VehicleEvent event) {

    }

    @Override
    public boolean vehicle_in_car_park(String registration_number) {
        return false;
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
