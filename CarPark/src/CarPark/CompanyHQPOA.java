package CarPark;


/**
* CarPark/CompanyHQPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Friday, 22 March 2019 10:59:46 o'clock GMT
*/

public abstract class CompanyHQPOA extends org.omg.PortableServer.Servant
 implements CarPark.CompanyHQOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("_get_server_log", new java.lang.Integer (0));
    _methods.put ("raise_alarm", new java.lang.Integer (1));
    _methods.put ("register_local_server", new java.lang.Integer (2));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // CarPark/CompanyHQ/_get_server_log
       {
         CarPark.LocalServers $result[] = null;
         $result = this.server_log ();
         out = $rh.createReply();
         CarPark.Log_of_local_serversHelper.write (out, $result);
         break;
       }

       case 1:  // CarPark/CompanyHQ/raise_alarm
       {
         CarPark.VehicleEvent event = CarPark.VehicleEventHelper.read (in);
         this.raise_alarm (event);
         out = $rh.createReply();
         break;
       }

       case 2:  // CarPark/CompanyHQ/register_local_server
       {
         CarPark.LocalServers server = CarPark.LocalServersHelper.read (in);
         this.register_local_server (server);
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:CarPark/CompanyHQ:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public CompanyHQ _this() 
  {
    return CompanyHQHelper.narrow(
    super._this_object());
  }

  public CompanyHQ _this(org.omg.CORBA.ORB orb) 
  {
    return CompanyHQHelper.narrow(
    super._this_object(orb));
  }


} // class CompanyHQPOA
