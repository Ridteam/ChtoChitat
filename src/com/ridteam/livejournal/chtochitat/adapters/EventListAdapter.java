package com.ridteam.livejournal.chtochitat.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ridteam.livejournal.chtochitat.views.EventListItem;
import com.ridteam.livejournal.chtochitat.views.EventListItem.ViewHolder;
import com.ridteam.livejournal.chtochitat.xmlrpc.entities.event.EventEntity;

//import android.provider.CalendarContract.EventsEntity;

public class EventListAdapter extends ArrayAdapter<EventEntity>
{

	public static final String TAG = "EventListAdapter";

	// public ImageLoader imageLoader = null;

	public EventListAdapter(Context context, List<EventEntity> objects)
	{
		super(context, 0, objects);
	}

	public EventListAdapter(Context context)
	{
		super(context, 0);
	}

	@Override
	public View getView(int position, View v, ViewGroup parent)
	{
		EventListItem item = null;
		if (v != null)
		{
			item = (EventListItem) v;
		}
		else
		{
			item = new EventListItem(getContext());
		}
		ViewHolder holder = (ViewHolder) item.getTag();
		EventEntity entity = (EventEntity) getItem(position);
		if (entity != null)
		{
			holder.pEvent.setText(entity.getEvent());
			holder.pEventTime.setText(entity.getEventTime());
			holder.pPoster.setText(entity.getPoster());
			holder.pSubject.setText(entity.getSubject());
			/* imageLoader.displayImage(String.format(FriendsListAdapter.
			 * URL_USER_PHOTO_THUMB, String.valueOf(entity.getFromId())),
			 * holder.pUserPostNewsfeed); if (entity.getPicture() != null) {
			 * imageLoader.displayImage(entity.getPicture(),
			 * holder.pNewsNewsfeed); } else {
			 * holder.pNewsNewsfeed.setVisibility(View.GONE); } */
			// holder.pUserActionDate.setText(Utils.getDateFormatForNewsFeed(entity.getCreateTime()));
		}
		return item;
	}

}
