import CarPark.*;

public class PayStationImpl extends PayStationPOA {
    public static final String MACHINETYPE = "Pay Station";
    public static final String OPERATION = "Paid";
    private static String machine_name;

    LocalServerImpl impl = new LocalServerImpl();

    @Override
    public String machine_name() {
        return machine_name;
    }

    @Override
    public void register_station(String name) {
        machine_name = name;

        Machines machines = new Machines();
        machines.ior = "";
        machines.machine_name = name;
        machines.machine_type = MACHINETYPE;
        impl.add_entry_gate(machines);
    }

    @Override
    public void turn_on() {

    }

    @Override
    public void turn_off() {

    }

    @Override
    public void reset() {

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

            impl.vehicle_paid(vehicleEvent);

        } else {
            System.out.println("Car not found in carPark");
        }
        return found;
    }



    @Override
    public int return_cash_total() {
        return 0;
    }
}
