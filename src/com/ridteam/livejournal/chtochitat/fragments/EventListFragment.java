package com.ridteam.livejournal.chtochitat.fragments;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.markupartist.android.widget.PullToRefreshListView;
import com.markupartist.android.widget.PullToRefreshListView.OnRefreshListener;
import com.ridteam.livejournal.chtochitat.activity.R;
import com.ridteam.livejournal.chtochitat.adapters.EventListAdapter;
import com.ridteam.livejournal.chtochitat.utils.Utils;
import com.ridteam.livejournal.chtochitat.webservice.WebService;
import com.ridteam.livejournal.chtochitat.xmlrpc.entities.event.EventEntity;

public class EventListFragment extends BaseFragment {
	int pageNumber;
	public static final String TAG = "EventListFragment";
	private PullToRefreshListView mLvEvents = null;
	private EventListAdapter mAdapter = null;
	private AsyncLoadEventList asyncLoadEvents = null;
	private ArrayList<EventEntity> mListEventEntity = null;

	public static EventListFragment newInstance(int page) {
		EventListFragment pageFragment = new EventListFragment();
		Bundle arguments = new Bundle();
		arguments.putInt("page", page);
		pageFragment.setArguments(arguments);
		return pageFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pageNumber = getArguments().getInt("page");
		Log.d(TAG, "pageNumber = " + pageNumber);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_wall_fragment, container, false);
		initControls(view);
		Log.d(TAG, "pageNumber = " + pageNumber);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData();
	}

	/**
	 * run asynchrone loading data of events list
	 */
	@SuppressLint("NewApi")
	private void loadData() {
		if (Utils.isExistConnection(getActivity())) {
			if (asyncLoadEvents != null) {
				asyncLoadEvents.cancel(true);
			}
			asyncLoadEvents = new AsyncLoadEventList();
			if (Utils.hasHoneycomb()) {
				asyncLoadEvents.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			} else {
				asyncLoadEvents.execute();
			}
		} else {
		}

	}

	/**
	 * AsyncLoadEventList asynchrone load list of events
	 */
	private class AsyncLoadEventList extends AsyncTask<Void, ArrayList<EventEntity>, ArrayList<EventEntity>> {

		private static final String TAG = "AsyncLoadEventList";

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected ArrayList<EventEntity> doInBackground(Void... params) {
			WebService wService = new WebService();
			return wService.getListOfMessagesFromWall(null, null);
		}

		@Override
		protected void onPostExecute(ArrayList<EventEntity> result) {
			mListEventEntity = result;
			initData();
			if (mLvEvents != null) {
				mLvEvents.onRefreshComplete();
			}
			super.onPostExecute(result);
		}
	}

	@Override
	public void initControls(View view) {
		mLvEvents = (PullToRefreshListView) view.findViewById(R.id.lvEntity);
		mLvEvents.setOnRefreshListener(onRefreshListener);
	}

	@Override
	public void initData() {
		mAdapter = new EventListAdapter(getActivity(), mListEventEntity);
		mLvEvents.setAdapter(mAdapter);
	}

	private OnRefreshListener onRefreshListener = new OnRefreshListener() {

		@Override
		public void onRefresh() {
			loadData();
		}
	};

}
