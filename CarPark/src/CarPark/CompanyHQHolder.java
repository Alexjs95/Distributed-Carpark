package CarPark;

/**
* CarPark/CompanyHQHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Monday, 18 March 2019 12:45:24 o'clock GMT
*/

public final class CompanyHQHolder implements org.omg.CORBA.portable.Streamable
{
  public CarPark.CompanyHQ value = null;

  public CompanyHQHolder ()
  {
  }

  public CompanyHQHolder (CarPark.CompanyHQ initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = CarPark.CompanyHQHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    CarPark.CompanyHQHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return CarPark.CompanyHQHelper.type ();
  }

}
