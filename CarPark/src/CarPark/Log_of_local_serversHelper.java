package CarPark;


/**
* CarPark/Log_of_local_serversHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Monday, 1 April 2019 11:19:23 o'clock BST
*/

abstract public class Log_of_local_serversHelper
{
  private static String  _id = "IDL:CarPark/Log_of_local_servers:1.0";

  public static void insert (org.omg.CORBA.Any a, CarPark.LocalServers[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static CarPark.LocalServers[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = CarPark.LocalServersHelper.type ();
      __typeCode = org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (CarPark.Log_of_local_serversHelper.id (), "Log_of_local_servers", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static CarPark.LocalServers[] read (org.omg.CORBA.portable.InputStream istream)
  {
    CarPark.LocalServers value[] = null;
    int _len0 = istream.read_long ();
    value = new CarPark.LocalServers[_len0];
    for (int _o1 = 0;_o1 < value.length; ++_o1)
      value[_o1] = CarPark.LocalServersHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, CarPark.LocalServers[] value)
  {
    ostream.write_long (value.length);
    for (int _i0 = 0;_i0 < value.length; ++_i0)
      CarPark.LocalServersHelper.write (ostream, value[_i0]);
  }

}
