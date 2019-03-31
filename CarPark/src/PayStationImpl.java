import CarPark.*;
import CarPark.LocalServer;

public class PayStationImpl extends PayStationPOA {
    public static final String MACHINETYPE = "Pay Station";
    public static final String OPERATION = "Paid";
    public static boolean enabled = true;
    private static String machine_name;
    public double cashTaken;

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
    public void register_station(String name, String ior, LocalServer obj) {
        lsImpl = obj;

        machine_name = name;
        Machines machine = new Machines();
        machine.ior = ior;
        machine.machine_name = machine_name;
        machine.machine_type = MACHINETYPE;
        machine.enabled = true;

        lsImpl.add_pay_station(machine);
    }

    @Override
    public boolean check_vehicle(String registration) {
        // checks if the car is in the car park and checks if the car has not left the car park.
        System.out.println("vehicle in carPark " + lsImpl.check_vehicle_in_car_park(registration));
        System.out.println("vehicle out of carPark " + lsImpl.check_vehicle_out_car_park(registration));
        System.out.println("vehicle paid: " + lsImpl.vehicle_paid_for(registration));
        if (lsImpl.check_vehicle_in_car_park(registration) && !lsImpl.check_vehicle_out_car_park(registration) && !lsImpl.vehicle_paid_for(registration)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean pay(String registration, Date datePaid, Time timePaid, short duration, double cost) {
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

            lsImpl.vehicle_pay(vehicleEvent);
        } else {
            System.out.println("Car not found in carPark");
        }
        return found;
    }

    @Override
    public double return_cash_total() {
        return cashTaken;
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
