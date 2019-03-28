package CarPark;


/**
* CarPark/LocalServerOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Thursday, 28 March 2019 12:35:03 o'clock GMT
*/

public interface LocalServerOperations 
{
  String location ();
  CarPark.VehicleEvent[] events_log ();
  CarPark.Machines[] machine_log ();
  void register_server (String location, String ior);
  void vehicle_in (CarPark.VehicleEvent event);
  void vehicle_out (CarPark.VehicleEvent event);
  boolean vehicle_pay (CarPark.VehicleEvent event);
  boolean vehicle_paid_for (String registration_number);
  boolean vehicle_in_car_park (String registration_number);
  double return_cash_total ();
  CarPark.VehicleEvent[] return_log ();
  void add_entry_gate (CarPark.Machines machine);
  void add_exit_gate (CarPark.Machines machine);
  void add_pay_station (CarPark.Machines machine);
} // interface LocalServerOperations
