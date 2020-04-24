package com.bilgekod.naplog;

import java.util.Calendar;

public class Nap
{

    String userid ;
    String rowID;
    String date_start_year;
    String date_start_month;
    String date_start_day;
    String time_start_hour;
    String time_start_minute;
    String date_stop_year;
    String date_stop_month;
    String date_stop_day;
    String time_stop_hour;
    String time_stop_minute;
    String duration;

    public Nap(String userid,
               String date_start_year,
               String date_start_month,
               String date_start_day,
               String time_start_hour,
               String time_start_minute,
               String date_stop_year,
               String date_stop_month,
               String date_stop_day,
               String time_stop_hour,
               String time_stop_minute,
               String duration)
    {
        this.userid = userid;
        this.date_start_year = date_start_year;
        this.date_start_month = date_start_month;
        this.date_start_day = date_start_day;
        this.time_start_hour = time_start_hour;
        this.time_start_minute = time_start_minute;
        this.date_stop_year = date_stop_year;
        this.date_stop_month = date_stop_month;
        this.date_stop_day = date_stop_day;
        this.time_stop_hour = time_stop_hour;
        this.time_stop_minute = time_stop_minute;
        this.duration = duration;
    }

    public Nap(String userid,
               String date_start_year,
               String date_start_month,
               String date_start_day,
               String time_start_hour,
               String time_start_minute,
               String date_stop_year,
               String date_stop_month,
               String date_stop_day,
               String time_stop_hour,
               String time_stop_minute)
    {
        this.userid = userid;
        this.date_start_year = date_start_year;
        this.date_start_month = date_start_month;
        this.date_start_day = date_start_day;
        this.time_start_hour = time_start_hour;
        this.time_start_minute = time_start_minute;
        this.date_stop_year = date_stop_year;
        this.date_stop_month = date_stop_month;
        this.date_stop_day = date_stop_day;
        this.time_stop_hour = time_stop_hour;
        this.time_stop_minute = time_stop_minute;

        this.calculateDuration();
    }

    public String getUserid()
    {
        return userid;
    }

    public String getRowID()
    {
        return rowID;
    }

    public String getDate_start_year()
    {
        return date_start_year;
    }

    public String getDate_start_month()
    {
        return date_start_month;
    }

    public String getDate_start_day()
    {
        return date_start_day;
    }

    public String getTime_start_hour()
    {
        return time_start_hour;
    }

    public String getTime_start_minute()
    {
        return time_start_minute;
    }

    public String getDate_stop_year()
    {
        return date_stop_year;
    }

    public String getDate_stop_month()
    {
        return date_stop_month;
    }

    public String getDate_stop_day()
    {
        return date_stop_day;
    }

    public String getTime_stop_hour()
    {
        return time_stop_hour;
    }

    public String getTime_stop_minute()
    {
        return time_stop_minute;
    }

    public String getDuration()
    {
        return duration;
    }

    public void setRowID(String rowID)
    {
        this.rowID = rowID;
    }

    public  String getDateStart()
    {
        String sMonth = "" + (Integer.parseInt(date_start_month)+1);
        String sDay = "" + date_start_day;

        if(sMonth.length()==1)
        {
            sMonth = "0"+sMonth;
        }

        if(sDay.length()==1)
        {
            sDay = "0"+sDay;
        }

        return sDay + Globals.DATE_SEPARATOR + sMonth + Globals.DATE_SEPARATOR + date_start_year;
    }

    public  String getDateStop()
    {
        String sMonth = "" + (Integer.parseInt(date_stop_month)+1);
        String sDay = "" + date_stop_day;

        if(sMonth.length()==1)
        {
            sMonth = "0"+sMonth;
        }

        if(sDay.length()==1)
        {
            sDay = "0"+sDay;
        }

        return sDay +Globals.DATE_SEPARATOR + sMonth + Globals.DATE_SEPARATOR + date_stop_year;
    }

    public  String getTimeStart()
    {
        String sHour = "" + time_start_hour;
        String sMinute = "" + time_start_minute;

        if(sHour.length()==1)
        {
            sHour = "0"+sHour;
        }

        if(sMinute.length()==1)
        {
            sMinute = "0"+sMinute;
        }

        return sHour + Globals.TIME_SEPARATOR + sMinute;
    }

    public  String getTimeStop()
    {
        String sHour = "" + time_stop_hour;
        String sMinute = "" + time_stop_minute;

        if(sHour.length()==1)
        {
            sHour = "0"+sHour;
        }

        if(sMinute.length()==1)
        {
            sMinute = "0"+sMinute;
        }

        return sHour + Globals.TIME_SEPARATOR + sMinute;
    }

    public void calculateDuration()
    {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        calendar1.set(Integer.parseInt(this.date_start_year),
            Integer.parseInt(this.date_start_month),
            Integer.parseInt(this.date_start_day),
            Integer.parseInt(this.time_start_hour),
            Integer.parseInt(this.time_start_minute),
            0);

        calendar2.set(Integer.parseInt(this.date_stop_year),
                Integer.parseInt(this.date_stop_month),
                Integer.parseInt(this.date_stop_day),
                Integer.parseInt(this.time_stop_hour),
                Integer.parseInt(this.time_stop_minute),
                0);

        long miliSecondForDate1 = calendar1.getTimeInMillis();
        long miliSecondForDate2 = calendar2.getTimeInMillis();

        long diffInMilis = miliSecondForDate2 - miliSecondForDate1;

        long diffInMinute = diffInMilis / (60 * 1000);

        this.duration = ""+diffInMinute;
    }

    public void setUserid(String userid)
    {
        this.userid = userid;
    }

    public boolean isValid()
    {
        return Integer.parseInt(this.duration)>0 && Integer.parseInt(this.duration)<24*60;
    }
}
