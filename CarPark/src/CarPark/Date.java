package CarPark;


/**
* CarPark/Date.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Monday, 18 March 2019 15:06:44 o'clock GMT
*/

public final class Date implements org.omg.CORBA.portable.IDLEntity
{
  public int days = (int)0;
  public int months = (int)0;
  public int years = (int)0;

  public Date ()
  {
  } // ctor

  public Date (int _days, int _months, int _years)
  {
    days = _days;
    months = _months;
    years = _years;
  } // ctor

} // class Date
