package com.shtern.timeupp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public class MainActivity extends ActionBarActivity {
	static ViewPager pager;
	static TUPagerAdapter pagerAdapter;
	static DestListAdapter adapter;
	ListView destlist;
	final static List<DestListItem> itemlist = new ArrayList<DestListItem>();
	static int prev_page=1;
	//Strings for settings
	String hours;
	String minutes;
	String guarant;
	String traffic; 
	String type; 
	//Views for settings
	EditText time_ed;
	Spinner guarant_spin;
	Spinner type_spin;
	ToggleButton traffic_btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_main);
		loadVars();
		pager = (ViewPager) findViewById(R.id.viewpager);
		pagerAdapter = new TUPagerAdapter();
		pager.setOffscreenPageLimit(7);
		pager.setAdapter(pagerAdapter);
		inflateSettings();
		
		
		RelativeLayout page = (RelativeLayout) getLayoutInflater().inflate(
				R.layout.first_time_screen, null);
		Button settingsbutton = (Button) page
				.findViewById(R.id.settings_button);
		settingsbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				prev_page=pager.getCurrentItem();
				pager.setCurrentItem(0);
			}

		});
		Button createbutton = (Button) page
				.findViewById(R.id.create_button);
		createbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Intent intent = new Intent(getApplicationContext(),
						CreateActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("destination","");
				intent.putExtra("time", "");
				intent.putExtra("name", "");
				startActivity(intent);
			}
			
		});
		destlist = (ListView) page.findViewById(R.id.olddest);
		destlist.setFadingEdgeLength(0);

		destlist.setVerticalScrollBarEnabled(true);
		destlist.setHorizontalScrollBarEnabled(false);
		destlist.setVerticalFadingEdgeEnabled(false);
		destlist.setHorizontalFadingEdgeEnabled(false);
		destlist.setFadingEdgeLength(0);
		
		adapter = new DestListAdapter(getApplicationContext(), itemlist);
		itemlist.add(new DestListItem("г. Москва, Ленинский проспект, 16", "Работа", "08:00"));
		itemlist.add(new DestListItem("г. Москва, Покровка, 10", "Бар", "18:00"));
		itemlist.add(new DestListItem("г. Москва, Гоголевский бул. 16", "Баня", "20:00"));
		itemlist.add(new DestListItem("г. Москва, Тверская ул., 23", "Клуб", "23:00"));
		itemlist.add(new DestListItem("г. Москва, Скорняжный пер., 3", "Домой", "04:00"));
		adapter.notifyDataSetChanged();
		destlist.setAdapter(adapter);
	
		CustomDigitalClock clock = (CustomDigitalClock) page
				.findViewById(R.id.clock);
		clock.setTypeface(Typeface
				.createFromAsset(getAssets(), "digital-7.ttf"));
		pagerAdapter.addView(page);
		pager.setCurrentItem(1);
	}
	
	public void loadVars()
	{
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
	}
	public void TimeDialog() {
		int hour =Integer.parseInt(hours);
		int minute = Integer.parseInt(minutes);
		TimePickerDialog mTimePicker;
		mTimePicker = new TimePickerDialog(MainActivity.this,
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
	public static void addItem(DestListItem item)
	{
		itemlist.add(item);
		adapter.notifyDataSetChanged();
	}

	public void inflateSettings()
	{

		RelativeLayout settingspage = (RelativeLayout) getLayoutInflater().inflate(R.layout.settings, null);
		traffic_btn = (ToggleButton) settingspage.findViewById(R.id.trafficbutton);
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
		guarant_spin = (Spinner) settingspage.findViewById(R.id.routespinner);
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
		type_spin = (Spinner) settingspage.findViewById(R.id.transpspin);
		
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
		time_ed = (EditText) settingspage.findViewById(R.id.timechosen);
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
		
		pagerAdapter.addView(settingspage);
	}
	 @Override
	  public void onBackPressed() {
		 if (pager.getCurrentItem()==1)
			 super.onBackPressed();
		 if (pager.getCurrentItem()>1)
			 pager.setCurrentItem(1);
		 if (pager.getCurrentItem()==0)
			 pager.setCurrentItem(prev_page);
		
	 }
}
