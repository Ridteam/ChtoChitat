package com.ridteam.livejournal.chtochitat.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ridteam.livejournal.chtochitat.db.DataBaseProviderMetaData.EventTableMetaData;
import com.ridteam.livejournal.chtochitat.db.DataBaseProviderMetaData.ProfileTableMetaData;
import com.ridteam.livejournal.chtochitat.utils.Utils;

public class DataBaseHelper extends SQLiteOpenHelper
{

	private static final String TAG = "DataBaseHelper";

	public static final String DATABASE_NAME = "chtochitat.db";
	public static final int DATABASE_VERSION = 1;

	public DataBaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		Utils.logDebug(TAG, "create database");
		createEventTable(db);
		createProfileTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Utils.logDebug(TAG, "update database");
		Utils.logDebug(TAG, "change and update database old version: " + oldVersion + " on new version: " + newVersion);
		db.execSQL("DROP TABLE IF EXISTS " + EventTableMetaData.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + ProfileTableMetaData.TABLE_NAME);
		onCreate(db);
	}

	private void createEventTable(SQLiteDatabase db)
	{
		Utils.logDebug(TAG, "create " + EventTableMetaData.TABLE_NAME);
		String query = "CREATE TABLE " + EventTableMetaData.TABLE_NAME + " (" + EventTableMetaData._ID + " INTEGER PRIMARY KEY, " + EventTableMetaData.ITEM_ID + " INTEGER, " + EventTableMetaData.EVENT + " TEXT, " + EventTableMetaData.TIME + " BIGINT, " + EventTableMetaData.URL + " TEXT, " + EventTableMetaData.ANUM + " INTEGER," + EventTableMetaData.TIMESTAMP + " BIGINT, " + EventTableMetaData.REPLY_COUNT + " INTEGER, " + EventTableMetaData.COMMENT + " BOOLEAN, " + EventTableMetaData.POSTER + " TEXT );";
		Utils.logDebug(TAG, query);
		db.execSQL(query);
	}

	private void createProfileTable(SQLiteDatabase db)
	{
		Utils.logDebug(TAG, "create " + ProfileTableMetaData.TABLE_NAME);
		String query = "CREATE TABLE " + ProfileTableMetaData.TABLE_NAME + " (" + ProfileTableMetaData._ID + " INTEGER PRIMARY KEY, " + ProfileTableMetaData.TITLE_CHANNEL + " TEXT, " + ProfileTableMetaData.LINK_CHANNEL + " TEXT, " + ProfileTableMetaData.DESCRIPTION + " TEXT, " + ProfileTableMetaData.LAST_BUILD_DATA + " BIGINT, " + ProfileTableMetaData.IMAGE_URL + " TEXT );";
		Utils.logDebug(TAG, query);
		db.execSQL(query);
	}
}
