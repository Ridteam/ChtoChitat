package com.ridteam.livejournal.chtochitat.xmlrpc;

public class XmlRpcRequestField
{

	// field for authorization
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String VER = "ver";

	// field for getevents
	public static final String TRUNCATE = "truncate";
	public static final String PREFERSUBJECT = "prefersubject";
	public static final String NOPROPS = "noprops";
	public static final String NOTAGS = "notags";
	public static final String SELECTTYPE = "selecttype";
	public static final String LASTSYNC = "lastsync";
	public static final String YEAR = "year";
	public static final String MONTH = "month";
	public static final String DAY = "day";
	public static final String HOWMANY = "howmany/itemshow";
	public static final String SKIP = "skip";
	public static final String BEFORE = "before";
	public static final String BEFOREDATE = "beforedate";
	public static final String ITEMID = "itemid";
	public static final String ITEMIDS = "itemids";
	public static final String LINEENDINGS = "lineendings";
	public static final String USEJOURNAL = "usejournal";
	public static final String TRIM_WIDGETS = "trim_widgets";
	public static final String WIDGETS_IMG_LENGTH = "widgets_img_length";
	public static final String PARSELJTS = "parseljtags";

	// field for comment
	public static final String ITEMSHOW = "itemshow";

	// methods name
	public static final String METHOD_LOGIN = "LJ.XMLRPC.login";
	public static final String METHOD_GETEVENTS = "LJ.XMLRPC.getevents";
	public static final String METHOD_GETRECENTCOMMENTS = "LJ.XMLRPC.getrecentcomments";

}
