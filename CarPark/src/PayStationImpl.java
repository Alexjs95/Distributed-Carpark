import CarPark.*;
import CarPark.LocalServer;

import java.time.LocalDateTime;

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
        lsImpl = obj;       // get reference to local server.
        machine_name = name;

        // Create a new machine instance.
        Machines machine = new Machines();
        machine.ior = ior;
        machine.machine_name = machine_name;
        machine.machine_type = MACHINETYPE;
        machine.enabled = true;

        lsImpl.add_pay_station(machine);        //  add machine to local server.
    }

    @Override
    public boolean pay(String registration,  short duration, double cost) {
        LocalDateTime currDate = LocalDateTime.now();

        CarPark.Date date = new CarPark.Date();
        date.days = currDate.getDayOfMonth();
        date.months = currDate.getMonthValue();
        date.years = currDate.getYear();

        CarPark.Time time = new CarPark.Time();
        time.hours = currDate.getHour();
        time.minutes = currDate.getMinute();
        time.seconds = currDate.getSecond();

        // Create an instance of vehicle event.
        CarPark.VehicleEvent vehicleEvent = new CarPark.VehicleEvent();

        vehicleEvent.registration_number = registration;
        vehicleEvent.date = date;
        vehicleEvent.time = time;
        vehicleEvent.duration = duration;
        vehicleEvent.cost = cost;
        vehicleEvent.operation = OPERATION;
        vehicleEvent.exited = false;



        // increment the cash taken for the pay station.
        cashTaken += cost;
        // increment the cash total stored in lsImpl.
        lsImpl.update_cash_total(cost);

        return lsImpl.vehicle_pay(vehicleEvent);         //  add vehicle event to local server.
    }

    @Override
    public double return_car_park_total() {
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
