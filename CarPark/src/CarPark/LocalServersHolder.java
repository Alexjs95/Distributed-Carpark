package CarPark;

/**
* CarPark/LocalServersHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Wednesday, 3 April 2019 13:51:54 o'clock BST
*/

public final class LocalServersHolder implements org.omg.CORBA.portable.Streamable
{
  public CarPark.LocalServers value = null;

  public LocalServersHolder ()
  {
  }

  public LocalServersHolder (CarPark.LocalServers initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = CarPark.LocalServersHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    CarPark.LocalServersHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return CarPark.LocalServersHelper.type ();
  }

}
