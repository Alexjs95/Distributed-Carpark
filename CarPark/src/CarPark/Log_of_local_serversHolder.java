package CarPark;


/**
* CarPark/Log_of_local_serversHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Saturday, 30 March 2019 12:12:12 o'clock GMT
*/

public final class Log_of_local_serversHolder implements org.omg.CORBA.portable.Streamable
{
  public CarPark.LocalServers value[] = null;

  public Log_of_local_serversHolder ()
  {
  }

  public Log_of_local_serversHolder (CarPark.LocalServers[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = CarPark.Log_of_local_serversHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    CarPark.Log_of_local_serversHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return CarPark.Log_of_local_serversHelper.type ();
  }

}
