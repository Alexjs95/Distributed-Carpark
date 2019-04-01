import CarPark.*;
import CarPark.LocalServer;

import java.time.LocalDateTime;

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
        lsImpl = obj;       // get reference to local server.

        machine_name = name;

        // Create a new machine instance.
        Machines machine = new Machines();
        machine.ior = ior;
        machine.machine_name = machine_name;
        machine.machine_type = MACHINETYPE;
        machine.enabled = true;

        lsImpl.add_entry_gate(machine);   //  add machine to local server.
    }

    @Override
    public boolean vehicle_entered(String registration) {
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
        vehicleEvent.operation = OPERATION;

        return lsImpl.vehicle_in(vehicleEvent);     //  add vehicle event to local server.
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
