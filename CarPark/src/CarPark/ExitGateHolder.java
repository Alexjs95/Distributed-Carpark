package CarPark;

/**
* CarPark/ExitGateHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Saturday, 9 March 2019 13:23:48 o'clock GMT
*/

public final class ExitGateHolder implements org.omg.CORBA.portable.Streamable
{
  public CarPark.ExitGate value = null;

  public ExitGateHolder ()
  {
  }

  public ExitGateHolder (CarPark.ExitGate initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = CarPark.ExitGateHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    CarPark.ExitGateHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return CarPark.ExitGateHelper.type ();
  }

}
