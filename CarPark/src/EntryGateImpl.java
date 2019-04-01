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
    public boolean vehicle_entered(Date date, Time time, String registration) {
        System.out.println("Vehicle entered with registration: " + registration + " at " + date.days + "/" + date.months + "/" + date.years + "   " + time.hours + ":" + time.minutes + ":" + time.seconds);
        CarPark.VehicleEvent vehicleEvent = new CarPark.VehicleEvent();

        vehicleEvent.registration_number = registration;
        vehicleEvent.date = date;
        vehicleEvent.time = time;
        vehicleEvent.operation = OPERATION;

        return lsImpl.vehicle_in(vehicleEvent);
    }


    @Override
    public void turn_on(String machine_name, String machine_type) {
        enabled = true;
        lsImpl.change_machine_state(machine_name, machine_type, enabled);
    }

    @Override
    public void turn_off(String machine_name, String machine_type) {
        enabled = false;
        lsImpl.change_machine_state(machine_name, machine_type, enabled);
    }

    @Override
    public void reset(String machine_name, String machine_type) {
        turn_off(machine_name, machine_type);
        turn_on(machine_name, machine_type);
    }

}
