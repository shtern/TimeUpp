package com.shtern.timeupp;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class MainActivity extends ActionBarActivity {
	ViewPager pager;
	TUPagerAdapter pagerAdapter;
	DestListAdapter adapter;
	ListView destlist;
	final List<DestListItem> itemlist = new ArrayList<DestListItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_main);
		pager = (ViewPager) findViewById(R.id.viewpager);
		pagerAdapter = new TUPagerAdapter();
		pager.setOffscreenPageLimit(7);
		pager.setAdapter(pagerAdapter);
		RelativeLayout page = (RelativeLayout) getLayoutInflater().inflate(
				R.layout.first_time_screen, null);
		Button settingsbutton = (Button) page
				.findViewById(R.id.settings_button);
		settingsbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Intent intent = new Intent(getApplicationContext(),
						SettingsActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				// intent.putExtra("token", token);
				startActivity(intent);
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
				// intent.putExtra("token", token);
				startActivity(intent);
			}
			
		});
		destlist = (ListView) page.findViewById(R.id.olddest);
		destlist.setFadingEdgeLength(0);

		destlist.setVerticalScrollBarEnabled(false);
		destlist.setHorizontalScrollBarEnabled(false);
		destlist.setVerticalFadingEdgeEnabled(false);
		destlist.setHorizontalFadingEdgeEnabled(false);
		destlist.setFadingEdgeLength(0);
		
		adapter = new DestListAdapter(getApplicationContext(), itemlist);
		itemlist.add(new DestListItem("Москва", "Бар", "12:00"));
		itemlist.add(new DestListItem("Москва", "Баня", "20:00"));
		itemlist.add(new DestListItem("Москва", "Вытрезвитель", "23:00"));
		adapter.notifyDataSetChanged();
		destlist.setAdapter(adapter);
	
		CustomDigitalClock clock = (CustomDigitalClock) page
				.findViewById(R.id.clock);
		clock.setTypeface(Typeface
				.createFromAsset(getAssets(), "digital-7.ttf"));
		pagerAdapter.addView(page);
	}

}
