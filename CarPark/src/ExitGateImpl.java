import CarPark.Date;
import CarPark.ExitGatePOA;
import CarPark.Machines;
import CarPark.Time;

public class ExitGateImpl extends ExitGatePOA {
    public static final String MACHINETYPE = "Exit Gate";
    public static final String OPERATION = "Exited";
    private static String machine_name;
    public static boolean enabled = true;

    LocalServerImpl impl = new LocalServerImpl();

    @Override
    public String machine_name() {
        return machine_name;
    }

    @Override
    public boolean status() {
        return enabled;
    }

    @Override
    public void register_gate(String name, String ior) {
        machine_name = name;

        Machines machines = new Machines();
        machines.ior = ior;
        machines.machine_name = name;
        machines.machine_type = MACHINETYPE;
        machines.enabled = true;

        impl.add_exit_gate(machines);
    }

    @Override
    public void vehicle_exited(Date date, Time time, String registration) {
        System.out.println("Vehicle exited with registration: " + registration + " at " + date.days + "/" + date.months + "/" + date.years + "   " + time.hours + ":" + time.minutes + ":" + time.seconds);
        CarPark.VehicleEvent vehicleEvent = new CarPark.VehicleEvent();

        vehicleEvent.registration_number = registration;
        vehicleEvent.date = date;
        vehicleEvent.time = time;
        vehicleEvent.operation = OPERATION;

        impl.vehicle_out(vehicleEvent);
    }

    @Override
    public void turn_on() {
        System.out.println("exit gate turned on");
        enabled = true;
    }

    @Override
    public void turn_off() {
        System.out.println("exit gate turned off");
        enabled = false;
    }

    @Override
    public void reset() {
        turn_on();
        turn_off();
    }
}
