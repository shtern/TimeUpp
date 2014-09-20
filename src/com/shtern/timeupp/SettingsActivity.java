package com.shtern.timeupp;

import java.util.Calendar;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TimePicker;

public class SettingsActivity extends Activity {
	EditText time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.settings);
		time = (EditText) findViewById(R.id.timechosen);
		time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TimeDialog();

			}
		});
		time.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					TimeDialog();

				}
			}
		});
	}

	public void TimeDialog() {
		Calendar mcurrentTime = Calendar.getInstance();
		int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
		int minute = mcurrentTime.get(Calendar.MINUTE);
		TimePickerDialog mTimePicker;
		mTimePicker = new TimePickerDialog(SettingsActivity.this,
				new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker timePicker,
							int selectedHour, int selectedMinute) {

						time.setText(selectedHour
								+ ":"
								+ String.format("%02d",
										Integer.valueOf(selectedMinute)));
					}
				}, hour, minute, true);
		mTimePicker.setTitle("Select Time");
		mTimePicker.show();
	}
}
