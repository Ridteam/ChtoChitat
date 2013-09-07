package com.ridteam.livejournal.chtochitat.db;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.ridteam.livejournal.chtochitat.db.DataBaseProviderMetaData.EventTableMetaData;
import com.ridteam.livejournal.chtochitat.db.DataBaseProviderMetaData.ProfileTableMetaData;
import com.ridteam.livejournal.chtochitat.utils.Utils;

public class DataBaseProvider extends ContentProvider
{

	private static final String TAG = "DataBaseProvider";

	public static HashMap<String, String> sEventProjectionMap;
	public static HashMap<String, String> sProfileProjectionMap;
	static
	{
		sEventProjectionMap = new HashMap<String, String>();
		sProfileProjectionMap = new HashMap<String, String>();

		// Event
		sEventProjectionMap.put(EventTableMetaData._ID, EventTableMetaData._ID);
		sEventProjectionMap.put(EventTableMetaData.ITEM_ID, EventTableMetaData.ITEM_ID);
		sEventProjectionMap.put(EventTableMetaData.EVENT, EventTableMetaData.EVENT);
		// sEventProjectionMap.put(EventTableMetaData.SUBJECT,
		// EventTableMetaData.SUBJECT);
		sEventProjectionMap.put(EventTableMetaData.TIME, EventTableMetaData.TIME);
		sEventProjectionMap.put(EventTableMetaData.URL, EventTableMetaData.URL);
		sEventProjectionMap.put(EventTableMetaData.ANUM, EventTableMetaData.ANUM);
		sEventProjectionMap.put(EventTableMetaData.TIMESTAMP, EventTableMetaData.TIMESTAMP);
		sEventProjectionMap.put(EventTableMetaData.REPLY_COUNT, EventTableMetaData.REPLY_COUNT);
		sEventProjectionMap.put(EventTableMetaData.COMMENT, EventTableMetaData.COMMENT);
		sEventProjectionMap.put(EventTableMetaData.POSTER, EventTableMetaData.POSTER);

		// Profile
		sProfileProjectionMap.put(ProfileTableMetaData._ID, ProfileTableMetaData._ID);
		sProfileProjectionMap.put(ProfileTableMetaData.TITLE_CHANNEL, ProfileTableMetaData.TITLE_CHANNEL);
		sProfileProjectionMap.put(ProfileTableMetaData.LINK_CHANNEL, ProfileTableMetaData.LINK_CHANNEL);
		sProfileProjectionMap.put(ProfileTableMetaData.DESCRIPTION, ProfileTableMetaData.DESCRIPTION);
		sProfileProjectionMap.put(ProfileTableMetaData.LAST_BUILD_DATA, ProfileTableMetaData.LAST_BUILD_DATA);
		sProfileProjectionMap.put(ProfileTableMetaData.IMAGE_URL, ProfileTableMetaData.IMAGE_URL);
	}

	private static UriMatcher sUriMatcher = null;
	private static final int INCOMING_EVENT_COLLECTION_URI_INDICATOR = 1;
	private static final int INCOMING_EVENT_SINGLE_URI_INDICATOR = 2;
	private static final int INCOMING_PROFILE_SINGLE_URI_INDICATOR = 3;
	static
	{
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(DataBaseProviderMetaData.AUTHORITY, EventTableMetaData.TABLE_NAME, INCOMING_EVENT_COLLECTION_URI_INDICATOR);
		sUriMatcher.addURI(DataBaseProviderMetaData.AUTHORITY, EventTableMetaData.TABLE_NAME + "/#", INCOMING_EVENT_SINGLE_URI_INDICATOR);
		sUriMatcher.addURI(DataBaseProviderMetaData.AUTHORITY, ProfileTableMetaData.TABLE_NAME + "/#", INCOMING_PROFILE_SINGLE_URI_INDICATOR);
	}

