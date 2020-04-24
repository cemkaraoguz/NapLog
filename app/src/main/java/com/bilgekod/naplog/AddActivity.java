package com.bilgekod.naplog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity
{

    EditText etUserid;
    EditText etDateStart;
    EditText etDateStop;
    EditText etHourStart;
    EditText etHourStop;
    Button btAdd;

    String userid ;
    String dateStart;
    String dateStop;
    String hourStart;
    String hourStop;
    String rowID;
    Boolean isEditMode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etUserid = findViewById(R.id.etUserid);
        etDateStart = findViewById(R.id.etDateStart);
        etDateStop = findViewById(R.id.etDateStop);
        etHourStart = findViewById(R.id.etHourStart);
        etHourStop = findViewById(R.id.etHourStop);
        btAdd = findViewById(R.id.btAdd);

        isEditMode = getIntent().getBooleanExtra("isEditMode", false);
        if(isEditMode)
        {
            btAdd.setText("UPDATE");

            // Get old info from intent
            userid = getIntent().getStringExtra("userid");
            dateStart = getIntent().getStringExtra("date_start");
            dateStop = getIntent().getStringExtra("date_stop");
            hourStart = getIntent().getStringExtra("hour_start");
            hourStop = getIntent().getStringExtra("hour_stop");
            rowID = getIntent().getStringExtra("rowID");

            // Set old info on the UI
            etUserid.setText(userid);
            etDateStart.setText(dateStart);
            etDateStop.setText(dateStop);
            etHourStart.setText(hourStart);
            etHourStop.setText(hourStop);

            btAdd.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    // Get updated info from the UI
                    userid = etUserid.getText().toString().trim();
                    dateStart = etDateStart.getText().toString().trim();
                    dateStop = etDateStop.getText().toString().trim();
                    hourStart = etHourStart.getText().toString().trim();
                    hourStop = etHourStop.getText().toString().trim();

                    if(userid.isEmpty() ||
                            dateStart.isEmpty() ||
                            dateStop.isEmpty() ||
                            hourStart.isEmpty() ||
                            hourStop.isEmpty() )
                    {
                        Toast.makeText(AddActivity.this, "Please enter all fields!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Nap nap = new Nap("","","","","","","","","","","","");

                        NapDB db = new NapDB(AddActivity.this);
                        db.open();
                        db.updateEntry(rowID, nap);
                        db.close();
                        Toast.makeText(AddActivity.this, "Nap updated!", Toast.LENGTH_SHORT).show();

                        AddActivity.this.finish();
                    }
                }
            });
        }
        else
        {
            btAdd.setText("ADD");

            // Get info from intent
            userid = getIntent().getStringExtra("userid");
            dateStart = getIntent().getStringExtra("date_start");
            dateStop = getIntent().getStringExtra("date_stop");
            hourStart = getIntent().getStringExtra("hour_start");
            hourStop = getIntent().getStringExtra("hour_stop");

            // Set info on the UI
            etUserid.setText(userid);
            etDateStart.setText(dateStart);
            etDateStop.setText(dateStop);
            etHourStart.setText(hourStart);
            etHourStop.setText(hourStop);

            btAdd.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    userid = etUserid.getText().toString().trim();
                    dateStart = etDateStart.getText().toString().trim();
                    dateStop = etDateStop.getText().toString().trim();
                    hourStart = etHourStart.getText().toString().trim();
                    hourStop = etHourStop.getText().toString().trim();

                    if(userid.isEmpty() ||
                            dateStart.isEmpty() ||
                            dateStop.isEmpty() ||
                            hourStart.isEmpty() ||
                            hourStop.isEmpty() )
                    {
                        Toast.makeText(AddActivity.this, "Please enter all fields!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Nap nap = new Nap("","","","","","","","","","","","");


                        NapDB db = new NapDB(AddActivity.this);
                        db.open();
                        db.createEntry(nap);
                        db.close();

                        Toast.makeText(AddActivity.this, "Nap created!", Toast.LENGTH_SHORT).show();

                        etUserid.setText("");
                        etDateStart.setText("");
                        etDateStop.setText("");
                        etHourStart.setText("");
                        etHourStop.setText("");

                        AddActivity.this.finish();
                    }
                }
            });
        }
    }
}
