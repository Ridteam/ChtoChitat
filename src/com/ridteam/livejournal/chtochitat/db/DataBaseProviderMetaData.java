package com.ridteam.livejournal.chtochitat.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DataBaseProviderMetaData
{

	public static final String AUTHORITY = "com.ridteam.livejournal.chtochitat.db.DataBaseProvider";

	public static final class EventTableMetaData implements BaseColumns
	{
		public static final String TABLE_NAME = "event";
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + TABLE_NAME;
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + TABLE_NAME;

		// column name
		public static final String ITEM_ID = "itemid";
		public static final String EVENT = "event";
//		public static final String SUBJECT = "subject";
		public static final String TIME = "time";
		public static final String URL = "url";
		public static final String ANUM = "anum";
		public static final String TIMESTAMP = "timeStamp";
		public static final String REPLY_COUNT = "replyCount";
		public static final String COMMENT = "canComment";
		public static final String POSTER = "poster";
		
		public static final String DEFAULT_SORT_ORDER = ITEM_ID + " DESC";
	}

	public static final class ProfileTableMetaData implements BaseColumns
	{
		public static final String TABLE_NAME = "profile";
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + TABLE_NAME;
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + TABLE_NAME;

		// column name
		public static final String TITLE_CHANNEL = "titleChannel";
		public static final String LINK_CHANNEL = "linkChannel";
		public static final String DESCRIPTION = "description";
		public static final String LAST_BUILD_DATA = "lastBuildData";
		public static final String IMAGE_URL = "imageUrl";
		
		public static final String DEFAULT_SORT_ORDER = TITLE_CHANNEL + " DESC";
	}

}
