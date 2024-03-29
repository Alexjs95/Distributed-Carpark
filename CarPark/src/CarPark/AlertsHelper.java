package CarPark;


/**
* CarPark/AlertsHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Wednesday, 3 April 2019 13:51:54 o'clock BST
*/

abstract public class AlertsHelper
{
  private static String  _id = "IDL:CarPark/Alerts:1.0";

  public static void insert (org.omg.CORBA.Any a, CarPark.Alerts that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static CarPark.Alerts extract (org.omg.CORBA.Any a)
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
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [4];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "alertType",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[1] = new org.omg.CORBA.StructMember (
            "overStayedBy",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[2] = new org.omg.CORBA.StructMember (
            "serverName",
            _tcOf_members0,
            null);
          _tcOf_members0 = CarPark.VehicleEventHelper.type ();
          _members0[3] = new org.omg.CORBA.StructMember (
            "vehicleEvent",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (CarPark.AlertsHelper.id (), "Alerts", _members0);
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

  public static CarPark.Alerts read (org.omg.CORBA.portable.InputStream istream)
  {
    CarPark.Alerts value = new CarPark.Alerts ();
    value.alertType = istream.read_string ();
    value.overStayedBy = istream.read_string ();
    value.serverName = istream.read_string ();
    value.vehicleEvent = CarPark.VehicleEventHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, CarPark.Alerts value)
  {
    ostream.write_string (value.alertType);
    ostream.write_string (value.overStayedBy);
    ostream.write_string (value.serverName);
    CarPark.VehicleEventHelper.write (ostream, value.vehicleEvent);
  }

}
