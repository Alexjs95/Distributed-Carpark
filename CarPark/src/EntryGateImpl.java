import CarPark.*;
import CarPark.LocalServer;

public class EntryGateImpl extends EntryGatePOA {
    public static final String MACHINETYPE = "Entry Gate";
    public static final String OPERATION = "Entered";
    private static String machine_name;
    public static boolean enabled = true;

    LocalServer lsImpl;

    @Override
    public String machine_name() {
        return machine_name;
    }

    @Override
    public boolean status() {
        return enabled;
    }

    @Override
    public void register_gate(String name, String ior, LocalServer obj) {
        lsImpl = obj;

        machine_name = name;
        Machines machine = new Machines();
        machine.ior = ior;
        machine.machine_name = machine_name;
        machine.machine_type = MACHINETYPE;
        machine.enabled = true;

        lsImpl.add_entry_gate(machine);
    }

    @Override
    public void vehicle_entered(Date date, Time time, String registration) {
        System.out.println("Vehicle entered with registration: " + registration + " at " + date.days + "/" + date.months + "/" + date.years + "   " + time.hours + ":" + time.minutes + ":" + time.seconds);
        CarPark.VehicleEvent vehicleEvent = new CarPark.VehicleEvent();

        vehicleEvent.registration_number = registration;
        vehicleEvent.date = date;
        vehicleEvent.time = time;
        vehicleEvent.operation = OPERATION;

        lsImpl.vehicle_in(vehicleEvent);
    }

    @Override
    public void turn_on() {
        enabled = true;
    }

    @Override
    public void turn_off() {
        enabled = false;
    }

    @Override
    public void reset() {
        turn_on();
        turn_off();
    }
}
