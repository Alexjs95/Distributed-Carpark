package CarPark;


/**
* CarPark/Log_of_vehicle_eventsHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Monday, 18 March 2019 15:06:45 o'clock GMT
*/

abstract public class Log_of_vehicle_eventsHelper
{
  private static String  _id = "IDL:CarPark/Log_of_vehicle_events:1.0";

  public static void insert (org.omg.CORBA.Any a, CarPark.VehicleEvent[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static CarPark.VehicleEvent[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = CarPark.VehicleEventHelper.type ();
      __typeCode = org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (CarPark.Log_of_vehicle_eventsHelper.id (), "Log_of_vehicle_events", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static CarPark.VehicleEvent[] read (org.omg.CORBA.portable.InputStream istream)
  {
    CarPark.VehicleEvent value[] = null;
    int _len0 = istream.read_long ();
    value = new CarPark.VehicleEvent[_len0];
    for (int _o1 = 0;_o1 < value.length; ++_o1)
      value[_o1] = CarPark.VehicleEventHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, CarPark.VehicleEvent[] value)
  {
    ostream.write_long (value.length);
    for (int _i0 = 0;_i0 < value.length; ++_i0)
      CarPark.VehicleEventHelper.write (ostream, value[_i0]);
  }

}
