package com.bilgekod.naplog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DailyNapAdapter extends RecyclerView.Adapter<DailyNapAdapter.ViewHolder>
{
    private ArrayList<DailyNap> dailyNaps;

    ItemClicked activity;

    public DailyNapAdapter(Context context, ArrayList<DailyNap> list)
    {
        dailyNaps = list;
        activity = (ItemClicked) context;
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

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            tvDailyInfo = itemView.findViewById(R.id.tvDailyInfo);

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
        holder.tvDailyInfo.setText("Total nap duration on " + dailyNaps.get(position).getDateStart() + " is " + duration + sMinutes);
    }

    @Override
    public int getItemCount()
    {
        return dailyNaps.size();
    }
}
