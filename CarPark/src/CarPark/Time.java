package CarPark;


/**
* CarPark/Time.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Friday, 22 March 2019 12:03:51 o'clock GMT
*/

public final class Time implements org.omg.CORBA.portable.IDLEntity
{
  public int seconds = (int)0;
  public int minutes = (int)0;
  public int hours = (int)0;

  public Time ()
  {
  } // ctor

  public Time (int _seconds, int _minutes, int _hours)
  {
    seconds = _seconds;
    minutes = _minutes;
    hours = _hours;
  } // ctor

} // class Time
