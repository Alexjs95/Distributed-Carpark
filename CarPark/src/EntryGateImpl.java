import CarPark.Date;
import CarPark.EntryGatePOA;
import CarPark.Machines;
import CarPark.Time;

public class EntryGateImpl extends EntryGatePOA {
    public static final String MACHINETYPE = "Entry Gate";
    public static final String OPERATION = "Entered";
    private static String machine_name;

    LocalServerImpl impl = new LocalServerImpl();

    @Override
    public String machine_name() {
        return machine_name;
    }

    @Override
    public void register_gate(String name, String ior) {
        machine_name = name;

        Machines machines = new Machines();
        machines.ior = ior;
        machines.machine_name = name;
        machines.machine_type = MACHINETYPE;
        System.out.println(ior);
        impl.add_entry_gate(machines);
    }

    @Override
    public void turn_on() {

    }

    @Override
    public void vehicle_entered(Date date, Time time, String registration) {
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
