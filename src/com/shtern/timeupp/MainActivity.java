package com.shtern.timeupp;




import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.RelativeLayout;

public class MainActivity extends ActionBarActivity {
	ViewPager pager;
	TUPagerAdapter pagerAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_main);
		pager = (ViewPager) findViewById(R.id.viewpager);
		pagerAdapter = new TUPagerAdapter();
		pager.setOffscreenPageLimit(7);
		pager.setAdapter(pagerAdapter);
		RelativeLayout ttpage = (RelativeLayout) getLayoutInflater().inflate(
				R.layout.first_time_screen, null);
		Button settingsbutton = (Button) ttpage.findViewById(R.id.settings_button);
		settingsbutton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Intent intent = new Intent(getApplicationContext(),
						SettingsActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//intent.putExtra("token", token);
				startActivity(intent);
			}
			
		});
		CustomDigitalClock clock = (CustomDigitalClock)ttpage.findViewById(R.id.clock);
		clock.setTypeface(Typeface.createFromAsset(getAssets(),
				"digital-7.ttf"));
		pagerAdapter.addView(ttpage);
	}


}
