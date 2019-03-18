package CarPark;


/**
* CarPark/EntryGateOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Monday, 18 March 2019 12:45:23 o'clock GMT
*/

public interface EntryGateOperations 
{
  String machine_name ();
  void register_gate (String machine_name);
  void turn_on ();
  void turn_off ();
  void reset ();
  void vehicle_entered (CarPark.Date date, CarPark.Time time, String registration, String operation);
} // interface EntryGateOperations
