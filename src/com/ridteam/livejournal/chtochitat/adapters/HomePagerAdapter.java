package com.ridteam.livejournal.chtochitat.adapters;

import com.ridteam.livejournal.chtochitat.fragments.EventListFragment;
import com.ridteam.livejournal.chtochitat.fragments.EventMemoryFragment;
import com.ridteam.livejournal.chtochitat.fragments.EventNewFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class HomePagerAdapter extends FragmentPagerAdapter {

	public HomePagerAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		switch (position){
		case 0:
			return EventListFragment.newInstance(position);
		case 1:
			return EventMemoryFragment.newInstance(position);
		default:
			return EventNewFragment.newInstance(position);
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position){
		case 0:
			return "Что читать";
		case 1:
			return "Избранное";
		default:
			return "Новое сообщение";
		}
	}
}
