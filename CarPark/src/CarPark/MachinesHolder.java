package CarPark;

/**
* CarPark/MachinesHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Tuesday, 2 April 2019 22:49:29 o'clock BST
*/

public final class MachinesHolder implements org.omg.CORBA.portable.Streamable
{
  public CarPark.Machines value = null;

  public MachinesHolder ()
  {
  }

  public MachinesHolder (CarPark.Machines initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = CarPark.MachinesHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    CarPark.MachinesHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return CarPark.MachinesHelper.type ();
  }

}
