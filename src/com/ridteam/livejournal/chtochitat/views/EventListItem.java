package com.ridteam.livejournal.chtochitat.views;

import com.ridteam.livejournal.chtochitat.activity.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EventListItem extends LinearLayout {
	public static final String TAG = "EventListItem";
	public EventListItem(Context context) {
		super(context);
		initControls(context);
	}

	public EventListItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		initControls(context);
	}
	private void initControls(Context context)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.item_event, this);
		ViewHolder holder = new ViewHolder();
		holder.pAvatar = (ImageView) view.findViewById(R.id.ivAvatar);
		holder.pEvent = (TextView) view.findViewById(R.id.tvEvent);
		holder.pEventTime = (TextView) view.findViewById(R.id.tvEventTime);
		holder.pPoster = (TextView) view.findViewById(R.id.tvPoster);
		holder.pSubject = (TextView) view.findViewById(R.id.tvSubject);

		this.setTag(holder);
	}
	public static class ViewHolder
	{
		public ImageView pAvatar = null;
		public TextView pPoster = null;
		public TextView pSubject = null;
		public TextView pEvent = null;
		public TextView pEventTime = null;
		//public int pReplyCount = null;
	}

}
