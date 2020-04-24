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
        boolean doMergeNap = true;

        if(userid==null)
        {
            // if we do not have userid, we inherit from the nap
            userid = nap.getUserid();
        }
        else if(!userid.equals(nap.getUserid()))
        {
            // if we have already userid, it has to be the same with that of the nap
            doMergeNap = false;
        }

        if(dateStart==null)
        {
            // if we do not have dateStart, we inherit from the nap
            dateStart = nap.getDateStart();
        }
        else if(!dateStart.equals(nap.getDateStart()))
        {
            // if we have already dateStart, it has to be the same with that of the nap
            doMergeNap = false;
        }

        if(doMergeNap)
        {
            napList.add(nap);
            totalNapDuration = totalNapDuration + Integer.parseInt(nap.getDuration());
        }
    }
}
