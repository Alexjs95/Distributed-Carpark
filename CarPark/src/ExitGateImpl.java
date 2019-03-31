import CarPark.*;
import CarPark.LocalServer;

public class ExitGateImpl extends ExitGatePOA {
    public static final String MACHINETYPE = "Exit Gate";
    public static final String OPERATION = "Exited";
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

        lsImpl.add_exit_gate(machine);
    }

    @Override
    public void vehicle_exited(Date date, Time time, String registration) {
        CarPark.VehicleEvent vehicleEvent = new CarPark.VehicleEvent();

        vehicleEvent.registration_number = registration;
        vehicleEvent.date = date;
        vehicleEvent.time = time;
        vehicleEvent.operation = OPERATION;

        lsImpl.vehicle_out(vehicleEvent);
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
