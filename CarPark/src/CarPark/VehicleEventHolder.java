package CarPark;

/**
* CarPark/VehicleEventHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Thursday, 21 March 2019 13:20:56 o'clock GMT
*/

public final class VehicleEventHolder implements org.omg.CORBA.portable.Streamable
{
  public CarPark.VehicleEvent value = null;

  public VehicleEventHolder ()
  {
  }

  public VehicleEventHolder (CarPark.VehicleEvent initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = CarPark.VehicleEventHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    CarPark.VehicleEventHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return CarPark.VehicleEventHelper.type ();
  }

}
