package CarPark;


/**
* CarPark/PayStationHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
<<<<<<< HEAD
* Tuesday, 2 April 2019 22:49:29 o'clock BST
=======
* Tuesday, 2 April 2019 12:27:24 o'clock BST
>>>>>>> 4ffa59a92a35d3ac6659213456086bc70942bfc1
*/

abstract public class PayStationHelper
{
  private static String  _id = "IDL:CarPark/PayStation:1.0";

  public static void insert (org.omg.CORBA.Any a, CarPark.PayStation that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static CarPark.PayStation extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (CarPark.PayStationHelper.id (), "PayStation");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static CarPark.PayStation read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_PayStationStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, CarPark.PayStation value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static CarPark.PayStation narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof CarPark.PayStation)
      return (CarPark.PayStation)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      CarPark._PayStationStub stub = new CarPark._PayStationStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static CarPark.PayStation unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof CarPark.PayStation)
      return (CarPark.PayStation)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      CarPark._PayStationStub stub = new CarPark._PayStationStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
