package com.ridteam.livejournal.chtochitat.fragments;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.markupartist.android.widget.PullToRefreshListView;
import com.markupartist.android.widget.PullToRefreshListView.OnRefreshListener;
import com.ridteam.livejournal.chtochitat.R;
import com.ridteam.livejournal.chtochitat.adapters.EventListAdapter;
import com.ridteam.livejournal.chtochitat.utils.Utils;
import com.ridteam.livejournal.chtochitat.webservice.WebService;
import com.ridteam.livejournal.chtochitat.xmlrpc.entities.event.EventEntity;

public class WallFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<List<EventEntity>>, OnItemClickListener
{
	public static final String TAG = "EventListFragment";
	private PullToRefreshListView mLvEvents = null;
	private EventListAdapter mAdapter = null;
	private WallLoader loader = null;

	public static WallFragment newInstance(Bundle bundle)
	{
		WallFragment pageFragment = new WallFragment();
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
		View view = inflater.inflate(R.layout.home_wall_fragment, container, false);
		initControls(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		initData();
	}

	@Override
	public void initControls(View view)
	{
		mLvEvents = (PullToRefreshListView) view.findViewById(R.id.lvEntity);
		mLvEvents.setOnRefreshListener(onRefreshListener);
		mLvEvents.setOnItemClickListener(this);
	}

	@Override
	public void initData()
	{
		mAdapter = new EventListAdapter(getActivity());
		mLvEvents.setAdapter(mAdapter);
	}

	private OnRefreshListener onRefreshListener = new OnRefreshListener()
	{

		@Override
		public void onRefresh()
		{
			if (loader == null)
			{
				getLoaderManager().initLoader(0, null, WallFragment.this);
			}
			else
			{
				loader.forceLoad();
			}
		}
	};

	private static class WallLoader extends AsyncTaskLoader<List<EventEntity>>
	{

		private static final String TAG = "WallLoader";

		private List<EventEntity> listEvent = null;

		public WallLoader(Context context)
		{
			super(context);
		}

		@Override
		public List<EventEntity> loadInBackground()
		{
			Utils.logDebug(TAG, "loadInBackground");
			WebService wService = new WebService();
			return wService.getListOfMessagesFromWall(null, null);
		}

		@Override
		public void deliverResult(List<EventEntity> data)
		{
			Utils.logDebug(TAG, "deliverResult");
			if (isReset() && data != null)
			{
				onReleaseData(data);
			}
			List<EventEntity> oldData = data;
			listEvent = oldData;
			if (isStarted())
			{
				super.deliverResult(data);
			}
			if (oldData != null)
			{
				onReleaseData(data);
			}
			super.deliverResult(data);
		}

		@Override
		protected void onStartLoading()
		{
			Utils.logDebug(TAG, "onStartLoading");
			if (listEvent != null)
			{
				deliverResult(listEvent);
			}
			else
			{
				forceLoad();
			}
			super.onStartLoading();
		}

		@Override
		protected void onStopLoading()
		{
			Utils.logDebug(TAG, "onStopLoading");
			cancelLoad();
		}

		@Override
		public void onCanceled(List<EventEntity> data)
		{
			Utils.logDebug(TAG, "onCanceled");
			super.onCanceled(data);
			onReleaseData(data);
		}

		@Override
		protected void onReset()
		{

			super.onReset();
			Utils.logDebug(TAG, "onReset");
			onStopLoading();
			if (listEvent != null)
			{
				onReleaseData(listEvent);
				listEvent = null;
			}
		}

		private void onReleaseData(List<EventEntity> data)
		{

		}

	}

	@Override
	public Loader<List<EventEntity>> onCreateLoader(int arg0, Bundle arg1)
	{
		Utils.logDebug(TAG, "onCreateLoader");
		if (loader == null)
		{
			loader = new WallLoader(getActivity());
		}
		loader.forceLoad();
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<List<EventEntity>> arg0, List<EventEntity> result)
	{
		Utils.logDebug(TAG, "onLoadFinished");
		mAdapter = new EventListAdapter(getActivity(), result);
		if (mLvEvents != null)
		{
			mLvEvents.setAdapter(mAdapter);
			mLvEvents.onRefreshComplete();
		}
	}

	@Override
	public void onLoaderReset(Loader<List<EventEntity>> arg0)
	{
		Utils.logDebug(TAG, "onLoaderReset");
		mAdapter = new EventListAdapter(getActivity(), null);
		mLvEvents.setAdapter(mAdapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		EventEntity entity = mAdapter.getItem(position);
		new AsyncTask<Integer, EventEntity, EventEntity>()
		{
			private ProgressDialog dialog = null;

			@Override
			protected void onPreExecute()
			{
				dialog = new ProgressDialog(getActivity());
				dialog.setMessage(getString(R.string.dialog_loading));
				dialog.show();
				super.onPreExecute();
			}

			@Override
			protected EventEntity doInBackground(Integer... params)
			{
				EventEntity entity = null;
				entity = new WebService().getMessageFromWall(null, null, params[0]);
				return entity;
			}

			@Override
			protected void onPostExecute(EventEntity result)
			{
				super.onPostExecute(result);
				dialog.dismiss();
			}

		}.execute(entity.getItemid());
	}
}
