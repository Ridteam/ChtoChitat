package com.ridteam.livejournal.chtochitat.xmlrpc.entities.event;

public class EventEntity
{

	private int mItemid;
	private String mSubject = null;
	private String mEvent = null;
	private String mEventTime = null;
	private String mUrl = null;
	private int mAnum;
	private String mEventTimeStamp = null;
	private int mReplyCount;
	private String mSecurity = null;
	private String mAllowmask = null;
	private boolean isCanComment = true;
	private String mPoster = null;

	public EventEntity()
	{

	}

	public EventEntity(final int itemid, final String subject, final String event, final String eventTime, final String url, final int anum, final String eventTimeStamp, final int replyCount, final String security, final String allowmask, boolean isCanComment, final String poster)
	{
		this.setItemid(itemid);
		this.setSubject(subject);
		this.setEvent(event);
		this.setEventTime(eventTime);
		this.setUrl(url);
		this.setAnum(anum);
		this.setEventTimeStamp(eventTimeStamp);
		this.setReplyCount(replyCount);
		this.setSecurity(security);
		this.setAllowmask(allowmask);
		this.setCanComment(isCanComment);
		this.setPoster(poster);
	}

	public int getItemid()
	{
		return mItemid;
	}

	public void setItemid(int itemid)
	{
		this.mItemid = itemid;
	}

	public String getSubject()
	{
		return mSubject;
	}

	public void setSubject(String subject)
	{
		this.mSubject = subject;
	}

	public String getEvent()
	{
		return mEvent;
	}

	public void setEvent(String event)
	{
		this.mEvent = event;
	}

	public String getEventTime()
	{
		return mEventTime;
	}

	public void setEventTime(String eventTime)
	{
		this.mEventTime = eventTime;
	}

	public String getUrl()
	{
		return mUrl;
	}

	public void setUrl(String url)
	{
		this.mUrl = url;
	}

	public int getAnum()
	{
		return mAnum;
	}

	public void setAnum(int anum)
	{
		this.mAnum = anum;
	}

	public String getEventTimeStamp()
	{
		return mEventTimeStamp;
	}

	public void setEventTimeStamp(String eventTimeStamp)
	{
		this.mEventTimeStamp = eventTimeStamp;
	}

	public int getReplyCount()
	{
		return mReplyCount;
	}

	public void setReplyCount(int replyCount)
	{
		this.mReplyCount = replyCount;
	}

	public String getSecurity()
	{
		return mSecurity;
	}

	public void setSecurity(String security)
	{
		this.mSecurity = security;
	}

	public String getAllowmask()
	{
		return mAllowmask;
	}

	public void setAllowmask(String allowmask)
	{
		this.mAllowmask = allowmask;
	}

	public boolean isCanComment()
	{
		return isCanComment;
	}

	public void setCanComment(boolean isCanComment)
	{
		this.isCanComment = isCanComment;
	}

	public String getPoster()
	{
		return mPoster;
	}

	public void setPoster(String poster)
	{
		this.mPoster = poster;
	}

}
