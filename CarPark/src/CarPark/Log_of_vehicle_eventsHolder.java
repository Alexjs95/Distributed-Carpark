package CarPark;


/**
* CarPark/Log_of_vehicle_eventsHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Sunday, 31 March 2019 22:41:47 o'clock BST
*/

public final class Log_of_vehicle_eventsHolder implements org.omg.CORBA.portable.Streamable
{
  public CarPark.VehicleEvent value[] = null;

  public Log_of_vehicle_eventsHolder ()
  {
  }

  public Log_of_vehicle_eventsHolder (CarPark.VehicleEvent[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = CarPark.Log_of_vehicle_eventsHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    CarPark.Log_of_vehicle_eventsHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return CarPark.Log_of_vehicle_eventsHelper.type ();
  }

}
