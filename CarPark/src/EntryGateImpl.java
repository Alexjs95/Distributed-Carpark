import CarPark.EntryGatePOA;

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
    public void turn_off() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void vehicle_entered(int date, int time, String registration) {
        System.out.println("Vehicle entered with registration: " + registration);
    }
}
