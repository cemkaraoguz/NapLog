package com.bilgekod.naplog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;

public class NapDB
{
    public static final String KEY_ROWID = "_id";
    public static final String KEY_USERID = "_userid";
    public static final String KEY_DATE_START_YEAR = "_date_start_year";
    public static final String KEY_DATE_START_MONTH = "_date_start_month";
    public static final String KEY_DATE_START_DAY = "_date_start_day";
    public static final String KEY_DATE_STOP_YEAR = "_date_stop_year";
    public static final String KEY_DATE_STOP_MONTH = "_date_stop_month";
    public static final String KEY_DATE_STOP_DAY = "_date_stop_day";
    public static final String KEY_TIME_START_HOUR = "_time_start_hour";
    public static final String KEY_TIME_START_MINUTE = "_time_start_minute";
    public static final String KEY_TIME_STOP_HOUR = "_time_stop_hour";
    public static final String KEY_TIME_STOP_MINUTE = "_time_stop_minute";
    public static final String KEY_DURATION = "_duration";

    private final String DATABASE_NAME = "NapDB";
    private final String DATABASE_TABLE = "NapTable";
    private final Integer DATABASE_VERSION = 1;

    private DBHelper m_helper;
    private final Context m_context;
    private SQLiteDatabase m_database;

    public NapDB(Context context)
    {
        m_context = context;
    }

    private class DBHelper extends SQLiteOpenHelper
    {

        public DBHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            /*

            CREATE TABLE ContactsTable (_id INTEGER PRIMARY KEY AUTOINCREMENT,
                _userid TEXT NOT NULL,
                _date_start_year TEXT NOT NULL,
                _date_start_month TEXT NOT NULL,
                _date_start_day TEXT NOT NULL,
                _date_stop_year TEXT NOT NULL,
                _date_stop_month TEXT NOT NULL,
                _date_stop_day TEXT NOT NULL,
                _time_start_hour TEXT NOT NULL,
                _time_start_minute TEXT NOT NULL,
                _time_stop_hour TEXT NOT NULL,
                _time_stop_minute TEXT NOT NULL,
                _duration TEXT NOT NULL);

            */

            String sqlCode = "CREATE TABLE " + DATABASE_TABLE + " (" +
                    KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    KEY_USERID + " TEXT NOT NULL," +
                    KEY_DATE_START_YEAR + " TEXT NOT NULL," +
                    KEY_DATE_START_MONTH + " TEXT NOT NULL," +
                    KEY_DATE_START_DAY + " TEXT NOT NULL," +
                    KEY_DATE_STOP_YEAR + " TEXT NOT NULL," +
                    KEY_DATE_STOP_MONTH + " TEXT NOT NULL," +
                    KEY_DATE_STOP_DAY + " TEXT NOT NULL," +
                    KEY_TIME_START_HOUR + " TEXT NOT NULL," +
                    KEY_TIME_START_MINUTE + " TEXT NOT NULL," +
                    KEY_TIME_STOP_HOUR + " TEXT NOT NULL," +
                    KEY_TIME_STOP_MINUTE + " TEXT NOT NULL," +
                    KEY_DURATION + " TEXT NOT NULL);";

            db.execSQL(sqlCode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

            onCreate(db);
        }
    }

    public NapDB open() throws SQLException
    {
        m_helper = new DBHelper(m_context);
        m_database = m_helper.getWritableDatabase();

        return this;
    }

    public void close()
    {
        m_helper.close();
    }

