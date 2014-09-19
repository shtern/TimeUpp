package com.shtern.timeupp;



import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DigitalClock;
import android.widget.RelativeLayout;

public class MainActivity extends ActionBarActivity {
	ViewPager pager;
	TUPagerAdapter pagerAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		pager = (ViewPager) findViewById(R.id.viewpager);
		pagerAdapter = new TUPagerAdapter();
		pager.setOffscreenPageLimit(7);
		pager.setAdapter(pagerAdapter);
		RelativeLayout ttpage = (RelativeLayout) getLayoutInflater().inflate(
				R.layout.first_time_screen, null);
		
		CustomDigitalClock clock = (CustomDigitalClock)ttpage.findViewById(R.id.clock);
		clock.setTypeface(Typeface.createFromAsset(getAssets(),
				"digital-7.ttf"));
		pagerAdapter.addView(ttpage);
	}


}
