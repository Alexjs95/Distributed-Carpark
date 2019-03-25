package CarPark;


/**
* CarPark/ExitGateOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Monday, 25 March 2019 14:17:31 o'clock GMT
*/

public interface ExitGateOperations 
{
  String machine_name ();
  void register_gate (String machine_name, String ior);
  void turn_on ();
  void turn_off ();
  void reset ();
  void vehicle_exited (CarPark.Date date, CarPark.Time time, String registration);
} // interface ExitGateOperations
