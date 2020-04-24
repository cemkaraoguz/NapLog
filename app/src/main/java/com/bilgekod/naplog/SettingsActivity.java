package com.bilgekod.naplog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity
{

    EditText etStartDayHour;
    EditText etStartDayMinute;
    Button btStartDayChange;
    EditText etEndDayHour;
    EditText etEndDayMinute;
    Button btEndDayChange;

    String startofday_h_str;
    String startofday_m_str;
    String endofday_h_str;
    String endofday_m_str;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        etStartDayHour = findViewById(R.id.etStartDayHour);
        etStartDayMinute = findViewById(R.id.etStartDayMinute);
        btStartDayChange = findViewById(R.id.btStartDayChange);

        float startofday_h = Globals.hour_startofday/60;
        float startofday_m = Globals.hour_startofday%60;

        startofday_h_str = ""+(int)startofday_h;
        startofday_m_str = ""+(int)startofday_m;

        if(startofday_h_str.length()==1)
        {
            startofday_h_str = "0"+startofday_h_str;
        }

        if(startofday_m_str.length()==1)
        {
            startofday_m_str = "0"+startofday_m_str;
        }

        etStartDayHour.setText(startofday_h_str);
        etStartDayMinute.setText(startofday_m_str);

        btStartDayChange.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(etStartDayHour.getText().toString().isEmpty() || etStartDayMinute.getText().toString().isEmpty())
                {
                    Toast.makeText(SettingsActivity.this, "Please enter all fields!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    float newHour = Float.parseFloat(etStartDayHour.getText().toString().trim());
                    float newMinute = Float.parseFloat(etStartDayMinute.getText().toString().trim());
                    float newTime = newHour*60 + newMinute;

                    if(newHour<0 || newHour>23)
                    {
                        Toast.makeText(SettingsActivity.this, "Please enter a valid hour!", Toast.LENGTH_SHORT).show();
                        etStartDayHour.setText(startofday_h_str);
                        etStartDayMinute.setText(startofday_m_str);
                    }
                    else if(newMinute<0 || newMinute>59)
                    {
                        Toast.makeText(SettingsActivity.this, "Please enter a valid minute!", Toast.LENGTH_SHORT).show();
                        etStartDayHour.setText(startofday_h_str);
                        etStartDayMinute.setText(startofday_m_str);
                    }
                    else if(newTime>=Globals.hour_endofday)
                    {
                        Toast.makeText(SettingsActivity.this, "Start of the day cannot be ahead of the end of the day!", Toast.LENGTH_SHORT).show();
                        etStartDayHour.setText(startofday_h_str);
                        etStartDayMinute.setText(startofday_m_str);
                    }
                    else
                    {
                        Globals.setStartOfDay(SettingsActivity.this, newTime);
                    }

                }
            }
        });

        etEndDayHour = findViewById(R.id.etEndDayHour);
        etEndDayMinute = findViewById(R.id.etEndDayMinute);
        btEndDayChange = findViewById(R.id.btEndDayChange);

        float endofday_h = Globals.hour_endofday/60;
        float endofday_m = Globals.hour_endofday%60;

        endofday_h_str = ""+(int)endofday_h;
        endofday_m_str = ""+(int)endofday_m;

        if(endofday_h_str.length()==1)
        {
            endofday_h_str = "0"+endofday_h_str;
        }

        if(endofday_m_str.length()==1)
        {
            endofday_m_str = "0"+endofday_m_str;
        }

        etEndDayHour.setText(endofday_h_str);
        etEndDayMinute.setText(endofday_m_str);

        btEndDayChange.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(etEndDayHour.getText().toString().isEmpty() || etEndDayMinute.getText().toString().isEmpty())
                {
                    Toast.makeText(SettingsActivity.this, "Please enter all fields!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    float newHour = Float.parseFloat(etEndDayHour.getText().toString().trim());
                    float newMinute = Float.parseFloat(etEndDayMinute.getText().toString().trim());
                    float newTime = newHour*60 + newMinute;

                    if(newHour<0 || newHour>23)
                    {
                        Toast.makeText(SettingsActivity.this, "Please enter a valid hour!", Toast.LENGTH_SHORT).show();
                        etEndDayHour.setText(endofday_h_str);
                        etEndDayMinute.setText(endofday_m_str);
                    }
                    else if(newMinute<0 || newMinute>59)
                    {
                        Toast.makeText(SettingsActivity.this, "Please enter a valid minute!", Toast.LENGTH_SHORT).show();
                        etEndDayHour.setText(endofday_h_str);
                        etEndDayMinute.setText(endofday_m_str);
                    }
                    else if(newTime<=Globals.hour_startofday)
                    {
                        Toast.makeText(SettingsActivity.this, "End of the day cannot be before the start of the day!", Toast.LENGTH_SHORT).show();
                        etEndDayHour.setText(endofday_h_str);
                        etEndDayMinute.setText(endofday_m_str);
                    }
                    else
                    {
                        Globals.setEndOfDay(SettingsActivity.this, newTime);
                    }

                }
            }
        });
    }
}
