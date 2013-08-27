package com.ridteam.livejournal.chtochitat.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;

import com.ridteam.livejournal.chtochitat.R;
import com.ridteam.livejournal.chtochitat.adapters.ViewPagerAdapter;
import com.ridteam.livejournal.chtochitat.fragments.WallFragment;
import com.ridteam.livejournal.chtochitat.fragments.EventMemoryFragment;
import com.ridteam.livejournal.chtochitat.fragments.EventNewFragment;

public class HomeActivity extends ActionBarActivity implements OnPageChangeListener, ActionBar.TabListener
{

	private static final String TAG = "HomeActivity";

	private ViewPager viewPager = null;
	private ViewPagerAdapter adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity_layout);
		initControls();
		initData();
	}

	private void initControls()
	{
		viewPager = (ViewPager) findViewById(R.id.pager);
	}

	private void initData()
	{
		adapter = new ViewPagerAdapter(getSupportFragmentManager());
		adapter.addTab(WallFragment.class, null, WallFragment.newInstance(null));
		adapter.addTab(EventMemoryFragment.class, null, EventMemoryFragment.newInstance(null));
		adapter.addTab(EventNewFragment.class, null, EventNewFragment.newInstance(null));
		viewPager.setAdapter(adapter);
		initActionBarTabs();
	}

	private void initActionBarTabs()
	{
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		if (actionBar != null)
		{
			ActionBar.Tab tabWall = actionBar.newTab();
			// tabSpop.setIcon(R.drawable.ab_tab_spop);
			tabWall.setText(R.string.ab_tab_wall);
			tabWall.setTabListener(this);
			actionBar.addTab(tabWall);

			ActionBar.Tab tabDetail = actionBar.newTab();
			// tabPractice.setIcon(R.drawable.ab_tab_practice);
			tabDetail.setText(R.string.ab_tab_detail);
			tabDetail.setTabListener(this);
			actionBar.addTab(tabDetail);

			ActionBar.Tab tabNew = actionBar.newTab();
			// tabMatch.setIcon(R.drawable.ab_tab_match);
			tabNew.setText(R.string.ab_tab_new_entity);
			tabNew.setTabListener(this);
			actionBar.addTab(tabNew);

			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft)
	{
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrollStateChanged(int arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int position)
	{
		getSupportActionBar().setSelectedNavigationItem(position);
	}

}
