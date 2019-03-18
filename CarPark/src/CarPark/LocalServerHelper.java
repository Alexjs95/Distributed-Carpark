package CarPark;


/**
* CarPark/LocalServerHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Monday, 18 March 2019 12:45:24 o'clock GMT
*/

abstract public class LocalServerHelper
{
  private static String  _id = "IDL:CarPark/LocalServer:1.0";

  public static void insert (org.omg.CORBA.Any a, CarPark.LocalServer that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static CarPark.LocalServer extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (CarPark.LocalServerHelper.id (), "LocalServer");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static CarPark.LocalServer read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_LocalServerStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, CarPark.LocalServer value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static CarPark.LocalServer narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof CarPark.LocalServer)
      return (CarPark.LocalServer)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      CarPark._LocalServerStub stub = new CarPark._LocalServerStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static CarPark.LocalServer unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof CarPark.LocalServer)
      return (CarPark.LocalServer)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      CarPark._LocalServerStub stub = new CarPark._LocalServerStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
