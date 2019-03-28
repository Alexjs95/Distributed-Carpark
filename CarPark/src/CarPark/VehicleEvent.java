package CarPark;


/**
* CarPark/VehicleEvent.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Thursday, 28 March 2019 12:35:03 o'clock GMT
*/

public final class VehicleEvent implements org.omg.CORBA.portable.IDLEntity
{
  public CarPark.Time time = null;
  public CarPark.Date date = null;
  public String registration_number = null;
  public short duration = (short)0;
  public double cost = (double)0;
  public String operation = null;

  public VehicleEvent ()
  {
  } // ctor

  public VehicleEvent (CarPark.Time _time, CarPark.Date _date, String _registration_number, short _duration, double _cost, String _operation)
  {
    time = _time;
    date = _date;
    registration_number = _registration_number;
    duration = _duration;
    cost = _cost;
    operation = _operation;
  } // ctor

} // class VehicleEvent