	private DataBaseHelper mOpenHelper = null;

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs)
	{
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		int count = 0;
		String rowId = null;
		switch (sUriMatcher.match(uri))
		{
			case INCOMING_EVENT_COLLECTION_URI_INDICATOR:
				count = db.delete(EventTableMetaData.TABLE_NAME, selection, selectionArgs);
				break;
			case INCOMING_EVENT_SINGLE_URI_INDICATOR:
				rowId = uri.getPathSegments().get(1);
				count = db.delete(EventTableMetaData.TABLE_NAME, EventTableMetaData._ID + "=" + rowId + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : ""), selectionArgs);
				break;
			case INCOMING_PROFILE_SINGLE_URI_INDICATOR:
				rowId = uri.getPathSegments().get(1);
				count = db.delete(ProfileTableMetaData.TABLE_NAME, ProfileTableMetaData._ID + "=" + rowId + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : ""), selectionArgs);
				break;
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri uri)
	{
		switch (sUriMatcher.match(uri))
		{
			case INCOMING_EVENT_COLLECTION_URI_INDICATOR:
				return EventTableMetaData.CONTENT_TYPE;
			case INCOMING_EVENT_SINGLE_URI_INDICATOR:
				return EventTableMetaData.CONTENT_ITEM_TYPE;
			case INCOMING_PROFILE_SINGLE_URI_INDICATOR:
				return ProfileTableMetaData.CONTENT_ITEM_TYPE;
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values)
	{
		ContentValues cv = null;
		if (values != null)
		{
			cv = new ContentValues(values);
		}
		else
		{
			cv = new ContentValues();
		}
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		long rowId = 0;
		switch (sUriMatcher.match(uri))
		{
			case INCOMING_EVENT_COLLECTION_URI_INDICATOR:
				rowId = db.insert(EventTableMetaData.TABLE_NAME, null, cv);
				break;
			case INCOMING_EVENT_SINGLE_URI_INDICATOR:
				int id = isEventExists(cv);
				if (id > 0)
				{
					rowId = update(Uri.withAppendedPath(EventTableMetaData.CONTENT_URI, String.valueOf(id)), cv, null, null);
				}
				else
				{
					rowId = db.insert(EventTableMetaData.TABLE_NAME, null, cv);
				}
				break;
			case INCOMING_PROFILE_SINGLE_URI_INDICATOR:
				rowId = db.insert(ProfileTableMetaData.TABLE_NAME, null, cv);
				break;
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
		if (rowId > 0)
		{
			Uri insertedUri = null;
			switch (sUriMatcher.match(uri))
			{
				case INCOMING_EVENT_COLLECTION_URI_INDICATOR:
				case INCOMING_EVENT_SINGLE_URI_INDICATOR:
					insertedUri = ContentUris.withAppendedId(EventTableMetaData.CONTENT_URI, rowId);
					break;
				case INCOMING_PROFILE_SINGLE_URI_INDICATOR:
					insertedUri = ContentUris.withAppendedId(ProfileTableMetaData.CONTENT_URI, rowId);
					break;
				default:
					throw new IllegalArgumentException("Unknown URI " + uri);
			}
			getContext().getContentResolver().notifyChange(insertedUri, null);
			return insertedUri;
		}
		throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public boolean onCreate()
	{
		mOpenHelper = new DataBaseHelper(getContext());
		return true;
	}

	private int isEventExists(ContentValues values)
	{
		String itemId = values.getAsString(EventTableMetaData.ITEM_ID);
		SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		String query = "SELECT " + EventTableMetaData._ID + " FROM " + EventTableMetaData.TABLE_NAME + " WHERE " + EventTableMetaData.ITEM_ID + "=" + itemId;
		Utils.logDebug(TAG, query);
		Cursor cursor = db.rawQuery(query, null);
		int rowId = 0;
		if (cursor.moveToFirst())
		{
			do
			{
				int rowColumnIndex = cursor.getColumnIndex(EventTableMetaData._ID);
				rowId = cursor.getInt(rowColumnIndex);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return rowId;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
	{
		String orderBy = null;
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch (sUriMatcher.match(uri))
		{
			case INCOMING_EVENT_COLLECTION_URI_INDICATOR:
				qb.setTables(EventTableMetaData.TABLE_NAME);
				qb.setProjectionMap(sEventProjectionMap);
				break;
			case INCOMING_EVENT_SINGLE_URI_INDICATOR:
				qb.setTables(EventTableMetaData.TABLE_NAME);
				qb.setProjectionMap(sEventProjectionMap);
				qb.appendWhere(EventTableMetaData.ITEM_ID + "=" + uri.getPathSegments().get(1));
				break;
			case INCOMING_PROFILE_SINGLE_URI_INDICATOR:
				qb.setTables(ProfileTableMetaData.TABLE_NAME);
				qb.setProjectionMap(sProfileProjectionMap);
				qb.appendWhere(ProfileTableMetaData._ID + "=" + uri.getPathSegments().get(1));
				break;
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
		// set order by
		switch (sUriMatcher.match(uri))
		{
			case INCOMING_EVENT_COLLECTION_URI_INDICATOR:
			case INCOMING_EVENT_SINGLE_URI_INDICATOR:
				if (TextUtils.isEmpty(sortOrder))
				{
					orderBy = EventTableMetaData.DEFAULT_SORT_ORDER;
				}
				else
				{
					orderBy = sortOrder;
				}
				break;
			case INCOMING_PROFILE_SINGLE_URI_INDICATOR:
				if (TextUtils.isEmpty(sortOrder))
				{
					orderBy = ProfileTableMetaData.DEFAULT_SORT_ORDER;
				}
				else
				{
					orderBy = sortOrder;
				}
				break;
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
		SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
	{
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		int count = 0;
		String rowId = null;
		switch (sUriMatcher.match(uri))
		{
			case INCOMING_EVENT_COLLECTION_URI_INDICATOR:
				count = db.update(EventTableMetaData.TABLE_NAME, values, selection, selectionArgs);
				break;
			case INCOMING_EVENT_SINGLE_URI_INDICATOR:
				rowId = uri.getPathSegments().get(1);
				count = db.update(EventTableMetaData.TABLE_NAME, values, EventTableMetaData._ID + "=" + rowId + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : ""), selectionArgs);
				break;
			case INCOMING_PROFILE_SINGLE_URI_INDICATOR:
				rowId = uri.getPathSegments().get(1);
				count = db.update(ProfileTableMetaData.TABLE_NAME, values, ProfileTableMetaData._ID + "=" + rowId + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : ""), selectionArgs);
				break;
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

}
