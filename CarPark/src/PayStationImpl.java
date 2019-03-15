import CarPark.Date;
import CarPark.PayStationPOA;
import CarPark.Time;
import CarPark.VehicleEvent;

public class PayStationImpl extends PayStationPOA {

    LocalServerImpl impl = new LocalServerImpl();

    @Override
    public String machine_name() {
        return null;
    }

    @Override
    public void register_station(String machine_name) {

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
        boolean found = impl.vehicle_in_car_park(registration);
        return found;
    }

    @Override
    public boolean pay(String registration, Date datePaid, Time timePaid, short duration, String operation) {

        //boolean found = check_vehicle(registration);

       // if (found == true) {
            CarPark.VehicleEvent vehicleEvent = new CarPark.VehicleEvent();

            vehicleEvent.registration_number = registration;
            vehicleEvent.date = datePaid;
            vehicleEvent.time = timePaid;
            vehicleEvent.duration = duration;
            vehicleEvent.operation = "Paid";

            impl.vehicle_paid(vehicleEvent);

//        } else {
//            System.out.println("Car not found in carPark");
//        }

        return true;
    }

    @Override
    public int return_cash_total() {
        return 0;
    }
}
