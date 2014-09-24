package com.shtern.timeupp;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateActivity extends Activity {
	private Calendar cal;
	private int day;
	private int month;
	private int year;
	EditText dataselector;
	EditText timeselector;
	EditText name_et;
	EditText dest_et;
	EditText address;
	Button savebutton;
	Button startbutton;
	TextView aligntv;
	NonSwipeableViewPager pager;
	TUPagerAdapter pagerAdapter;
	GoogleMap googleMap;
	RelativeLayout mappage;
	Marker marker;
	String filterAddress = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_create);
		pager = (NonSwipeableViewPager) findViewById(R.id.ca_pager);
		pagerAdapter = new TUPagerAdapter();
		pager.setOffscreenPageLimit(2);
		pager.setAdapter(pagerAdapter);
		RelativeLayout createpage = (RelativeLayout) getLayoutInflater()
				.inflate(R.layout.create_page, null);
		Intent intent = getIntent();
		String name = intent.getStringExtra("name");
		final String time = intent.getStringExtra("time");
		final String destination = intent.getStringExtra("destination");
		cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);
		dataselector = (EditText) createpage.findViewById(R.id.date_selector);
		timeselector = (EditText) createpage.findViewById(R.id.time_selector);
		address = (EditText) createpage.findViewById(R.id.adress);
		if (!time.equals(""))
			timeselector.setText(time);

		name_et = (EditText) createpage.findViewById(R.id.name_edittext);
		if (!name.equals(""))
			name_et.setText(name);
		dest_et = (EditText) createpage.findViewById(R.id.adress);
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
				if (hasFocus) {
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
				if (hasFocus) {
					DateDialog();

				}
			}
		});
		aligntv = (TextView) createpage.findViewById(R.id.aligntext);
		aligntv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				hideKeyboard(imm);
				pager.setCurrentItem(1);
				if (marker != null)
					marker.showInfoWindow();
			}
		});
		savebutton = (Button) createpage.findViewById(R.id.save_button);
		savebutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// MainActivity.this.itemlist.add(new
				// DestListItem(dest_et.getText().toString(),name_et.getText().toString(),timeselector.getText().toString()));
				MainActivity.addItem(new DestListItem(dest_et.getText()
						.toString(), name_et.getText().toString(), timeselector
						.getText().toString()));
				CreateActivity.this.finish();
			}
		});

		startbutton = (Button) createpage.findViewById(R.id.start_button);
		startbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final RelativeLayout page = (RelativeLayout) getLayoutInflater()
						.inflate(R.layout.alarmpage, null);
				TextView drivetime = (TextView) page
						.findViewById(R.id.drivetime);
				drivetime.setText(timeselector.getText().toString());
				drivetime.setTypeface(Typeface.createFromAsset(getAssets(),
						"digital-7.ttf"));
				RemainClock remainclock = (RemainClock) page
						.findViewById(R.id.remaintime);
				remainclock.setTimeToDrive(timeselector.getText().toString());
				remainclock.setTypeface(Typeface.createFromAsset(getAssets(),
						"digital-7.ttf"));
				TextView routetv = (TextView) page.findViewById(R.id.route_tv);
				routetv.setText(dest_et.getText().toString());
				final Button next = (Button) page.findViewById(R.id.nextbutton);
				next.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						final int repos = MainActivity.pagerAdapter
								.getItemPosition(page) + 1;
						MainActivity.pager.setCurrentItem(repos);
					}
				});

				final Button prev = (Button) page.findViewById(R.id.prevbutton);
				if (MainActivity.pagerAdapter.getCount() > 1)
					prev.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							final int repos = MainActivity.pagerAdapter
									.getItemPosition(page) - 1;
							MainActivity.pager.setCurrentItem(repos);
						}
					});
				else
					prev.setVisibility(View.GONE);
				final Button settings = (Button) page
						.findViewById(R.id.settingsbutton);
				settings.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						final Intent intent = new Intent(
								getApplicationContext(), SettingsActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

						startActivity(intent);
					}
				});
				MainActivity.pagerAdapter.addView(page);
				MainActivity.pager.setCurrentItem(MainActivity.pagerAdapter
						.getCount());
				CreateActivity.this.finish();
			}
		});
		pagerAdapter.addView(createpage);
		mappage = (RelativeLayout) getLayoutInflater().inflate(
				R.layout.mappage, null);
		pagerAdapter.addView(mappage);
		try {
			// Loading map
			initilizeMap();

		} catch (Exception e) {
			e.printStackTrace();
		}
		googleMap.setOnMapLongClickListener(new OnMapLongClickListener() {

			@Override
			public void onMapLongClick(LatLng arg0) {
				// TODO Auto-generated method stub
				filterAddress = "";
				Geocoder geoCoder = new Geocoder(getBaseContext(), Locale
						.getDefault());
				try {
					List<Address> addresses = geoCoder.getFromLocation(
							arg0.latitude, arg0.longitude, 1);

					if (addresses.size() > 0) {
						for (int index = 0; index < addresses.get(0)
								.getMaxAddressLineIndex(); index++)
							filterAddress += addresses.get(0).getAddressLine(
									index)
									+ " ";
					}

				} catch (IOException ex) {
					ex.printStackTrace();
				} catch (Exception e2) {
					// TODO: handle exception

					e2.printStackTrace();
				}
				googleMap.clear();
				marker = googleMap.addMarker(new MarkerOptions().position(arg0)
						.title(filterAddress));
				marker.showInfoWindow();

			}

		});
		googleMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker arg0) {
				address.setText(filterAddress);
				pager.setCurrentItem(0);
				return true;
			}

		});
	}


	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();
			googleMap.setMyLocationEnabled(true);
			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
			CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(
					55.753559, 37.609218));
			CameraUpdate zoom = CameraUpdateFactory.zoomTo(10);

			googleMap.moveCamera(center);
			googleMap.animateCamera(zoom);
		}
	}
	void hideKeyboard(InputMethodManager imm) {
		imm.hideSoftInputFromWindow(address.getWindowToken(), 0);
	}
	public void DateDialog() {

		OnDateSetListener listener = new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {

				dataselector.setText(dayOfMonth + "/" + monthOfYear + "/"
						+ year);

			}
		};

		DatePickerDialog dpDialog = new DatePickerDialog(this, listener, year,
				month, day);
		dpDialog.show();

	}

	public void TimeDialog() {
		Calendar mcurrentTime = Calendar.getInstance();
		int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
		int minute = mcurrentTime.get(Calendar.MINUTE);
		TimePickerDialog mTimePicker;
		mTimePicker = new TimePickerDialog(CreateActivity.this,
				new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker timePicker,
							int selectedHour, int selectedMinute) {

						timeselector.setText(selectedHour
								+ ":"
								+ String.format("%02d",
										Integer.valueOf(selectedMinute)));
					}
				}, hour, minute, true);
		mTimePicker.setTitle("Select Time");
		mTimePicker.show();
	}
}
