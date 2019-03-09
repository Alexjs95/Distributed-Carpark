import CarPark.Date;
import CarPark.EntryGatePOA;
import CarPark.Time;

public class EntryGateImpl extends EntryGatePOA {
    @Override
    public String machine_name() {
        return null;
    }

    @Override
    public void register_gate(String machine_name) {

    }

    @Override
    public void turn_on() {

    }

    @Override
    public void vehicle_entered(Date date, Time time, String registration) {
        System.out.println("Vehicle entered with registration: " + registration + " at " + date.days + "/" + date.months + "/" + date.years + "   " + time.hours + ":" + time.minutes + ":" + time.seconds);
    }

    @Override
    public void turn_off() {

    }

    @Override
    public void reset() {

    }
}
