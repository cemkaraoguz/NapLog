package com.bilgekod.naplog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NapAdapter extends RecyclerView.Adapter<NapAdapter.ViewHolder>
{
    private ArrayList<Nap> naps;

    public static ItemClicked activity;


    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvDate;
        TextView tvTime;
        ImageView ivEdit;
        ImageView ivDelete;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDelete = itemView.findViewById(R.id.ivDelete);

            ivEdit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    activity.ivEditOnClick(v, getAdapterPosition());
                }
            });

            ivDelete.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    activity.ivDeleteOnClick(v, getAdapterPosition());
                }
            });
        }
    }

    public NapAdapter(ArrayList<Nap> list, ItemClicked itemClickedActivity)
    {
        naps = list;
        activity = itemClickedActivity;
    }

    @NonNull
    @Override
    public NapAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NapAdapter.ViewHolder holder, int position)
    {
        int duration = Integer.parseInt( naps.get(position).getDuration() );
        int duration_h = duration/60;
        int duration_m = duration%60;
        String duration_h_str = ""+duration_h;
        String duration_m_str = ""+duration_m;
        String hour_suffix;
        String minute_suffix;
        String duration_str;

        /*
        if(duration_h_str.length()==1)
        {
            duration_h_str = "0"+duration_h_str;
        }

        if(duration_m_str.length()==1)
        {
            duration_m_str = "0"+duration_m_str;
        }
         */

        if(duration_h>1)
        {
            hour_suffix = "hours";
        }
        else
        {
            hour_suffix = "hour";
        }

        if(duration_m>1)
        {
            minute_suffix = "mins";
        }
        else
        {
            minute_suffix = "min";
        }

        if(duration_h>0)
        {
            duration_str = duration_h_str+" "+hour_suffix+" "+duration_m_str+" "+minute_suffix;
        }
        else
        {
            duration_str = duration_m_str+" "+minute_suffix;
        }

        /*
        int duration = Integer.parseInt(naps.get(position).getDuration());
        String sMinutes = " minute";
        if(duration>1)
        {
            sMinutes = sMinutes + "s";
        }

         */

        holder.itemView.setTag(naps.get(position));
        holder.tvDate.setText(naps.get(position).getDateStart());
        holder.tvTime.setText("At " + naps.get(position).getTimeStart() + " for " + duration_str);
    }

    @Override
    public int getItemCount()
    {
        return naps.size();
    }

    public interface ItemClicked
    {

        void ivEditOnClick(View v, int position);

        void ivDeleteOnClick(View v, int position);
    }
}
