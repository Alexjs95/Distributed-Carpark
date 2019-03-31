package CarPark;


/**
* CarPark/EntryGateHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Sunday, 31 March 2019 22:41:47 o'clock BST
*/

abstract public class EntryGateHelper
{
  private static String  _id = "IDL:CarPark/EntryGate:1.0";

  public static void insert (org.omg.CORBA.Any a, CarPark.EntryGate that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static CarPark.EntryGate extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (CarPark.EntryGateHelper.id (), "EntryGate");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static CarPark.EntryGate read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_EntryGateStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, CarPark.EntryGate value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static CarPark.EntryGate narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof CarPark.EntryGate)
      return (CarPark.EntryGate)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      CarPark._EntryGateStub stub = new CarPark._EntryGateStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static CarPark.EntryGate unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof CarPark.EntryGate)
      return (CarPark.EntryGate)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      CarPark._EntryGateStub stub = new CarPark._EntryGateStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
