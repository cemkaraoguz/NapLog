package com.bilgekod.naplog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity
{

    String userid ;
    String rowID;
    Boolean isEditMode;
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

    DatePicker datePicker;
    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        isEditMode = getIntent().getBooleanExtra("isEditMode", false);
        userid = getIntent().getStringExtra("userid");
        rowID = getIntent().getStringExtra("rowID");

        date_start_year = getIntent().getStringExtra("date_start_year");
        date_start_month = getIntent().getStringExtra("date_start_month");
        date_start_day = getIntent().getStringExtra("date_start_day");
        time_start_hour = getIntent().getStringExtra("time_start_hour");
        time_start_minute = getIntent().getStringExtra("time_start_minute");
        date_stop_year = getIntent().getStringExtra("date_stop_year");
        date_stop_month = getIntent().getStringExtra("date_stop_month");
        date_stop_day = getIntent().getStringExtra("date_stop_day");
        time_stop_hour = getIntent().getStringExtra("time_stop_hour");
        time_stop_minute = getIntent().getStringExtra("time_stop_minute");

        startDialogSystem();
    }

    private void startDialogSystem()
    {
        // TODO: change this horrible nested dialogs! Fragments?
        // Set start date
        final View dialogView_date = View.inflate(UpdateActivity.this, R.layout.date_picker, null);
        final AlertDialog alertDialog_date_start = new AlertDialog.Builder(UpdateActivity.this).create();

        datePicker = (DatePicker) dialogView_date.findViewById(R.id.date_picker);

        if(date_start_year!=null && date_start_month!=null && date_start_day!=null)
        {
            datePicker.updateDate(Integer.parseInt(date_start_year), Integer.parseInt(date_start_month), Integer.parseInt(date_start_day));
        }

        Button btSetDate = dialogView_date.findViewById(R.id.btSetDate);
        btSetDate.setText("SET NAP START DATE");
        btSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                date_start_year = ""+datePicker.getYear();
                date_start_month = ""+datePicker.getMonth();
                date_start_day = ""+datePicker.getDayOfMonth();

                //Toast.makeText(UpdateActivity.this, "Start Date: " + date_start_year + "-" + date_start_month + "-" + date_start_day, Toast.LENGTH_SHORT).show();

                alertDialog_date_start.dismiss();



                // Set start time
                final View dialogView_time = View.inflate(UpdateActivity.this, R.layout.time_picker, null);
                final AlertDialog alertDialog_time_start = new AlertDialog.Builder(UpdateActivity.this).create();

                timePicker = (TimePicker) dialogView_time.findViewById(R.id.time_picker);

                if(time_start_hour!=null && time_start_minute!=null)
                {
                    timePicker.setCurrentHour(Integer.parseInt(time_start_hour));
                    timePicker.setCurrentMinute(Integer.parseInt(time_start_minute));
                }

                Button btSetTime = dialogView_time.findViewById(R.id.btSetTime);
                btSetTime.setText("SET NAP START TIME");
                btSetTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        time_start_hour = ""+timePicker.getCurrentHour();
                        time_start_minute = ""+timePicker.getCurrentMinute();

                        //Toast.makeText(UpdateActivity.this, "Start Time: " + time_start_hour + ":" + time_start_minute, Toast.LENGTH_SHORT).show();

                        alertDialog_time_start.dismiss();



                        // Set stop date
                        final View dialogView_date_stop = View.inflate(UpdateActivity.this, R.layout.date_picker, null);
                        final AlertDialog alertDialog_date_stop = new AlertDialog.Builder(UpdateActivity.this).create();

                        datePicker = (DatePicker) dialogView_date_stop.findViewById(R.id.date_picker);

                        if(date_stop_year!=null && date_stop_month!=null && date_stop_day!=null)
                        {
                            datePicker.updateDate(Integer.parseInt(date_stop_year), Integer.parseInt(date_stop_month), Integer.parseInt(date_stop_day));
                        }

                        Button btSetDate = dialogView_date_stop.findViewById(R.id.btSetDate);
                        btSetDate.setText("SET NAP STOP DATE");
                        btSetDate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                date_stop_year = ""+datePicker.getYear();
                                date_stop_month = ""+datePicker.getMonth();
                                date_stop_day = ""+datePicker.getDayOfMonth();

                                //Toast.makeText(UpdateActivity.this, "Stop Date: " + date_stop_year + "-" + date_stop_month + "-" + date_stop_day, Toast.LENGTH_SHORT).show();

                                alertDialog_date_stop.dismiss();



                                // Set start time
                                final View dialogView_time_stop = View.inflate(UpdateActivity.this, R.layout.time_picker, null);
                                final AlertDialog alertDialog_time_stop = new AlertDialog.Builder(UpdateActivity.this).create();

                                timePicker = (TimePicker) dialogView_time_stop.findViewById(R.id.time_picker);

                                if(time_stop_hour!=null && time_stop_minute!=null)
                                {
                                    timePicker.setCurrentHour(Integer.parseInt(time_stop_hour));
                                    timePicker.setCurrentMinute(Integer.parseInt(time_stop_minute));
                                }

                                Button btSetTime = dialogView_time_stop.findViewById(R.id.btSetTime);
                                btSetTime.setText("SET NAP STOP TIME");
                                btSetTime.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        time_stop_hour = ""+timePicker.getCurrentHour();
                                        time_stop_minute = ""+timePicker.getCurrentMinute();

                                        //Toast.makeText(UpdateActivity.this, "Stop Time: " + time_stop_hour + ":" + time_stop_minute, Toast.LENGTH_SHORT).show();

                                        alertDialog_time_stop.dismiss();

                                        updateDatabase();
                                    }});
                                alertDialog_time_stop.setView(dialogView_time_stop);
                                alertDialog_time_stop.show();

                                alertDialog_time_stop.setOnCancelListener(new DialogInterface.OnCancelListener()
                                {
                                    @Override
                                    public void onCancel(DialogInterface dialog)
                                    {
                                        Intent returnIntent = new Intent();
                                        //returnIntent.putExtra("result", Globals.INTENT_RESULT_CANCELLED);
                                        setResult(Globals.INTENT_RESULT_CANCELLED, returnIntent);
                                        UpdateActivity.this.finish();
                                    }
                                });

                            }});
                        alertDialog_date_stop.setView(dialogView_date_stop);
                        alertDialog_date_stop.show();

                        alertDialog_date_stop.setOnCancelListener(new DialogInterface.OnCancelListener()
                        {
                            @Override
                            public void onCancel(DialogInterface dialog)
                            {
                                Intent returnIntent = new Intent();
                                //returnIntent.putExtra("result", Globals.INTENT_RESULT_CANCELLED);
                                setResult(Globals.INTENT_RESULT_CANCELLED, returnIntent);
                                UpdateActivity.this.finish();
                            }
                        });

                    }});
                alertDialog_time_start.setView(dialogView_time);
                alertDialog_time_start.show();

                alertDialog_time_start.setOnCancelListener(new DialogInterface.OnCancelListener()
                {
                    @Override
                    public void onCancel(DialogInterface dialog)
                    {
                        Intent returnIntent = new Intent();
                        //returnIntent.putExtra("result", Globals.INTENT_RESULT_CANCELLED);
                        setResult(Globals.INTENT_RESULT_CANCELLED, returnIntent);
                        UpdateActivity.this.finish();
                    }
                });

            }});

        alertDialog_date_start.setView(dialogView_date);
        alertDialog_date_start.show();

        alertDialog_date_start.setOnCancelListener(new DialogInterface.OnCancelListener()
        {
            @Override
            public void onCancel(DialogInterface dialog)
            {
                Intent returnIntent = new Intent();
                //returnIntent.putExtra("result", Globals.INTENT_RESULT_CANCELLED);
                setResult(Globals.INTENT_RESULT_CANCELLED, returnIntent);
                UpdateActivity.this.finish();
            }
        });
    }

    private void updateDatabase()
    {
        if(isEditMode)
        {
            Nap nap = new Nap(Globals.getUserid(),
                    ""+date_start_year,
                    ""+date_start_month,
                    ""+date_start_day,
                    ""+time_start_hour,
                    ""+time_start_minute,
                    ""+date_stop_year,
                    ""+date_stop_month,
                    ""+date_stop_day,
                    ""+time_stop_hour,
                    ""+time_stop_minute);

            if(nap.isValid())
            {
                NapDB db = new NapDB(UpdateActivity.this);
                db.open();
                db.updateEntry(rowID, nap);
                db.close();

                Toast.makeText(UpdateActivity.this, "Nap updated!", Toast.LENGTH_SHORT).show();

                Intent returnIntent = new Intent();
                int position = getIntent().getIntExtra("position", -1);
                returnIntent.putExtra("position", position);
                setResult(Globals.INTENT_RESULT_OK, returnIntent);
                UpdateActivity.this.finish();
            }
            else
            {
                Toast.makeText(UpdateActivity.this, "ERROR: Nap is not valid!", Toast.LENGTH_SHORT).show();

                Intent returnIntent = new Intent();
                //returnIntent.putExtra("result", Globals.INTENT_RESULT_ERROR);
                setResult(Globals.INTENT_RESULT_ERROR, returnIntent);
                UpdateActivity.this.finish();
            }
        }
        else
        {
            Nap nap = new Nap(Globals.getUserid(),
                    ""+date_start_year,
                    ""+date_start_month,
                    ""+date_start_day,
                    ""+time_start_hour,
                    ""+time_start_minute,
                    ""+date_stop_year,
                    ""+date_stop_month,
                    ""+date_stop_day,
                    ""+time_stop_hour,
                    ""+time_stop_minute);

            if(nap.isValid())
            {
                NapDB db = new NapDB(UpdateActivity.this);
                db.open();
                db.createEntry(nap);
                db.close();

                Toast.makeText(UpdateActivity.this, "Nap created!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(UpdateActivity.this, "ERROR: Nap is not valid!", Toast.LENGTH_SHORT).show();
            }
        }

        UpdateActivity.this.finish();
    }
}
