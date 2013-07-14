package com.ridteam.livejournal.chtochitat.fragments;

import android.support.v4.app.Fragment;
import android.view.View;

public abstract class BaseFragment extends Fragment
{

	/**
	 * Initialize controls of view
	 * 
	 * @param view
	 *            - inflate (root) view
	 */
	abstract void initControls(View view);

	/**
	 * Initialize data of fragments for view
	 */
	abstract public void initData();

}
