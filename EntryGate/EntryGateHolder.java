package EntryGate;

/**
* EntryGate/EntryGateHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from entry.idl
* Thursday, 7 March 2019 11:50:35 o'clock GMT
*/

public final class EntryGateHolder implements org.omg.CORBA.portable.Streamable
{
  public EntryGate.EntryGate value = null;

  public EntryGateHolder ()
  {
  }

  public EntryGateHolder (EntryGate.EntryGate initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = EntryGate.EntryGateHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    EntryGate.EntryGateHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return EntryGate.EntryGateHelper.type ();
  }

}
