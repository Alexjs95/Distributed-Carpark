package CarPark;


/**
* CarPark/CompanyHQOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Saturday, 30 March 2019 15:52:43 o'clock GMT
*/

public interface CompanyHQOperations 
{
  CarPark.LocalServers[] server_log ();
  void raise_alarm (CarPark.VehicleEvent event);
  void register_local_server (String name, String ior, CarPark.LocalServer obj);
  CarPark.Machines[] return_machines (short server);
} // interface CompanyHQOperations
