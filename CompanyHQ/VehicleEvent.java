package CompanyHQ;


/**
* CompanyHQ/VehicleEvent.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from headquarters.idl
* Thursday, 7 March 2019 11:50:55 o'clock GMT
*/

public final class VehicleEvent implements org.omg.CORBA.portable.IDLEntity
{
  public int time = (int)0;
  public int date = (int)0;
  public String registration_number = null;

  public VehicleEvent ()
  {
  } // ctor

  public VehicleEvent (int _time, int _date, String _registration_number)
  {
    time = _time;
    date = _date;
    registration_number = _registration_number;
  } // ctor

} // class VehicleEvent