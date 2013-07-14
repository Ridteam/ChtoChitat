package com.ridteam.livejournal.chtochitat.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.ridteam.livejournal.chtochitat.adapters.HomePagerAdapter;

public class HomeActivity extends SherlockFragmentActivity {
	static final int ITEMS = 3;
	HomePagerAdapter mAdapter;
	ViewPager mPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity_layout);
		mAdapter = new HomePagerAdapter(getSupportFragmentManager());
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

	}
}
