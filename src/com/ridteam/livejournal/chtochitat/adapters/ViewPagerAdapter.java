package com.ridteam.livejournal.chtochitat.adapters;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ridteam.livejournal.chtochitat.fragments.BaseFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter
{

	private final ArrayList<TabInfo> fragmentList;

	public ViewPagerAdapter(FragmentManager fm)
	{
		super(fm);
		fragmentList = new ArrayList<ViewPagerAdapter.TabInfo>();
	}

	@Override
	public BaseFragment getItem(int position)
	{
		TabInfo info = fragmentList.get(position);
		return info.fragment;
	}

	public ArrayList<TabInfo> getFragmentList()
	{
		return fragmentList;
	}

	@Override
	public int getCount()
	{
		return fragmentList.size();
	}

	public static final class TabInfo
	{
		public final Class<?> clss;
		public final Bundle args;
		public final BaseFragment fragment;

		public TabInfo(Class<?> clss, Bundle args, BaseFragment fragment)
		{
			this.clss = clss;
			this.args = args;
			this.fragment = fragment;
		}
	}

	public void addTab(Class<?> clss, Bundle args, BaseFragment fragment)
	{
		TabInfo info = new TabInfo(clss, args, fragment);
		fragmentList.add(info);
		notifyDataSetChanged();
	}
}
