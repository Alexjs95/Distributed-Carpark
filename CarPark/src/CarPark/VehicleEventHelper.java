package CarPark;


/**
* CarPark/VehicleEventHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Sunday, 31 March 2019 09:48:09 o'clock BST
*/

abstract public class VehicleEventHelper
{
  private static String  _id = "IDL:CarPark/VehicleEvent:1.0";

  public static void insert (org.omg.CORBA.Any a, CarPark.VehicleEvent that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static CarPark.VehicleEvent extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [6];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = CarPark.TimeHelper.type ();
          _members0[0] = new org.omg.CORBA.StructMember (
            "time",
            _tcOf_members0,
            null);
          _tcOf_members0 = CarPark.DateHelper.type ();
          _members0[1] = new org.omg.CORBA.StructMember (
            "date",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[2] = new org.omg.CORBA.StructMember (
            "registration_number",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_short);
          _members0[3] = new org.omg.CORBA.StructMember (
            "duration",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_double);
          _members0[4] = new org.omg.CORBA.StructMember (
            "cost",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[5] = new org.omg.CORBA.StructMember (
            "operation",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (CarPark.VehicleEventHelper.id (), "VehicleEvent", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static CarPark.VehicleEvent read (org.omg.CORBA.portable.InputStream istream)
  {
    CarPark.VehicleEvent value = new CarPark.VehicleEvent ();
    value.time = CarPark.TimeHelper.read (istream);
    value.date = CarPark.DateHelper.read (istream);
    value.registration_number = istream.read_string ();
    value.duration = istream.read_short ();
    value.cost = istream.read_double ();
    value.operation = istream.read_string ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, CarPark.VehicleEvent value)
  {
    CarPark.TimeHelper.write (ostream, value.time);
    CarPark.DateHelper.write (ostream, value.date);
    ostream.write_string (value.registration_number);
    ostream.write_short (value.duration);
    ostream.write_double (value.cost);
    ostream.write_string (value.operation);
  }

}
