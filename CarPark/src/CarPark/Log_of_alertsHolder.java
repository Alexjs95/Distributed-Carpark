package CarPark;


/**
* CarPark/Log_of_alertsHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Tuesday, 2 April 2019 12:27:24 o'clock BST
*/

public final class Log_of_alertsHolder implements org.omg.CORBA.portable.Streamable
{
  public CarPark.Alerts value[] = null;

  public Log_of_alertsHolder ()
  {
  }

  public Log_of_alertsHolder (CarPark.Alerts[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = CarPark.Log_of_alertsHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    CarPark.Log_of_alertsHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return CarPark.Log_of_alertsHelper.type ();
  }

}
