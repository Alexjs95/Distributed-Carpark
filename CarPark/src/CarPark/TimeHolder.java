package CarPark;

/**
* CarPark/TimeHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Saturday, 9 March 2019 13:23:48 o'clock GMT
*/

public final class TimeHolder implements org.omg.CORBA.portable.Streamable
{
  public CarPark.Time value = null;

  public TimeHolder ()
  {
  }

  public TimeHolder (CarPark.Time initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = CarPark.TimeHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    CarPark.TimeHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return CarPark.TimeHelper.type ();
  }

}
