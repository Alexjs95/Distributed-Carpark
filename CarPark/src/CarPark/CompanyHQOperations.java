package CarPark;


/**
* CarPark/CompanyHQOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Monday, 18 March 2019 13:20:12 o'clock GMT
*/

public interface CompanyHQOperations 
{
  void raise_alarm (CarPark.VehicleEvent event);
  void register_local_server (String server_name, String server_ior);
} // interface CompanyHQOperations
