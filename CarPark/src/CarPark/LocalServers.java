package CarPark;


/**
* CarPark/LocalServers.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Friday, 22 March 2019 12:03:52 o'clock GMT
*/

public final class LocalServers implements org.omg.CORBA.portable.IDLEntity
{
  public String location = null;
  public String ior = null;

  public LocalServers ()
  {
  } // ctor

  public LocalServers (String _location, String _ior)
  {
    location = _location;
    ior = _ior;
  } // ctor

} // class LocalServers
