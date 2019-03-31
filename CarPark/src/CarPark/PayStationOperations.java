package CarPark;


/**
* CarPark/PayStationOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Sunday, 31 March 2019 22:41:47 o'clock BST
*/

public interface PayStationOperations 
{
  String machine_name ();
  boolean status ();
  void register_station (String name, String ior, CarPark.LocalServer obj);
  boolean check_vehicle (String registration);
  boolean pay (String registration, CarPark.Date datePaid, CarPark.Time timePaid, short duration, double cost);
  double return_cash_total ();
  void turn_on (String machine_name, String machine_type);
  void turn_off (String machine_name, String machine_type);
  void reset (String machine_name, String machine_type);
} // interface PayStationOperations
