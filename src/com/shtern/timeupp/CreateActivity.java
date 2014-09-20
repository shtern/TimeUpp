package com.shtern.timeupp;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

public class CreateActivity extends Activity {
	private Calendar cal;
	private int day;
	private int month;
	private int year;
	EditText dataselector;
	EditText timeselector;
	EditText name_et;
	EditText dest_et;
	Button savebutton;
	Button startbutton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.create_actvity);
		Intent intent = getIntent();
		String name = intent.getStringExtra("name");
		final String time = intent.getStringExtra("time");
		final String destination = intent.getStringExtra("destination");
		cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);
		dataselector = (EditText) findViewById(R.id.date_selector);
		timeselector = (EditText) findViewById(R.id.time_selector);
		
		if (!time.equals(""))
		timeselector.setText(time);
		
		name_et = (EditText) findViewById(R.id.name_edittext);
		if (!name.equals(""))
			name_et.setText(name);
		dest_et = (EditText) findViewById(R.id.adress);
		if (!destination.equals(""))
			dest_et.setText(destination);
		timeselector.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	TimeDialog();

            }
        });
		timeselector.setOnFocusChangeListener(new OnFocusChangeListener() {
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) {
		        if(hasFocus)
		        {
		        	TimeDialog(); 

		        }
		    }
		});
		dataselector.setOnClickListener(new OnClickListener() {

	        @Override
	        public void onClick(View v) {

	            DateDialog(); 

	        }
	    });
		dataselector.setOnFocusChangeListener(new OnFocusChangeListener() {
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) {
		        if(hasFocus)
		        {
		        	 DateDialog(); 

		        }
		    }
		});
		savebutton = (Button) findViewById(R.id.save_button);
		savebutton.setOnClickListener(new OnClickListener() {

	        @Override
	        public void onClick(View v) {
	        	//MainActivity.this.itemlist.add(new DestListItem(dest_et.getText().toString(),name_et.getText().toString(),timeselector.getText().toString()));
	        	MainActivity.addItem(new DestListItem(dest_et.getText().toString(),name_et.getText().toString(),timeselector.getText().toString()));
	        	CreateActivity.this.finish();
	        }
	    });
		startbutton = (Button) findViewById(R.id.start_button);
		startbutton.setOnClickListener(new OnClickListener() {

	        @Override
	        public void onClick(View v) {
	        	RelativeLayout page = (RelativeLayout) getLayoutInflater().inflate(
	    				R.layout.alarmpage, null);
	        	TextView drivetime = (TextView) page.findViewById(R.id.drivetime);
	        	drivetime.setText(time);
	        	drivetime.setTypeface(Typeface
	    				.createFromAsset(getAssets(), "digital-7.ttf"));
	        	RemainClock remainclock = (RemainClock) page.findViewById(R.id.remaintime);
	        	remainclock.setTimeToDrive(time);
	        	remainclock.setTypeface(Typeface
	    				.createFromAsset(getAssets(), "digital-7.ttf"));
	        	TextView routetv= (TextView) page.findViewById(R.id.route_tv);
	        	routetv.setText(destination);
	        	MainActivity.pagerAdapter.addView(page);
	        	MainActivity.pager.setCurrentItem(MainActivity.pagerAdapter.getCount());
	        	CreateActivity.this.finish();
	        }
	    });
	}
	
	
	public void DateDialog(){

	    OnDateSetListener listener=new OnDateSetListener() {

	        @Override
	        public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth)
	        {

	        	dataselector.setText(dayOfMonth+"/"+monthOfYear+"/"+year);

	        }};

	    DatePickerDialog dpDialog=new DatePickerDialog(this, listener, year, month, day);
	    dpDialog.show();

	}
	public void TimeDialog(){
		  Calendar mcurrentTime = Calendar.getInstance();
          int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
          int minute = mcurrentTime.get(Calendar.MINUTE);
          TimePickerDialog mTimePicker;
          mTimePicker = new TimePickerDialog(CreateActivity.this, new TimePickerDialog.OnTimeSetListener() {
              @Override
              public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
         

            	  timeselector.setText( selectedHour + ":" + String.format("%02d", Integer.valueOf(selectedMinute)) );
              }
          }, hour, minute,true);
          mTimePicker.setTitle("Select Time");
          mTimePicker.show();
	}
}
