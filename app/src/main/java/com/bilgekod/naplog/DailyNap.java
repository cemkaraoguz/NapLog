package com.bilgekod.naplog;

import java.util.ArrayList;

public class DailyNap
{
    String userid ;
    String dateStart;
    ArrayList<Nap> napList;
    int totalNapDuration;

    public DailyNap()
    {
        userid = null;
        dateStart = null;
        napList = new ArrayList<>();
        totalNapDuration = 0;
    }

    public int getTotalNapDuration()
    {
        return totalNapDuration;
    }

    public String getDateStart()
    {
        return dateStart;
    }

    public ArrayList<Nap> getNapList()
    {
        return napList;
    }

    public void mergeNap(Nap nap)
    {
        if(userid==null)
        {
            userid = nap.getUserid();
        }
        else
        {
            // TODO: check if userids match
        }

        if(dateStart==null)
        {
            dateStart = nap.getDateStart();
        }
        else
        {
            // TODO: check if dates match
        }

        napList.add(nap);
        totalNapDuration = totalNapDuration + Integer.parseInt(nap.getDuration());
    }
}
