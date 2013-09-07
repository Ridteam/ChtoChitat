package com.ridteam.livejournal.chtochitat.adapters;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ridteam.livejournal.chtochitat.R;
import com.ridteam.livejournal.chtochitat.db.DataBaseProvider;
import com.ridteam.livejournal.chtochitat.db.DataBaseUtils;
import com.ridteam.livejournal.chtochitat.utils.ImageLoader;
import com.ridteam.livejournal.chtochitat.utils.ImageLoader.ImageType;
import com.ridteam.livejournal.chtochitat.utils.Utils;
import com.ridteam.livejournal.chtochitat.views.EventListItem;
import com.ridteam.livejournal.chtochitat.views.EventListItem.ViewHolder;
import com.ridteam.livejournal.chtochitat.webservice.WebService;
import com.ridteam.livejournal.chtochitat.xmlrpc.entities.event.EventEntity;
import com.ridteam.livejournal.chtochitat.xmlrpc.entities.profile.Profile;

public class EventListAdapter extends SimpleCursorAdapter
{

	public static final String TAG = "EventListAdapter";

	private ImageLoader mImageLoader = null;

	public EventListAdapter(Context context, Cursor cursor)
	{
		super(context, 0, cursor, (String[]) DataBaseProvider.sEventProjectionMap.keySet().toArray(new String[0]), null, 0);
		mImageLoader = new ImageLoader(context);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor)
	{
		EventListItem item = (EventListItem) view;
		ViewHolder holder = (ViewHolder) item.getTag();
		EventEntity entity = DataBaseUtils.getEventFromCursor(cursor);
		if (entity != null)
		{
			holder.pEvent.setText(entity.getEvent());
			holder.pEventTime.setText(Utils.getFormattedDate(entity.getEventTime()));
			holder.pPoster.setText(entity.getPoster());
			holder.pSubject.setText(entity.getSubject());
			holder.pAvatar.setImageResource(R.drawable.nouserpic);
			setImageOfProfile(holder.pAvatar, entity.getPoster());
		}
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent)
	{
		EventListItem item = new EventListItem(context);
		return item;
	}

	private void setImageOfProfile(final ImageView iv, String nickName)
	{
		if (nickName != null)
		{
			if (nickName.contains("_"))
			{
				nickName = nickName.replace("_", "-");
			}
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
						mImageLoader.displayImage(result, iv, ImageType.PROFILE_THUMB);
					}
				};

			}.execute(nickName);
		}
	}

}
