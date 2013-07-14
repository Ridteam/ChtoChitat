package com.ridteam.livejournal.chtochitat.fragments;

import com.ridteam.livejournal.chtochitat.activity.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EventMemoryFragment extends Fragment {
	int pageNumber;
	public static final String TAG = "EventMemoryFragment";
	public static EventMemoryFragment newInstance(int page) {
		EventMemoryFragment pageFragment = new EventMemoryFragment();
		Bundle arguments = new Bundle();
		arguments.putInt("page", page);
		pageFragment.setArguments(arguments);
		return pageFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pageNumber = getArguments().getInt("page");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_memory_fragment, container,false);
		Log.d(TAG, "pageNumber = "+pageNumber);
		return view;
	}

}
