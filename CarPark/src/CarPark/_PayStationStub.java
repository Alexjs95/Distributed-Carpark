package CarPark;


/**
* CarPark/_PayStationStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
<<<<<<< HEAD
* Tuesday, 2 April 2019 22:49:29 o'clock BST
=======
* Tuesday, 2 April 2019 12:27:24 o'clock BST
>>>>>>> 4ffa59a92a35d3ac6659213456086bc70942bfc1
*/

public class _PayStationStub extends org.omg.CORBA.portable.ObjectImpl implements CarPark.PayStation
{

  public String machine_name ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_get_machine_name", true);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return machine_name (        );
            } finally {
                _releaseReply ($in);
            }
  } // machine_name

  public boolean status ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_get_status", true);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return status (        );
            } finally {
                _releaseReply ($in);
            }
  } // status

  public void register_station (String name, String ior, CarPark.LocalServer obj)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("register_station", true);
                $out.write_string (name);
                $out.write_string (ior);
                CarPark.LocalServerHelper.write ($out, obj);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                register_station (name, ior, obj        );
            } finally {
                _releaseReply ($in);
            }
  } // register_station

  public boolean pay (String registration, short duration, double cost)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("pay", true);
                $out.write_string (registration);
                $out.write_short (duration);
                $out.write_double (cost);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return pay (registration, duration, cost        );
            } finally {
                _releaseReply ($in);
            }
  } // pay

  public double return_car_park_total ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("return_car_park_total", true);
                $in = _invoke ($out);
                double $result = $in.read_double ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return return_car_park_total (        );
            } finally {
                _releaseReply ($in);
            }
  } // return_car_park_total

  public void turn_on (String machine_name, String machine_type)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("turn_on", true);
                $out.write_string (machine_name);
                $out.write_string (machine_type);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                turn_on (machine_name, machine_type        );
            } finally {
                _releaseReply ($in);
            }
  } // turn_on

  public void turn_off (String machine_name, String machine_type)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("turn_off", true);
                $out.write_string (machine_name);
                $out.write_string (machine_type);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                turn_off (machine_name, machine_type        );
            } finally {
                _releaseReply ($in);
            }
  } // turn_off

  public void reset (String machine_name, String machine_type)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("reset", true);
                $out.write_string (machine_name);
                $out.write_string (machine_type);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                reset (machine_name, machine_type        );
            } finally {
                _releaseReply ($in);
            }
  } // reset

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:CarPark/PayStation:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _PayStationStub
