package CarPark;


/**
* CarPark/ExitGateOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Saturday, 30 March 2019 15:52:43 o'clock GMT
*/

public interface ExitGateOperations 
{
  String machine_name ();
  boolean status ();
  void register_gate (String name, String ior, CarPark.LocalServer obj);
  void vehicle_exited (CarPark.Date date, CarPark.Time time, String registration);
  void turn_on ();
  void turn_off ();
  void reset ();
} // interface ExitGateOperations
