package com.ridteam.livejournal.chtochitat.adapters;

import java.util.ArrayList;

import com.ridteam.livejournal.chtochitat.xmlrpc.entities.event.EventEntity;
import com.ridteam.livejournal.chtochitat.views.EventListItem;
import com.ridteam.livejournal.chtochitat.views.EventListItem.ViewHolder;
import android.content.Context;
//import android.provider.CalendarContract.EventsEntity;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class EventListAdapter extends BaseAdapter {

	public static final String TAG = "EventListAdapter";
	private Context mContext = null;
	private ArrayList<EventEntity> listEventsEntity = null;
	//public ImageLoader imageLoader = null;
	
	public EventListAdapter(FragmentActivity activity, 
			ArrayList<EventEntity> listEventsEntity) {
		mContext = activity;
		//imageLoader = new ImageLoader(activity.getApplicationContext());
		this.listEventsEntity = listEventsEntity;
	}

	@Override
	public int getCount() {
		if (listEventsEntity != null)	{
			return listEventsEntity.size();
		}else{
		return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		if (listEventsEntity != null && position < listEventsEntity.size())
		{
			return listEventsEntity.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		EventListItem item = null;
		if (v != null)
		{
			item = (EventListItem) v;
		}
		else
		{
			item = new EventListItem(mContext);
		}
		ViewHolder holder = (ViewHolder) item.getTag();
		EventEntity entity = (EventEntity) getItem(position);
		if (entity != null)
		{
			holder.pEvent.setText(entity.getEvent());
			holder.pEventTime.setText(entity.getEventTime());
			holder.pPoster.setText(entity.getPoster());
			holder.pSubject.setText(entity.getSubject());
/*			imageLoader.displayImage(String.format(FriendsListAdapter.URL_USER_PHOTO_THUMB, String.valueOf(entity.getFromId())), holder.pUserPostNewsfeed);
			if (entity.getPicture() != null)
			{
				imageLoader.displayImage(entity.getPicture(), holder.pNewsNewsfeed);
			}
			else
			{
				holder.pNewsNewsfeed.setVisibility(View.GONE);
			}*/
			//holder.pUserActionDate.setText(Utils.getDateFormatForNewsFeed(entity.getCreateTime()));
		}
		return item;
	}

}
