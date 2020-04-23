package com.bilgekod.naplog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity implements DailyNapAdapter.ItemClicked
{

    ArrayList<Nap> list_nap;
    static ArrayList<DailyNap> list_daily_nap;

    RecyclerView recyclerView;
    //RecyclerView.Adapter adapter;
    DailyNapAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Button btChangeDisplay;

    static final int LAUNCH_SHOWDETAILS_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        btChangeDisplay = findViewById(R.id.btChangeDisplay);
        btChangeDisplay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                boolean doShowDurations = adapter.getDoShowDurations();
                if(doShowDurations)
                {
                    btChangeDisplay.setText("SHOW DURATIONS");
                }
                else
                {
                    btChangeDisplay.setText("SHOW HOURS");
                }

                adapter.setDoShowDurations( !(doShowDurations && doShowDurations) );
                adapter.notifyDataSetChanged();
            }
        });

        NapDB db = new NapDB(this);
        db.open();

       list_nap = db.getData();

        db.close();

        list_daily_nap = mergeNapsToDailyNaps(list_nap);

        recyclerView = findViewById(R.id.naplist);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new DailyNapAdapter(this, list_daily_nap);

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SHOWDETAILS_ACTIVITY)
        {
            if(resultCode == Globals.INTENT_RESULT_OK)
            {
                //Toast.makeText(ShowActivity.this, "DEBUG: intent result OK", Toast.LENGTH_LONG).show();

                NapDB db = new NapDB(this);
                db.open();
                list_nap = db.getData();
                db.close();

                list_daily_nap = mergeNapsToDailyNaps(list_nap);

                adapter.updateDailyNaps(list_daily_nap);
                adapter.notifyDataSetChanged();
            }
            if (resultCode == Globals.INTENT_RESULT_CANCELLED)
            {
                //Toast.makeText(ShowActivity.this, "DEBUG: intent result CANCELLED", Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
            }
            if (resultCode == Globals.INTENT_RESULT_ERROR)
            {
                //Toast.makeText(ShowActivity.this, "DEBUG: intent result ERROR", Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
            }
        }
    }//onActivityResult

    @Override
    public void onItemClicked(int index)
    {
        Intent intent = new Intent(ShowActivity.this, ShowDetailsActivity.class);
        intent.putExtra("index", index);
        startActivityForResult(intent, LAUNCH_SHOWDETAILS_ACTIVITY);
    }

    public ArrayList<DailyNap> mergeNapsToDailyNaps(ArrayList<Nap> list_nap)
    {
        ArrayList<DailyNap> list_dailyNap = new ArrayList<>();

        String date = "";
        for(int i=0; i<list_nap.size(); i++)
        {
            if(!date.equals(list_nap.get(i).getDateStart()))
            {
                // New date found, set book keeping date
                date = list_nap.get(i).getDateStart();

                // Create a new daily nap for the new date
                DailyNap dailyNap = new DailyNap();

                // Add the daily nap to the list
                list_dailyNap.add(dailyNap);

                // Merge the current nap to the daily nap
                list_dailyNap.get(list_dailyNap.size()-1).mergeNap(list_nap.get(i));
            }
            else
            {
                list_dailyNap.get(list_dailyNap.size()-1).mergeNap(list_nap.get(i));
            }
        }

        return list_dailyNap;
    }
}
