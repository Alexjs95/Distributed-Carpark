package LocalServer;


/**
* LocalServer/Log_of_vehicle_eventsHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from server.idl
* Thursday, 7 March 2019 12:20:50 o'clock GMT
*/

public final class Log_of_vehicle_eventsHolder implements org.omg.CORBA.portable.Streamable
{
  public LocalServer.VehicleEvent value[] = null;

  public Log_of_vehicle_eventsHolder ()
  {
  }

  public Log_of_vehicle_eventsHolder (LocalServer.VehicleEvent[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = LocalServer.Log_of_vehicle_eventsHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    LocalServer.Log_of_vehicle_eventsHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return LocalServer.Log_of_vehicle_eventsHelper.type ();
  }

}
