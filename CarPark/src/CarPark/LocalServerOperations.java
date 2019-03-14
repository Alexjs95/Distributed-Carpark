package CarPark;


/**
* CarPark/LocalServerOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Thursday, 14 March 2019 10:41:31 o'clock GMT
*/

public interface LocalServerOperations 
{
  String location ();
  CarPark.VehicleEvent[] log ();
  void vehicle_in (CarPark.VehicleEvent event);
  void vehicle_out (CarPark.VehicleEvent event);
  boolean vehicle_paid (CarPark.VehicleEvent event);
  boolean vehicle_in_car_park (String registration_number);
  int return_cash_total ();
  CarPark.VehicleEvent[] return_log ();
  void add_entry_gate (String gate_name, String gate_ior);
  void add_exit_gate (String gate_name, String gate_ior);
  void add_pay_station (String station_name, String station_ior);
} // interface LocalServerOperations
