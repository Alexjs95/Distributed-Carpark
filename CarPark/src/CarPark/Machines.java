package CarPark;


/**
* CarPark/Machines.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Friday, 22 March 2019 10:59:45 o'clock GMT
*/

public final class Machines implements org.omg.CORBA.portable.IDLEntity
{
  public String machine_name = null;
  public String machine_type = null;
  public String ior = null;

  public Machines ()
  {
  } // ctor

  public Machines (String _machine_name, String _machine_type, String _ior)
  {
    machine_name = _machine_name;
    machine_type = _machine_type;
    ior = _ior;
  } // ctor

} // class Machines
