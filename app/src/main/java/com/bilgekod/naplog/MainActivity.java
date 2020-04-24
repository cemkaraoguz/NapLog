package com.bilgekod.naplog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    TextView tvNapStartInfo;
    TextView tvNapStopInfo;
    ImageView ivNapState;
    ImageView ivAdd;
    ImageView ivDatabase;
    ImageView ivSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNapStartInfo = findViewById(R.id.tvNapStartInfo);
        tvNapStopInfo = findViewById(R.id.tvNapStopInfo);
        ivNapState = findViewById(R.id.ivNapState);
        ivAdd = findViewById(R.id.ivAdd);
        ivDatabase = findViewById(R.id.ivDatabase);
        ivSettings = findViewById(R.id.ivSettings);

        tvNapStopInfo.setVisibility(View.INVISIBLE);

        updateNapStateInfo();

        ivNapState.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Globals.changeState(MainActivity.this);
                updateNapStateInfo();
            }
        });
    }

    public void btnSettings(View v)
    {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    public void btnAdd(View v)
    {
        startActivity(new Intent(this, UpdateActivity.class));
    }

    public void btnShow(View v)
    {
        startActivity(new Intent(this, ShowActivity.class));
    }

    public void updateNapStateInfo()
    {
        if(Globals.getState()==Globals.STATE_WAKE)
        {
            ivNapState.setImageResource(R.mipmap.naplog_awake_foreground);
            ivNapState.setBackgroundColor(Color.TRANSPARENT);

            if(Globals.getStopDate()!=null && Globals.getStopHour()!=null)
            {
                //tvNapStopInfo.setText("Nap finished on " + Globals.getStopDate() + " at " + Globals.getStopHour());
                tvNapStopInfo.setText("Nap finished at " + Globals.getStopHour());
                tvNapStopInfo.setVisibility(View.VISIBLE);

                addNapDialog();
            }
            else
            {
                tvNapStartInfo.setText("Click to start recording a nap");
                tvNapStopInfo.setVisibility(View.INVISIBLE);
            }

        }
        else if(Globals.getState()==Globals.STATE_SLEEP)
        {
            ivNapState.setImageResource(R.mipmap.naplog_sleep_foreground);
            ivNapState.setBackgroundColor(Color.TRANSPARENT);

            if(Globals.getStartDate()!=null && Globals.getStartHour()!=null)
            {
                //tvNapStartInfo.setText("Nap started on " + Globals.getStartDate() + " at " + Globals.getStartHour());
                tvNapStartInfo.setText("Nap started at " + Globals.getStartHour());
                tvNapStopInfo.setVisibility(View.INVISIBLE);
            }
            else
            {

            }
        }
    }

    public void addNapDialog()
    {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setMessage("Do you wish to add this nap?\n" + "(you can edit or delete it afterwards...)");

        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Nap nap = new Nap(Globals.getUserid(),
                        ""+Globals.getStart_year(),
                        ""+Globals.getStart_month(),
                        ""+Globals.getStart_day(),
                        ""+Globals.getStart_hour(),
                        ""+Globals.getStart_minute(),
                        ""+Globals.getStop_year(),
                        ""+Globals.getStop_month(),
                        ""+Globals.getStop_day(),
                        ""+Globals.getStop_hour(),
                        ""+Globals.getStop_minute());

                if(nap.isValid())
                {
                    NapDB db = new NapDB(MainActivity.this);
                    db.open();
                    db.createEntry(nap);
                    db.close();

                    Toast.makeText(MainActivity.this, "Nap added!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "ERROR: Nap is not valid!", Toast.LENGTH_SHORT).show();
                }

                Globals.resetState(MainActivity.this);
                tvNapStartInfo.setText("Click to start recording a nap");
                tvNapStopInfo.setVisibility(View.INVISIBLE);
            }
        });
        dialog.setNegativeButton("NO", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(MainActivity.this, "Nap cancelled!", Toast.LENGTH_SHORT).show();

                Globals.resetState(MainActivity.this);
                tvNapStartInfo.setText("Click to start recording a nap");
                tvNapStopInfo.setVisibility(View.INVISIBLE);
            }
        });
        dialog.show();

    }
}
