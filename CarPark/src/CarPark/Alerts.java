package CarPark;


/**
* CarPark/Alerts.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Sunday, 31 March 2019 09:48:09 o'clock BST
*/

public final class Alerts implements org.omg.CORBA.portable.IDLEntity
{
  public String alertType = null;
  public String overStayedBy = null;
  public String serverName = null;
  public CarPark.VehicleEvent vehicleEvent = null;

  public Alerts ()
  {
  } // ctor

  public Alerts (String _alertType, String _overStayedBy, String _serverName, CarPark.VehicleEvent _vehicleEvent)
  {
    alertType = _alertType;
    overStayedBy = _overStayedBy;
    serverName = _serverName;
    vehicleEvent = _vehicleEvent;
  } // ctor

} // class Alerts
