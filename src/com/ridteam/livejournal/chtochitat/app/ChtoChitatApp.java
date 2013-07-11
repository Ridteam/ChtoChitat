package com.ridteam.livejournal.chtochitat.app;

import android.annotation.SuppressLint;
import android.content.res.Configuration;

public class ChtoChitatApp extends android.app.Application
{

	@Override
	public void onCreate()
	{
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onLowMemory()
	{
		// TODO Auto-generated method stub
		super.onLowMemory();
	}

	@Override
	public void onTerminate()
	{
		// TODO Auto-generated method stub
		super.onTerminate();
	}

	@SuppressLint("NewApi")
	@Override
	public void onTrimMemory(int level)
	{
		// TODO Auto-generated method stub
		super.onTrimMemory(level);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

}