import CarPark.*;

public class PayStationImpl extends PayStationPOA {
    public static final String MACHINETYPE = "Pay Station";
    public static final String OPERATION = "Paid";
    public static boolean enabled = true;
    private static String machine_name;
    public double cashTaken;

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
    public void register_station(String name, String ior) {
        machine_name = name;

        Machines machines = new Machines();
        machines.ior = ior;
        machines.machine_name = name;
        machines.machine_type = MACHINETYPE;
        machines.enabled = true;

        impl.add_entry_gate(machines);
    }

    @Override
    public void turn_on() {
        System.out.println("pay station turned on");
        enabled = true;
    }

    @Override
    public void turn_off() {
        System.out.println("pay station turned off");
        enabled = false;
    }

    @Override
    public void reset() {
        turn_on();
        turn_off();
    }

    @Override
    public boolean check_vehicle(String registration) {
        return impl.vehicle_in_car_park(registration);
    }

    @Override
    public boolean pay(String registration, Date datePaid, Time timePaid, short duration, double cost) {
        System.out.println("Pay method: " + registration);
        boolean found = check_vehicle(registration);

        if (found == true) {
            CarPark.VehicleEvent vehicleEvent = new CarPark.VehicleEvent();

            vehicleEvent.registration_number = registration;
            vehicleEvent.date = datePaid;
            vehicleEvent.time = timePaid;
            vehicleEvent.duration = duration;
            vehicleEvent.cost = cost;
            vehicleEvent.operation = OPERATION;

            cashTaken += cost;

            impl.vehicle_pay(vehicleEvent);
        } else {
            System.out.println("Car not found in carPark");
        }
        return found;
    }

    @Override
    public double return_cash_total() {
        return cashTaken;
    }
}
