package com.ridteam.livejournal.chtochitat.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ridteam.livejournal.chtochitat.R;


public class EventNewFragment extends BaseFragment
{
	int pageNumber;
	public static final String TAG = "EventNewFragment";

	public static EventNewFragment newInstance(Bundle bundle)
	{
		EventNewFragment pageFragment = new EventNewFragment();
		Bundle arguments = new Bundle();
		pageFragment.setArguments(arguments);
		return pageFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.home_new_fragment, container, false);
		return view;
	}

	@Override
	void initControls(View view)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void initData()
	{
		// TODO Auto-generated method stub

	}
}
