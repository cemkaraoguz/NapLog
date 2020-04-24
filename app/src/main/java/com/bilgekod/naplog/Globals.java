package com.bilgekod.naplog;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;
import java.util.UUID;

public class Globals extends Application
{
    public static final int INTENT_RESULT_OK = 0;
    public static final int INTENT_RESULT_ERROR = 1;
    public static final int INTENT_RESULT_CANCELLED = 2;

    public static final int STATE_SLEEP = 0;
    public static final int STATE_WAKE = 1;
    public static final String PREFS_FILENAME = "com.bilgekod.naplog.prefs";
    public static final int INT_NULL = -1;
    public static final int DATE_TIME_INVALID = -1;
    public static final String DATE_SEPARATOR = "/";
    public static final String TIME_SEPARATOR = ":";

    public static final String DEFAULT_USERID = "defaultuserid";
    public static final float DEFAULT_HOUR_STARTOFDAY = 0f;
    public static final float DEFAULT_HOUR_ENDOFDAY = 23f*60f+59f;

    private static String userid;
    public static float hour_startofday;
    public static float hour_endofday;

    private static int state;
    private static int start_year;
    private static int start_month;
    private static int start_day;
    private static int start_hour;
    private static int start_minute;
    private static int start_second;
    private static int stop_year;
    private static int stop_month;
    private static int stop_day;
    private static int stop_hour;
    private static int stop_minute;
    private static int stop_second;

    @Override
    public void onCreate()
    {
        super.onCreate();

        SharedPreferences prefs = getSharedPreferences(PREFS_FILENAME, MODE_PRIVATE);
        state = prefs.getInt("state_sleep", STATE_WAKE);
        start_year = prefs.getInt("start_year", INT_NULL);
        start_month = prefs.getInt("start_month", INT_NULL);
        start_day = prefs.getInt("start_day", INT_NULL);
        start_hour = prefs.getInt("start_hour", INT_NULL);
        start_minute = prefs.getInt("start_minute", INT_NULL);
        start_second = prefs.getInt("start_second", INT_NULL);
        stop_year = prefs.getInt("stop_year", INT_NULL);
        stop_month = prefs.getInt("stop_month", INT_NULL);
        stop_day = prefs.getInt("stop_day", INT_NULL);
        stop_hour = prefs.getInt("stop_hour", INT_NULL);
        stop_minute = prefs.getInt("stop_minute", INT_NULL);
        stop_second = prefs.getInt("stop_second", INT_NULL);

        // Parameters
        userid = prefs.getString("userid", DEFAULT_USERID);
        if(userid.equals(DEFAULT_USERID))
        {
            userid = UUID.randomUUID().toString();
            SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences(PREFS_FILENAME, MODE_PRIVATE).edit();
            editor.putString("userid", userid);
            editor.commit();
        }

        hour_startofday = prefs.getFloat("hour_startofday", Globals.DEFAULT_HOUR_STARTOFDAY);
        hour_endofday = prefs.getFloat("hour_endofday", Globals.DEFAULT_HOUR_ENDOFDAY);
    }

    public static void changeState(Context context)
    {
        if(state==STATE_SLEEP)
        {
            // Wake up
            state = STATE_WAKE;

            Calendar wakeTime = Calendar.getInstance();

            stop_year = wakeTime.get(Calendar.YEAR);
            stop_month = wakeTime.getInstance().get(Calendar.MONTH);
            stop_day = wakeTime.getInstance().get(Calendar.DAY_OF_MONTH);
            stop_hour = wakeTime.getInstance().get(Calendar.HOUR_OF_DAY);
            stop_minute = wakeTime.getInstance().get(Calendar.MINUTE);
            stop_second = wakeTime.getInstance().get(Calendar.SECOND);

            writeState(context);
        }
        else
        {
            // Go to sleep
            state = STATE_SLEEP;

            Calendar sleepTime = Calendar.getInstance();

            start_year = sleepTime.get(Calendar.YEAR);
            start_month = sleepTime.getInstance().get(Calendar.MONTH);
            start_day = sleepTime.getInstance().get(Calendar.DAY_OF_MONTH);
            start_hour = sleepTime.getInstance().get(Calendar.HOUR_OF_DAY);
            start_minute = sleepTime.getInstance().get(Calendar.MINUTE);
            start_second = sleepTime.getInstance().get(Calendar.SECOND);

            writeState(context);
        }
    }

    public static int getState()
    {
        return state;
    }

