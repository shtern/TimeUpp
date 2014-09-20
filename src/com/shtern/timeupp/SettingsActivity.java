package com.shtern.timeupp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public class SettingsActivity extends Activity {
	EditText time_ed;
	Spinner guarant_spin;
	Spinner type_spin;
	ToggleButton traffic_btn;
	String hours;
	String minutes;
	String guarant;
	String traffic; 
	String type; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.settings);
		try {
			InputStream inputStream = openFileInput("config.txt");
			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				String receiveString = "";
				try {
					hours = bufferedReader.readLine();
					minutes = bufferedReader.readLine();
					guarant = bufferedReader.readLine();
					traffic = bufferedReader.readLine();
					type = bufferedReader.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if ((type==null)&&(minutes==null)&&(hours==null)&&(guarant==null)&&(traffic==null))
		{
			type="0";
			minutes="45";
			hours="00";
			guarant="0";
			traffic="1";
		}
		traffic_btn = (ToggleButton) findViewById(R.id.trafficbutton);
		if (Integer.parseInt(traffic)==1) traffic_btn.setChecked(true);
		else traffic_btn.setChecked(false);
		traffic_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (traffic_btn.isChecked()) {
					traffic=1+"";
					writeToFile();
			    } else {
			    	traffic=0+"";
					writeToFile();
			    }
			}
			
		});
		guarant_spin = (Spinner) findViewById(R.id.routespinner);
		guarant_spin.setSelection(Integer.parseInt(guarant));
		guarant_spin.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				guarant=position+"";
				writeToFile();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
		type_spin = (Spinner) findViewById(R.id.transpspin);
		
		type_spin.setSelection(Integer.parseInt(type));
		type_spin.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				type=position+"";
				writeToFile();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
		time_ed = (EditText) findViewById(R.id.timechosen);
		time_ed.setText(hours+":"+minutes);
		time_ed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TimeDialog();

			}
		});
		time_ed.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					TimeDialog();

				}
			}
		});
	}

	public void TimeDialog() {
		int hour =Integer.parseInt(hours);
		int minute = Integer.parseInt(minutes);
		TimePickerDialog mTimePicker;
		mTimePicker = new TimePickerDialog(SettingsActivity.this,
				new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker timePicker,
							int selectedHour, int selectedMinute) {
						hours=selectedHour+"";
						minutes=selectedMinute+"";
						writeToFile();
						time_ed.setText(selectedHour
								+ ":"
								+ String.format("%02d",
										Integer.valueOf(selectedMinute)));
					}
				}, hour, minute, true);
		mTimePicker.setTitle("Select Time");
		mTimePicker.show();
	}
	private void writeToFile() {
	    try {
	        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("config.txt", Context.MODE_PRIVATE));
	        outputStreamWriter.write(hours+"\n"+minutes+"\n"+guarant+"\n"+traffic+"\n"+type);
	        outputStreamWriter.close();
	    }
	    catch (IOException e) {
	        Log.e("Exception", "File write failed: " + e.toString());
	    } 
	}
}
