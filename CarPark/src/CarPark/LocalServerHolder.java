package CarPark;

/**
* CarPark/LocalServerHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Tuesday, 2 April 2019 12:27:24 o'clock BST
*/

public final class LocalServerHolder implements org.omg.CORBA.portable.Streamable
{
  public CarPark.LocalServer value = null;

  public LocalServerHolder ()
  {
  }

  public LocalServerHolder (CarPark.LocalServer initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = CarPark.LocalServerHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    CarPark.LocalServerHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return CarPark.LocalServerHelper.type ();
  }

}
