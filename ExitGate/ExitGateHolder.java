package ExitGate;

/**
* ExitGate/ExitGateHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from exit.idl
* Thursday, 7 March 2019 11:50:45 o'clock GMT
*/

public final class ExitGateHolder implements org.omg.CORBA.portable.Streamable
{
  public ExitGate.ExitGate value = null;

  public ExitGateHolder ()
  {
  }

  public ExitGateHolder (ExitGate.ExitGate initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = ExitGate.ExitGateHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    ExitGate.ExitGateHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return ExitGate.ExitGateHelper.type ();
  }

}