package CompanyHQ;


/**
* CompanyHQ/CompanyHQOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from headquarters.idl
* Thursday, 7 March 2019 11:50:55 o'clock GMT
*/

public interface CompanyHQOperations 
{
  void raise_alarm (CompanyHQ.VehicleEvent event);
  void register_local_server (String server_name, String server_ior);
} // interface CompanyHQOperations
