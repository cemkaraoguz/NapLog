package com.bilgekod.naplog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowDetailsActivity extends AppCompatActivity
{

    ArrayList<Nap> list_nap;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    static final int LAUNCH_UPDATE_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        int index = getIntent().getIntExtra("index", -1); //TODO: check validity
        list_nap = ShowActivity.list_daily_nap.get(index).getNapList();

        recyclerView = findViewById(R.id.detailednaplist);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new NapAdapter(list_nap, new NapAdapter.ItemClicked()
        {
            @Override
            public void ivEditOnClick(View v, int position)
            {
                Intent intent = new Intent(ShowDetailsActivity.this, UpdateActivity.class);
                intent.putExtra("isEditMode", true);
                intent.putExtra("userid", ""+list_nap.get(position).getUserid());
                intent.putExtra("date_start_year", ""+list_nap.get(position).getDate_start_year());
                intent.putExtra("date_start_month", ""+list_nap.get(position).getDate_start_month());
                intent.putExtra("date_start_day", ""+list_nap.get(position).getDate_start_day());
                intent.putExtra("date_stop_year", ""+list_nap.get(position).getDate_stop_year());
                intent.putExtra("date_stop_month", ""+list_nap.get(position).getDate_stop_month());
                intent.putExtra("date_stop_day", ""+list_nap.get(position).getDate_stop_day());
                intent.putExtra("time_start_hour", ""+list_nap.get(position).getTime_start_hour());
                intent.putExtra("time_start_minute", ""+list_nap.get(position).getTime_start_minute());
                intent.putExtra("time_stop_hour", ""+list_nap.get(position).getTime_stop_hour());
                intent.putExtra("time_stop_minute", ""+list_nap.get(position).getTime_stop_minute());
                intent.putExtra("rowID", list_nap.get(position).getRowID());
                intent.putExtra("position", position);
                startActivityForResult(intent, LAUNCH_UPDATE_ACTIVITY);
            }

            @Override
            public void ivDeleteOnClick(View v, final int position)
            {
                final String rowID = list_nap.get(position).getRowID();
                final AlertDialog.Builder dialog = new AlertDialog.Builder(ShowDetailsActivity.this);
                dialog.setMessage("Are you sure?");
                dialog.setPositiveButton("YES", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        NapDB db = new NapDB(ShowDetailsActivity.this);
                        db.open();
                        db.deleteEntry(rowID);
                        db.close();

                        list_nap.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                dialog.setNegativeButton("NO", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });
                dialog.show();
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_UPDATE_ACTIVITY)
        {
            if(resultCode == Globals.INTENT_RESULT_OK)
            {
                //Toast.makeText(ShowActivity.this, "DEBUG: intent result OK", Toast.LENGTH_LONG).show();

                int position = data.getIntExtra("position", -1);

                if(!(position<0))
                {
                    NapDB db = new NapDB(ShowDetailsActivity.this);
                    db.open();
                    ArrayList<Nap> updatedNap = db.getDataFromRowID(list_nap.get(position).getRowID());
                    db.close();

                    if (updatedNap.size() > 0)
                    {
                        list_nap.set(position, updatedNap.get(0));
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            if (resultCode == Globals.INTENT_RESULT_CANCELLED)
            {
                //Toast.makeText(ShowActivity.this, "DEBUG: intent result CANCELLED", Toast.LENGTH_LONG).show();
            }
            if (resultCode == Globals.INTENT_RESULT_ERROR)
            {
                //Toast.makeText(ShowDetailsActivity.this, "DEBUG: intent result ERROR", Toast.LENGTH_LONG).show();
            }
        }
    }//onActivityResult
}
