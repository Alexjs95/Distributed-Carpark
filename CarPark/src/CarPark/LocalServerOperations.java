package CarPark;


/**
* CarPark/LocalServerOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Wednesday, 3 April 2019 13:51:54 o'clock BST
*/

public interface LocalServerOperations 
{
  String server_name ();
  double cost_per_hour ();
  CarPark.VehicleEvent[] events_log ();
  CarPark.Machines[] machine_log ();
  boolean vehicle_in (CarPark.VehicleEvent event);
  boolean vehicle_out (CarPark.VehicleEvent event);
  boolean vehicle_pay (CarPark.VehicleEvent event);
  boolean vehicle_paid_for (String registration_number);
  boolean check_vehicle_in_car_park (String registration_number);
  boolean check_vehicle_out_car_park (String registration_number);
  void update_cash_total (double cash);
  double return_car_park_total ();
  int return_spaces ();
  void register_server (String name, int spaces, CarPark.CompanyHQ hq);
  void change_rate (double cost);
  void add_entry_gate (CarPark.Machines machine);
  void add_exit_gate (CarPark.Machines machine);
  void add_pay_station (CarPark.Machines machine);
  void change_machine_state (String machine_name, String machine_type, boolean state);
} // interface LocalServerOperations
