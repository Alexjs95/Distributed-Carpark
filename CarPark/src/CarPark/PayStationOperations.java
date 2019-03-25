package CarPark;


/**
* CarPark/PayStationOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Monday, 25 March 2019 14:17:31 o'clock GMT
*/

public interface PayStationOperations 
{
  String machine_name ();
  void register_station (String machine_name, String ior);
  void turn_on ();
  void turn_off ();
  void reset ();
  boolean check_vehicle (String registration);
  boolean pay (String registration, CarPark.Date datePaid, CarPark.Time timePaid, short duration, double cost);
  double return_cash_total ();
} // interface PayStationOperations
