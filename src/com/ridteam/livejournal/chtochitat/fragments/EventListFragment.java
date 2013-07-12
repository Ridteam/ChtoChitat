package com.ridteam.livejournal.chtochitat.fragments;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ridteam.livejournal.chtochitat.utils.Utils;
import com.ridteam.livejournal.chtochitat.webservice.WebService;
import com.ridteam.livejournal.chtochitat.xmlrpc.entities.event.EventEntity;

public class EventListFragment extends BaseFragment
{

	public static final String TAG = "EventListFragment";

	private AsyncLoadEventList asyncLoadEvents = null;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		loadData();
	}

	/**
	 * run asynchrone loading data of events list
	 */
	@SuppressLint("NewApi")
	private void loadData()
	{
		if (Utils.isExistConnection(getActivity()))
		{
			if (asyncLoadEvents != null)
			{
				asyncLoadEvents.cancel(true);
			}
			asyncLoadEvents = new AsyncLoadEventList();
			if (Utils.hasHoneycomb())
			{
				asyncLoadEvents.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			}
			else
			{
				asyncLoadEvents.execute();
			}
		}
		else
		{
			
		}
	}

	/**
	 * AsyncLoadEventList asynchrone load list of events
	 */
	private class AsyncLoadEventList extends AsyncTask<Void, ArrayList<EventEntity>, ArrayList<EventEntity>>
	{

		private static final String TAG = "AsyncLoadEventList";

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
		}

		@Override
		protected ArrayList<EventEntity> doInBackground(Void... params)
		{
			WebService wService = new WebService();
			return wService.getListOfMessagesFromWall(null, null);
		}

		@Override
		protected void onPostExecute(ArrayList<EventEntity> result)
		{
			super.onPostExecute(result);
		}
	}

	@Override
	public void initControls(View view)
	{

	}

	@Override
	public void initData()
	{

	}

}
