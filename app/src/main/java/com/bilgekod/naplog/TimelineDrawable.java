package com.bilgekod.naplog;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TimelineDrawable extends Drawable
{
    private Paint paint_bg;
    private Paint paint_fg;
    private Paint paint_text;

    private final float textSizeMin = 10f;
    private final float textSizeMax = 60f;
    private float hourMax;
    private float hourMin;

    private ArrayList<Float> list_hours;
    private ArrayList<Float> list_durations;

    private int flagAboveBelow;
    private boolean doShowDurations;

    public TimelineDrawable(int color_fg, int color_bg, int color_text, float hourMin, float hourMax, ArrayList<Float> list_hours, ArrayList<Float> list_durations) throws Exception
    {
        paint_fg = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_fg.setColor(color_fg);

        paint_bg = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_bg.setColor(color_bg);

        paint_text = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_text.setColor(color_text);

        this.hourMin = hourMin;
        this.hourMax = hourMax;

        if(hourMin>=hourMax)
        {
            throw new Exception("hourMax must be bigger than hourMin");
        }

        this.list_hours = list_hours;
        this.list_durations = list_durations;

        if(list_hours.size()!=list_durations.size())
        {
            throw new Exception("hour and duration array size mismatch");
        }

        doShowDurations = true;
        flagAboveBelow = 1;
    }

    @Override
    public void draw(@NonNull Canvas canvas)
    {
        Rect bounds = getBounds();

        canvas.drawRect(bounds.left, bounds.top, bounds.right, bounds.bottom, paint_bg);

        float hour;
        float duration;
        int i;

        for(i=0; i<list_hours.size(); i++)
        {
            hour = list_hours.get(i);
            duration = list_durations.get(i);

            if ((hour + duration) < hourMin || hour > hourMax)
            {
                // out of bounds
                continue;
            }
            else
            {
                float nap_left = Math.max((hour - hourMin) * ((bounds.right - bounds.left) / (hourMax - hourMin)) + bounds.left, bounds.left);
                float nap_right = Math.min((hour - hourMin + duration) * ((bounds.right - bounds.left) / (hourMax - hourMin)) + bounds.left, bounds.right);

                canvas.drawRect(nap_left, bounds.top, nap_right, bounds.bottom, paint_fg);
            }
        }

        for(i=0; i<list_hours.size(); i++)
        {
            hour = list_hours.get(i);
            duration = list_durations.get(i);

            if ((hour + duration) < hourMin || hour > hourMax)
            {
                // out of bounds
                continue;
            }
            else
            {
                float nap_left = Math.max((hour - hourMin) * ((bounds.right - bounds.left) / (hourMax - hourMin)) + bounds.left, bounds.left);

                float textSize = (bounds.bottom - bounds.top) * 0.3f;
                if (textSize < textSizeMin)
                {
                    textSize = textSizeMin;
                }
                if (textSize > textSizeMax)
                {
                    textSize = textSizeMax;
                }
                paint_text.setTextSize(textSize);

                String hour_str = "" + (int) (hour / 60);
                String minute_str = "" + (int) (hour % 60);
                if (minute_str.length() == 1)
                {
                    minute_str = "0" + minute_str;
                }

                float text_y;
                if (flagAboveBelow > 0)
                {
                    text_y = bounds.top + (bounds.bottom - bounds.top) * 0.4f;
                } else
                {
                    text_y = bounds.top + (bounds.bottom - bounds.top) * 0.8f;
                }

                if(doShowDurations)
                {
                    int duration_h = (int)(duration)/60;
                    int duration_m = (int)(duration)%60;
                    String duration_h_str = ""+duration_h;
                    String duration_m_str = ""+duration_m;
                    String hour_suffix = "h";
                    String minute_suffix = "m";
                    String duration_str;

                    /*
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
                     */

                    if(duration_h>0)
                    {
                        if(duration_m>0)
                        {
                            duration_str = duration_h_str+" "+hour_suffix+" "+duration_m_str+" "+minute_suffix;
                        }
                        else
                        {
                            duration_str = duration_h_str+" "+hour_suffix;
                        }
                    }
                    else
                    {
                        duration_str = duration_m_str+" "+minute_suffix;
                    }

                    canvas.drawText(duration_str, nap_left, text_y, paint_text);
                }
                else
                {
                    canvas.drawText(hour_str + ":" + minute_str, nap_left, text_y, paint_text);
                }

                flagAboveBelow = flagAboveBelow * -1;
            }
        }
    }

    @Override
    public void setAlpha(int alpha)
    {
        paint_fg.setAlpha(alpha);
        paint_bg.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter)
    {
        paint_fg.setColorFilter(colorFilter);
        paint_bg.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity()
    {
        //give the desired opacity of the shape
        return PixelFormat.TRANSLUCENT;
    }

    public void setDoShowDurations(boolean newDoShowDurations)
    {
        doShowDurations = newDoShowDurations;
    }
}
