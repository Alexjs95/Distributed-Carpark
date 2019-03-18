import CarPark.Date;
import CarPark.EntryGatePOA;
import CarPark.Time;

public class EntryGateImpl extends EntryGatePOA {

    LocalServerImpl impl = new LocalServerImpl();
    public static final String OPERATION = "Entered";

    @Override
    public String machine_name() {
        return null;
    }

    @Override
    public void register_gate(String machine_name) {

    }

    @Override
    public void turn_on() {

    }

    @Override
    public void vehicle_entered(Date date, Time time, String registration, String operation) {
        System.out.println("Vehicle entered with registration: " + registration + " at " + date.days + "/" + date.months + "/" + date.years + "   " + time.hours + ":" + time.minutes + ":" + time.seconds);
        CarPark.VehicleEvent vehicleEvent = new CarPark.VehicleEvent();

        vehicleEvent.registration_number = registration;
        vehicleEvent.date = date;
        vehicleEvent.time = time;
        vehicleEvent.operation = OPERATION;


        impl.vehicle_in(vehicleEvent);
    }

    @Override
    public void turn_off() {

    }

    @Override
    public void reset() {

    }
}
