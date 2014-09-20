package com.shtern.timeupp;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class CreateActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.create_actvity);
	}
}