    public long createEntry(Nap nap)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_USERID, nap.getUserid());
        cv.put(KEY_DATE_START_YEAR, nap.getDate_start_year());
        cv.put(KEY_DATE_START_MONTH, nap.getDate_start_month());
        cv.put(KEY_DATE_START_DAY, nap.getDate_start_day());
        cv.put(KEY_DATE_STOP_YEAR, nap.getDate_stop_year());
        cv.put(KEY_DATE_STOP_MONTH, nap.getDate_stop_month());
        cv.put(KEY_DATE_STOP_DAY, nap.getDate_stop_day());
        cv.put(KEY_TIME_START_HOUR, nap.getTime_start_hour());
        cv.put(KEY_TIME_START_MINUTE, nap.getTime_start_minute());
        cv.put(KEY_TIME_STOP_HOUR, nap.getTime_stop_hour());
        cv.put(KEY_TIME_STOP_MINUTE, nap.getTime_stop_minute());
        cv.put(KEY_DURATION, nap.getDuration());

        return m_database.insert(DATABASE_TABLE, null, cv);
    }

    public ArrayList<Nap> getData()
    {
        String [] columns = new String [] {KEY_ROWID,
                KEY_USERID,
                KEY_DATE_START_YEAR,
                KEY_DATE_START_MONTH,
                KEY_DATE_START_DAY,
                KEY_DATE_STOP_YEAR,
                KEY_DATE_STOP_MONTH,
                KEY_DATE_STOP_DAY,
                KEY_TIME_START_HOUR,
                KEY_TIME_START_MINUTE,
                KEY_TIME_STOP_HOUR,
                KEY_TIME_STOP_MINUTE,
                KEY_DURATION};

        Cursor cursor = m_database.query(DATABASE_TABLE, columns, null, null, null, null, null, null);

        ArrayList<Nap> list_nap = new ArrayList<Nap>();

        int iRowID = cursor.getColumnIndex(KEY_ROWID);
        int iUserid = cursor.getColumnIndex(KEY_USERID);
        int iDateStartYear = cursor.getColumnIndex(KEY_DATE_START_YEAR);
        int iDateStartMonth = cursor.getColumnIndex(KEY_DATE_START_MONTH);
        int iDateStartDay = cursor.getColumnIndex(KEY_DATE_START_DAY);
        int iDateStopYear = cursor.getColumnIndex(KEY_DATE_STOP_YEAR);
        int iDateStopMonth = cursor.getColumnIndex(KEY_DATE_STOP_MONTH);
        int iDateStopDay = cursor.getColumnIndex(KEY_DATE_STOP_DAY);
        int iTimeStartHour = cursor.getColumnIndex(KEY_TIME_START_HOUR);
        int iTimeStartMinute = cursor.getColumnIndex(KEY_TIME_START_MINUTE);
        int iTimeStopHour = cursor.getColumnIndex(KEY_TIME_STOP_HOUR);
        int iTimeStopMinute = cursor.getColumnIndex(KEY_TIME_STOP_MINUTE);
        int iDuration = cursor.getColumnIndex(KEY_DURATION);

        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
        {
            Nap nap = new Nap(cursor.getString(iUserid),
                    cursor.getString(iDateStartYear),
                    cursor.getString(iDateStartMonth),
                    cursor.getString(iDateStartDay),
                    cursor.getString(iTimeStartHour),
                    cursor.getString(iTimeStartMinute),
                    cursor.getString(iDateStopYear),
                    cursor.getString(iDateStopMonth),
                    cursor.getString(iDateStopDay),
                    cursor.getString(iTimeStopHour),
                    cursor.getString(iTimeStopMinute),
                    cursor.getString(iDuration));

            nap.setRowID(cursor.getString(iRowID));

            list_nap.add(nap);
        }

        cursor.close();

        Collections.reverse(list_nap);

        return list_nap;
    }

    public ArrayList<Nap> getDataFromRowID(String rowID)
    {
        String [] columns = new String [] {KEY_ROWID,
                KEY_USERID,
                KEY_DATE_START_YEAR,
                KEY_DATE_START_MONTH,
                KEY_DATE_START_DAY,
                KEY_DATE_STOP_YEAR,
                KEY_DATE_STOP_MONTH,
                KEY_DATE_STOP_DAY,
                KEY_TIME_START_HOUR,
                KEY_TIME_START_MINUTE,
                KEY_TIME_STOP_HOUR,
                KEY_TIME_STOP_MINUTE,
                KEY_DURATION};

        Cursor cursor = m_database.query(DATABASE_TABLE, columns, KEY_ROWID + " = " + rowID, null, null, null, null, null);

        ArrayList<Nap> list_nap = new ArrayList<Nap>();

        int iRowID = cursor.getColumnIndex(KEY_ROWID);
        int iUserid = cursor.getColumnIndex(KEY_USERID);
        int iDateStartYear = cursor.getColumnIndex(KEY_DATE_START_YEAR);
        int iDateStartMonth = cursor.getColumnIndex(KEY_DATE_START_MONTH);
        int iDateStartDay = cursor.getColumnIndex(KEY_DATE_START_DAY);
        int iDateStopYear = cursor.getColumnIndex(KEY_DATE_STOP_YEAR);
        int iDateStopMonth = cursor.getColumnIndex(KEY_DATE_STOP_MONTH);
        int iDateStopDay = cursor.getColumnIndex(KEY_DATE_STOP_DAY);
        int iTimeStartHour = cursor.getColumnIndex(KEY_TIME_START_HOUR);
        int iTimeStartMinute = cursor.getColumnIndex(KEY_TIME_START_MINUTE);
        int iTimeStopHour = cursor.getColumnIndex(KEY_TIME_STOP_HOUR);
        int iTimeStopMinute = cursor.getColumnIndex(KEY_TIME_STOP_MINUTE);
        int iDuration = cursor.getColumnIndex(KEY_DURATION);

        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
        {
            Nap nap = new Nap(cursor.getString(iUserid),
                    cursor.getString(iDateStartYear),
                    cursor.getString(iDateStartMonth),
                    cursor.getString(iDateStartDay),
                    cursor.getString(iTimeStartHour),
                    cursor.getString(iTimeStartMinute),
                    cursor.getString(iDateStopYear),
                    cursor.getString(iDateStopMonth),
                    cursor.getString(iDateStopDay),
                    cursor.getString(iTimeStopHour),
                    cursor.getString(iTimeStopMinute),
                    cursor.getString(iDuration));

            nap.setRowID(cursor.getString(iRowID));

            list_nap.add(nap);
        }

        cursor.close();

        return list_nap;
    }

    public long deleteEntry(String rowID)
    {
        return m_database.delete(DATABASE_TABLE, KEY_ROWID + "=?", new String []{rowID});
    }

    public long updateEntry(String rowID, Nap nap)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_USERID, nap.getUserid());
        cv.put(KEY_DATE_START_YEAR, nap.getDate_start_year());
        cv.put(KEY_DATE_START_MONTH, nap.getDate_start_month());
        cv.put(KEY_DATE_START_DAY, nap.getDate_start_day());
        cv.put(KEY_DATE_STOP_YEAR, nap.getDate_stop_year());
        cv.put(KEY_DATE_STOP_MONTH, nap.getDate_stop_month());
        cv.put(KEY_DATE_STOP_DAY, nap.getDate_stop_day());
        cv.put(KEY_TIME_START_HOUR, nap.getTime_start_hour());
        cv.put(KEY_TIME_START_MINUTE, nap.getTime_start_minute());
        cv.put(KEY_TIME_STOP_HOUR, nap.getTime_stop_hour());
        cv.put(KEY_TIME_STOP_MINUTE, nap.getTime_stop_minute());
        cv.put(KEY_DURATION, nap.getDuration());

        return m_database.update(DATABASE_TABLE, cv, KEY_ROWID + "=?", new String []{rowID});
    }
}
