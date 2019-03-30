package CarPark;


/**
* CarPark/LocalServerOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Saturday, 30 March 2019 12:12:12 o'clock GMT
*/

public interface LocalServerOperations 
{
  String server_name ();
  CarPark.VehicleEvent[] events_log ();
  CarPark.Machines[] machine_log ();
  void register_server (String name, String ior);
  void vehicle_in (CarPark.VehicleEvent event);
  void vehicle_out (CarPark.VehicleEvent event);
  boolean vehicle_pay (CarPark.VehicleEvent event);
  boolean vehicle_paid_for (String registration_number);
  boolean check_vehicle_in_car_park (String registration_number);
  boolean check_vehicle_out_car_park (String registration_number);
  double return_cash_total ();
  CarPark.VehicleEvent[] return_log ();
  void add_entry_gate (CarPark.Machines machine);
  void add_exit_gate (CarPark.Machines machine);
  void add_pay_station (CarPark.Machines machine);
} // interface LocalServerOperations