    public static String getStartDate()
    {
        if(start_year==DATE_TIME_INVALID || start_month==DATE_TIME_INVALID || start_day==DATE_TIME_INVALID)
        {
            return null;
        }
        else
        {
            String sMonth = "" + (start_month+1);
            String sDay = "" + start_day;

            if(sMonth.length()==1)
            {
                sMonth = "0"+sMonth;
            }

            if(sDay.length()==1)
            {
                sDay = "0"+sDay;
            }

            return sDay + DATE_SEPARATOR + sMonth + DATE_SEPARATOR + start_year;
        }
    }

    public static String getStartHour()
    {
        if(start_hour==DATE_TIME_INVALID || start_minute==DATE_TIME_INVALID || start_second==DATE_TIME_INVALID)
        {
            return null;
        }
        else
        {
            String sHour = "" + start_hour;
            String sMinute = "" + start_minute;

            if(sHour.length()==1)
            {
                sHour = "0"+sHour;
            }

            if(sMinute.length()==1)
            {
                sMinute = "0"+sMinute;
            }

            return sHour + TIME_SEPARATOR + sMinute;
        }
    }

    public static String getStopDate()
    {
        if(stop_year==DATE_TIME_INVALID || stop_month==DATE_TIME_INVALID || stop_day==DATE_TIME_INVALID)
        {
            return null;
        }
        else
        {
            String sMonth = "" + (stop_month+1);
            String sDay = "" + stop_day;

            if(sMonth.length()==1)
            {
                sMonth = "0"+sMonth;
            }

            if(sDay.length()==1)
            {
                sDay = "0"+sDay;
            }

            return sDay + DATE_SEPARATOR + sMonth + DATE_SEPARATOR + stop_year;
        }
    }

    public static String getStopHour()
    {
        if(stop_hour==DATE_TIME_INVALID || stop_minute==DATE_TIME_INVALID || stop_second==DATE_TIME_INVALID)
        {
            return null;
        }
        else
        {
            String sHour = "" + stop_hour;
            String sMinute = "" + stop_minute;

            if(sHour.length()==1)
            {
                sHour = "0"+sHour;
            }

            if(sMinute.length()==1)
            {
                sMinute = "0"+sMinute;
            }

            return sHour + TIME_SEPARATOR + sMinute;
        }
    }

    public static String getUserid()
    {
        return userid;
    }

    public static void resetState(Context context)
    {
        state = STATE_WAKE;
        start_year = DATE_TIME_INVALID;
        start_month = DATE_TIME_INVALID;
        start_day = DATE_TIME_INVALID;
        start_hour = DATE_TIME_INVALID;
        start_minute = DATE_TIME_INVALID;
        start_second = DATE_TIME_INVALID;
        stop_year = DATE_TIME_INVALID;
        stop_month = DATE_TIME_INVALID;
        stop_day = DATE_TIME_INVALID;
        stop_hour = DATE_TIME_INVALID;
        stop_minute = DATE_TIME_INVALID;
        stop_second = DATE_TIME_INVALID;

        writeState(context);
    }

    private static void writeState(Context context)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_FILENAME, MODE_PRIVATE).edit();
        editor.putInt("state_sleep", state);
        editor.putInt("start_year", start_year);
        editor.putInt("start_month", start_month);
        editor.putInt("start_day", start_day);
        editor.putInt("start_hour", start_hour);
        editor.putInt("start_minute", start_minute);
        editor.putInt("start_second", start_second);
        editor.putInt("stop_year", stop_year);
        editor.putInt("stop_month", stop_month);
        editor.putInt("stop_day", stop_day);
        editor.putInt("stop_hour", stop_hour);
        editor.putInt("stop_minute", stop_minute);
        editor.putInt("stop_second", stop_second);
        editor.commit();
    }

    public static int getStart_year()
    {
        return start_year;
    }

    public static int getStart_month()
    {
        return start_month;
    }

    public static int getStart_day()
    {
        return start_day;
    }

    public static int getStart_hour()
    {
        return start_hour;
    }

    public static int getStart_minute()
    {
        return start_minute;
    }

    public static int getStop_year()
    {
        return stop_year;
    }

    public static int getStop_month()
    {
        return stop_month;
    }

    public static int getStop_day()
    {
        return stop_day;
    }

    public static int getStop_hour()
    {
        return stop_hour;
    }

    public static int getStop_minute()
    {
        return stop_minute;
    }

    public static void setStartOfDay(Context context, float newtime)
    {
        hour_startofday = newtime;

        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_FILENAME, MODE_PRIVATE).edit();
        editor.putFloat("hour_startofday", hour_startofday);
        editor.commit();
    }

    public static void setEndOfDay(Context context, float newtime)
    {
        hour_endofday = newtime;

        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_FILENAME, MODE_PRIVATE).edit();
        editor.putFloat("hour_endofday", hour_endofday);
        editor.commit();
    }
}
