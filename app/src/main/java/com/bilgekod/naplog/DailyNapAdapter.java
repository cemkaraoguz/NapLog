package com.bilgekod.naplog;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DailyNapAdapter extends RecyclerView.Adapter<DailyNapAdapter.ViewHolder>
{
    private ArrayList<DailyNap> dailyNaps;

    ItemClicked activity;
    Context context;
    boolean doShowDurations = true;

    public DailyNapAdapter(Context context, ArrayList<DailyNap> list)
    {
        dailyNaps = list;
        activity = (ItemClicked) context;
        this.context = context;
    }

    public void updateDailyNap(int index, DailyNap dailyNap)
    {
        if(dailyNap.getNapList().size()==0)
        {
            dailyNaps.remove(index);
        }
        else
        {
            dailyNaps.set(index, dailyNap);
        }
    }

    public void updateDailyNaps(ArrayList<DailyNap> newDailyNaps)
    {
        dailyNaps.clear();
        dailyNaps.addAll(newDailyNaps);
    }

    public interface ItemClicked
    {
        void onItemClicked(int index);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvDailyInfo;
        ImageView ivTimeline;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            tvDailyInfo = itemView.findViewById(R.id.tvDailyInfo);
            ivTimeline = itemView.findViewById(R.id.ivTimeline);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    activity.onItemClicked(dailyNaps.indexOf(v.getTag()));
                }
            });
        }
    }

    @NonNull
    @Override
    public DailyNapAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_row_layout, parent, false);

        return new DailyNapAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyNapAdapter.ViewHolder holder, int position)
    {
        int duration = dailyNaps.get(position).getTotalNapDuration();
        String sMinutes = " minute";
        if(duration>1)
        {
            sMinutes = sMinutes + "s";
        }
        holder.itemView.setTag(dailyNaps.get(position));
        //holder.tvDailyInfo.setText("Total nap duration on " + dailyNaps.get(position).getDateStart() + " is " + duration + sMinutes);
        holder.tvDailyInfo.setText(dailyNaps.get(position).getDateStart());


        float hourMin = 8*60f; // TODO to globals
        float hourMax = 20*60f; // TODO to globals

        ArrayList<Nap> list_nap = dailyNaps.get(position).getNapList();
        ArrayList<Float> list_hours = new ArrayList<>();
        ArrayList<Float> list_durations = new ArrayList<>();

        for(int i=0; i<list_nap.size();i++)
        {
            list_hours.add(Float.parseFloat(list_nap.get(i).getTime_start_hour())*60f + Float.parseFloat(list_nap.get(i).getTime_start_minute()));
            list_durations.add(Float.parseFloat(list_nap.get(i).getDuration()));
        }

        try
        {
            //TODO colors
            TimelineDrawable timelineDrawable = new TimelineDrawable(ContextCompat.getColor(context, R.color.colorPrimary), ContextCompat.getColor(context, R.color.colorAccent),
                    Color.WHITE, hourMin, hourMax, list_hours, list_durations);

            timelineDrawable.setDoShowDurations(doShowDurations);

            //rectanglesDrawable.setAlpha(80);

            holder.ivTimeline.setImageDrawable(timelineDrawable);
        }
        catch(Exception e)
        {
            //Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount()
    {
        return dailyNaps.size();
    }

    public boolean getDoShowDurations()
    {
        return doShowDurations;
    }

    public void setDoShowDurations(boolean doShowDurations)
    {
        this.doShowDurations = doShowDurations;
    }
}
