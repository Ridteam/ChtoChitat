package com.ridteam.livejournal.chtochitat.adapters;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.ridteam.livejournal.chtochitat.R;
import com.ridteam.livejournal.chtochitat.utils.ImageLoader;
import com.ridteam.livejournal.chtochitat.utils.ImageLoader.ImageType;
import com.ridteam.livejournal.chtochitat.views.EventListItem;
import com.ridteam.livejournal.chtochitat.views.EventListItem.ViewHolder;
import com.ridteam.livejournal.chtochitat.webservice.WebService;
import com.ridteam.livejournal.chtochitat.xmlrpc.entities.event.EventEntity;
import com.ridteam.livejournal.chtochitat.xmlrpc.entities.profile.Profile;

public class EventListAdapter extends ArrayAdapter<EventEntity>
{

	public static final String TAG = "EventListAdapter";

	private ImageLoader imageLoader = null;

	public EventListAdapter(Context context, List<EventEntity> objects)
	{
		super(context, 0, objects);
		imageLoader = new ImageLoader(context);
	}

	public EventListAdapter(Context context)
	{
		super(context, 0);
		imageLoader = new ImageLoader(context);
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
			holder.pAvatar.setImageResource(R.drawable.nouserpic);
			if (!entity.getPoster().contains("_"))
			{
				setImageOfProfile(holder.pAvatar, entity.getPoster());
			}
		}
		return item;
	}

	private void setImageOfProfile(final ImageView iv, final String nickName)
	{
		new AsyncTask<String, String, String>()
		{

			protected void onPreExecute()
			{

			};

			@Override
			protected String doInBackground(String... params)
			{
				Profile profile = new WebService().getProfile(params[0]);
				if (profile != null)
				{
					return profile.getImgUrl();
				}
				return null;
			}

			protected void onPostExecute(String result)
			{
				Log.i(TAG, "image url of profile: " + result);
				if (result != null)
				{
					imageLoader.displayImage(result, iv, ImageType.PROFILE_THUMB);
				}
			};

		}.execute(nickName);
	}

}
