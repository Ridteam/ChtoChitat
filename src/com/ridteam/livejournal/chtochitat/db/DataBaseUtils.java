package com.ridteam.livejournal.chtochitat.db;

import java.util.Date;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.ridteam.livejournal.chtochitat.db.DataBaseProviderMetaData.EventTableMetaData;
import com.ridteam.livejournal.chtochitat.db.DataBaseProviderMetaData.ProfileTableMetaData;
import com.ridteam.livejournal.chtochitat.utils.Utils;
import com.ridteam.livejournal.chtochitat.xmlrpc.entities.event.EventEntity;
import com.ridteam.livejournal.chtochitat.xmlrpc.entities.profile.Profile;

public class DataBaseUtils
{

	private static final String TAG = "DataBaseUtils";

	public static void addAllEvent(Context context, List<EventEntity> listEvent)
	{
		ContentResolver contentResolver = context.getContentResolver();
		for (EventEntity event : listEvent)
		{
			ContentValues cv = new ContentValues();
			cv.put(EventTableMetaData.ITEM_ID, event.getItemid());
			cv.put(EventTableMetaData.EVENT, event.getEvent());
			cv.put(EventTableMetaData.TIME, event.getEventTime().getTime());
			cv.put(EventTableMetaData.URL, event.getUrl());
			cv.put(EventTableMetaData.ANUM, event.getAnum());
			cv.put(EventTableMetaData.TIMESTAMP, event.getEventTimeStamp().getTime());
			cv.put(EventTableMetaData.REPLY_COUNT, event.getReplyCount());
			cv.put(EventTableMetaData.COMMENT, event.isCanComment());
			cv.put(EventTableMetaData.POSTER, event.getPoster());

			Uri uri = EventTableMetaData.CONTENT_URI;
			Uri insertUri = contentResolver.insert(Uri.withAppendedPath(uri, String.valueOf(event.getItemid())), cv);
			Utils.logDebug(TAG, "insert to uri: " + insertUri);
		}
	}

	public static void addEvent(Context context, EventEntity event)
	{
		ContentResolver contentResolver = context.getContentResolver();

		ContentValues cv = new ContentValues();
		cv.put(EventTableMetaData.ITEM_ID, event.getItemid());
		cv.put(EventTableMetaData.EVENT, event.getEvent());
		cv.put(EventTableMetaData.TIME, event.getEventTime().getTime());
		cv.put(EventTableMetaData.URL, event.getUrl());
		cv.put(EventTableMetaData.ANUM, event.getAnum());
		cv.put(EventTableMetaData.TIMESTAMP, event.getEventTimeStamp().getTime());
		cv.put(EventTableMetaData.REPLY_COUNT, event.getReplyCount());
		cv.put(EventTableMetaData.COMMENT, event.isCanComment());
		cv.put(EventTableMetaData.POSTER, event.getPoster());

		Uri uri = EventTableMetaData.CONTENT_URI;
		Uri insertUri = contentResolver.insert(uri, cv);
		Utils.logDebug(TAG, "insert to uri: " + insertUri);
	}

	public static void addProfile(Context context, Profile profile)
	{
		ContentResolver contentResolver = context.getContentResolver();

		ContentValues cv = new ContentValues();
		cv.put(ProfileTableMetaData.TITLE_CHANNEL, profile.getTitleChanel());
		cv.put(ProfileTableMetaData.LINK_CHANNEL, profile.getLinkChanel());
		cv.put(ProfileTableMetaData.DESCRIPTION, profile.getDescription());
		cv.put(ProfileTableMetaData.LAST_BUILD_DATA, profile.getLastBuildDate().getTime());
		cv.put(ProfileTableMetaData.IMAGE_URL, profile.getImgUrl());

		Uri uri = EventTableMetaData.CONTENT_URI;
		Uri insertUri = contentResolver.insert(uri, cv);
		Utils.logDebug(TAG, "insert to uri: " + insertUri);
	}

	public static EventEntity getEventFromCursor(Cursor cursor)
	{
		EventEntity eventEntity = null;
		int itemId = cursor.getInt(cursor.getColumnIndex(EventTableMetaData.ITEM_ID));
		String event = cursor.getString(cursor.getColumnIndex(EventTableMetaData.EVENT));
		Date eventTime = Utils.getDateFromLong(cursor.getLong(cursor.getColumnIndex(EventTableMetaData.TIME)));
		String url = cursor.getString(cursor.getColumnIndex(EventTableMetaData.URL));
		int anum = cursor.getInt(cursor.getColumnIndex(EventTableMetaData.ANUM));
		Date eventTimeStamp = Utils.getDateFromLong(cursor.getLong(cursor.getColumnIndex(EventTableMetaData.TIMESTAMP)));
		int replyCount = cursor.getInt(cursor.getColumnIndex(EventTableMetaData.REPLY_COUNT));
		boolean isCanComment = (cursor.getInt(cursor.getColumnIndex(EventTableMetaData.COMMENT)) == 1);
		String poster = cursor.getString(cursor.getColumnIndex(EventTableMetaData.POSTER));
		eventEntity = new EventEntity(itemId, null, event, eventTime, url, anum, eventTimeStamp, replyCount, isCanComment, poster);
		return eventEntity;
	}

}
