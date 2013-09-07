package com.ridteam.livejournal.chtochitat.xmlrpc.entities.profile;

import java.util.Date;

public class Profile
{

	private String titleChanel = null;
	private String linkChanel = null;
	private String description = null;
	private Date lastBuildDate = null;
	private String imgUrl = null;

	public Profile()
	{}

	public Profile(String titleChanel, String linkChanel, String description, Date lastBuildDate, String imgUrl)
	{
		this.setTitleChanel(titleChanel);
		this.setLinkChanel(linkChanel);
		this.setDescription(description);
		this.setLastBuildDate(lastBuildDate);
		this.setImgUrl(imgUrl);
	}

	public String getTitleChanel()
	{
		return titleChanel;
	}

	public void setTitleChanel(String titleChanel)
	{
		this.titleChanel = titleChanel;
	}

	public String getLinkChanel()
	{
		return linkChanel;
	}

	public void setLinkChanel(String linkChanel)
	{
		this.linkChanel = linkChanel;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Date getLastBuildDate()
	{
		return lastBuildDate;
	}

	public void setLastBuildDate(Date lastBuildDate)
	{
		this.lastBuildDate = lastBuildDate;
	}

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

}
