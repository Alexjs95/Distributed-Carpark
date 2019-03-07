package LocalServer;


/**
* LocalServer/VehicleEventHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from server.idl
* Thursday, 7 March 2019 11:50:59 o'clock GMT
*/

abstract public class VehicleEventHelper
{
  private static String  _id = "IDL:LocalServer/VehicleEvent:1.0";

  public static void insert (org.omg.CORBA.Any a, LocalServer.VehicleEvent that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static LocalServer.VehicleEvent extract (org.omg.CORBA.Any a)
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
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [3];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[0] = new org.omg.CORBA.StructMember (
            "time",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[1] = new org.omg.CORBA.StructMember (
            "date",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[2] = new org.omg.CORBA.StructMember (
            "registration_number",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (LocalServer.VehicleEventHelper.id (), "VehicleEvent", _members0);
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

  public static LocalServer.VehicleEvent read (org.omg.CORBA.portable.InputStream istream)
  {
    LocalServer.VehicleEvent value = new LocalServer.VehicleEvent ();
    value.time = istream.read_long ();
    value.date = istream.read_long ();
    value.registration_number = istream.read_string ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, LocalServer.VehicleEvent value)
  {
    ostream.write_long (value.time);
    ostream.write_long (value.date);
    ostream.write_string (value.registration_number);
  }

}
