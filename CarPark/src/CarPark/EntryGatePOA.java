package CarPark;


/**
* CarPark/EntryGatePOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Tuesday, 2 April 2019 12:27:24 o'clock BST
*/

public abstract class EntryGatePOA extends org.omg.PortableServer.Servant
 implements CarPark.EntryGateOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("_get_machine_name", new java.lang.Integer (0));
    _methods.put ("_get_status", new java.lang.Integer (1));
    _methods.put ("register_gate", new java.lang.Integer (2));
    _methods.put ("vehicle_entered", new java.lang.Integer (3));
    _methods.put ("turn_on", new java.lang.Integer (4));
    _methods.put ("turn_off", new java.lang.Integer (5));
    _methods.put ("reset", new java.lang.Integer (6));
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
       case 0:  // CarPark/EntryGate/_get_machine_name
       {
         String $result = null;
         $result = this.machine_name ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 1:  // CarPark/EntryGate/_get_status
       {
         boolean $result = false;
         $result = this.status ();
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 2:  // CarPark/EntryGate/register_gate
       {
         String name = in.read_string ();
         String ior = in.read_string ();
         CarPark.LocalServer obj = CarPark.LocalServerHelper.read (in);
         this.register_gate (name, ior, obj);
         out = $rh.createReply();
         break;
       }

       case 3:  // CarPark/EntryGate/vehicle_entered
       {
         String registration = in.read_string ();
         boolean $result = false;
         $result = this.vehicle_entered (registration);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 4:  // CarPark/EntryGate/turn_on
       {
         String machine_name = in.read_string ();
         String machine_type = in.read_string ();
         this.turn_on (machine_name, machine_type);
         out = $rh.createReply();
         break;
       }

       case 5:  // CarPark/EntryGate/turn_off
       {
         String machine_name = in.read_string ();
         String machine_type = in.read_string ();
         this.turn_off (machine_name, machine_type);
         out = $rh.createReply();
         break;
       }

       case 6:  // CarPark/EntryGate/reset
       {
         String machine_name = in.read_string ();
         String machine_type = in.read_string ();
         this.reset (machine_name, machine_type);
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
    "IDL:CarPark/EntryGate:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public EntryGate _this() 
  {
    return EntryGateHelper.narrow(
    super._this_object());
  }

  public EntryGate _this(org.omg.CORBA.ORB orb) 
  {
    return EntryGateHelper.narrow(
    super._this_object(orb));
  }


} // class EntryGatePOA
