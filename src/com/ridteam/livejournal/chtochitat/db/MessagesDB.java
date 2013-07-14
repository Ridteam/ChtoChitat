package com.ridteam.livejournal.chtochitat.db;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MessagesDB extends SQLiteOpenHelper {

	private static String DBNAME = "messages.db";
	private static int VERSION = 1;
	public static final String MSG_ID = "_id";
	public static final String MSG_DATEUPD = "dateupd";
	public static final String MSG_AUTHOR = "author";
	public static final String MSG_SUBJECT = "subject";
	public static final String MSG_BODY = "body";
	public static final String MSG_NUMOFCOMMENTS = "num_of_comments";
	public static final String MSG_AVATAR_LINK = "avatar_link";
	public static final String MSG_FL_SELECTED = "fl_selected";
	public static final String MSG_ITEMID = "_itemid";
	public static final String MSG_EVENTTIME = "_eventtime";

	private static final String DATABASE_TABLE_MESSAGES = "messages";

	public static final String PIC_ID = "_id";
	public static final String PIC_ID_MESSAGE = "id_message";
	public static final String PIC_EXT_LINK = "ext_link";
	public static final String PIC_INT_LINK = "int_link";

	private static final String DATABASE_TABLE_PICTURES = "pictures";

	public static final String TAG_ID = "_id";
	public static final String TAG_ID_MESSAGE = "id_message";
	public static final String TAG_TAG = "tag";

	private static final String DATABASE_TABLE_TAGS = "tags";

	private static final String CREATE_TABLE_MESSAGES = "create table " + DATABASE_TABLE_MESSAGES + " ( " + MSG_ID
			+ " integer primary key autoincrement , " + MSG_DATEUPD + " integer  , " + MSG_AUTHOR + "  text  , "
			+ MSG_SUBJECT + "  text  , " + MSG_BODY + "  text  , " + MSG_NUMOFCOMMENTS + "  integer  , "
			+ MSG_AVATAR_LINK + "  text  , " + MSG_FL_SELECTED + "  integer  , " + MSG_ITEMID + "  integer  , "
			+ MSG_EVENTTIME + "  text  ) ";
	private static final String CREATE_TABLE_PICTURES = "create table " + DATABASE_TABLE_PICTURES + " ( " + PIC_ID
			+ " integer primary key autoincrement , " + PIC_ID_MESSAGE + " integer  , " + PIC_EXT_LINK + "  text  , "
			+ PIC_INT_LINK + "  text  ) ";

	private static final String CREATE_TABLE_TAGS = "create table " + DATABASE_TABLE_TAGS + " ( " + TAG_ID
			+ " integer primary key autoincrement , " + TAG_ID_MESSAGE + " integer  , " + TAG_TAG + "  text  ) ";
	/** An instance variable for SQLiteDatabase */
	private SQLiteDatabase mDB;

	/** Constructor */
	public MessagesDB(Context context) {
		super(context, DBNAME, null, VERSION);
		this.mDB = getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_MESSAGES);
		db.execSQL(CREATE_TABLE_TAGS);
		db.execSQL(CREATE_TABLE_PICTURES);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_MESSAGES);
		db.execSQL(CREATE_TABLE_MESSAGES);
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_PICTURES);
		db.execSQL(CREATE_TABLE_PICTURES);
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_TAGS);
		db.execSQL(CREATE_TABLE_TAGS);

	}

	public Cursor getAllMessages() {
		return mDB.query(DATABASE_TABLE_MESSAGES, null, null, null, null, null, MSG_DATEUPD + " desc ");

	}

	public Cursor getAllTagsByMessage(int id_message) {
		return mDB.query(DATABASE_TABLE_TAGS, null, TAG_ID_MESSAGE + " = " + id_message, null, null, null, MSG_DATEUPD
				+ " desc ");

	}

	public String getTagByMessage(int id_message) {
		Cursor mCursor = mDB.query(DATABASE_TABLE_TAGS, null, TAG_ID_MESSAGE + " = " + id_message, null, null, null,
				MSG_DATEUPD + " desc ");
		String mTags = null;
		if (mCursor != null) {
			mTags = "";
			if (mCursor.moveToFirst()) {
				while (!mCursor.isAfterLast()) {
					mTags.concat("" + mCursor.getColumnIndex(TAG_TAG) + ",");
					mCursor.moveToNext();
				}
			}
			mCursor.close();
		}
		return mTags;
	}

	public Cursor getAllPicturesByMessage(int id_message) {
		return mDB.query(DATABASE_TABLE_PICTURES, null, TAG_ID_MESSAGE + " = " + id_message, null, null, null, null);

	}
}
