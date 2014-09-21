package com.shtern.timeupp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;



import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class RemainClock extends TextView {


    Calendar mCalendar;
    private final static String m12 = "h:mm aa";
    private final static String m24 = "kk::mm";
    private final static String m24space = "kk mm";
    private FormatChangeObserver mFormatChangeObserver;
    private String mTimeToDrive="";
    private Runnable mTicker;
    private Handler mHandler;

    private boolean mTickerStopped = false;

    String mFormat;

    public RemainClock(Context context) {
        super(context);
        initClock(context);
    }

    public RemainClock(Context context, AttributeSet attrs) {
        super(context, attrs);
        initClock(context);
    }

    private void initClock(Context context) {
        Resources r = context.getResources();

        if (mCalendar == null) {
            mCalendar = Calendar.getInstance();
        }

        mFormatChangeObserver = new FormatChangeObserver();
        getContext().getContentResolver().registerContentObserver(
                Settings.System.CONTENT_URI, true, mFormatChangeObserver);

        setFormat();
    }

    @Override
    protected void onAttachedToWindow() {
    	mTickerStopped = false;
        super.onAttachedToWindow();
        mHandler = new Handler();
        
        mTicker = new Runnable() {
            public void run() {
                if (mTickerStopped) return;
                try {
                	mCalendar.setTimeInMillis(System.currentTimeMillis());
                    SimpleDateFormat format = new SimpleDateFormat("kk:mm");
                    Date Date1;
                    Date Date2;
					
						 Date1 = format.parse(DateFormat.format(m24, mCalendar).toString().replace("::", ":"));
						 Date2 = format.parse(mTimeToDrive);
			
                    //Date1.setTime(System.currentTimeMillis());
                    
                    long mills = Date2.getTime() - Date1.getTime();
                    Log.v("Data1", ""+Date1.getTime());
                    Log.v("Data2", ""+Date2.getTime());
                    int Hours = (int) (mills/(1000 * 60 * 60));
                    int Mins = (int) (mills/(1000*60)) % 60;

                    String diff = String.format("%02d",Hours) + ":" + String.format("%02d",Mins); // updated value every1 second
                    setText(diff);
                    invalidate();
                    long now = SystemClock.uptimeMillis();
                    long next = now + (1000 - now % 1000);
                    mHandler.postAtTime(mTicker, next);
        		} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        };
    mTicker.run();
}
    public void setTimeToDrive(String time){
    	mTimeToDrive=time;
    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mTickerStopped = true;
    }

    /**
     * Pulls 12/24 mode from system settings
     */
    private boolean get24HourMode() {
        return android.text.format.DateFormat.is24HourFormat(getContext());
    }

    private void setFormat() {
        if (get24HourMode()) {
            mFormat = m24;
        } else {
            mFormat = m12;
        }
    }

    private class FormatChangeObserver extends ContentObserver {
        public FormatChangeObserver() {
            super(new Handler());
        }

        @Override
        public void onChange(boolean selfChange) {
            setFormat();
        }
    }

}
