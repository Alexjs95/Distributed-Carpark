package CarPark;


/**
* CarPark/PayStationOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Saturday, 30 March 2019 15:52:43 o'clock GMT
*/

public interface PayStationOperations 
{
  String machine_name ();
  boolean status ();
  void register_station (String name, String ior, CarPark.LocalServer obj);
  boolean check_vehicle (String registration);
  boolean pay (String registration, CarPark.Date datePaid, CarPark.Time timePaid, short duration, double cost);
  double return_cash_total ();
  void turn_on ();
  void turn_off ();
  void reset ();
} // interface PayStationOperations
