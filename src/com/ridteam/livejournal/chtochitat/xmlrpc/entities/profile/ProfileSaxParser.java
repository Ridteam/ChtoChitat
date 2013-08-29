package com.ridteam.livejournal.chtochitat.xmlrpc.entities.profile;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.ridteam.livejournal.chtochitat.utils.Utils;
import com.ridteam.livejournal.chtochitat.xmlrpc.XmlRpcTag;

public class ProfileSaxParser extends DefaultHandler
{

	private static final String TAG = "ProfileSaxParser";

	private String mElementName = "";
	private Profile profile = new Profile();
	private boolean isImage = false;

	@Override
	public void startDocument() throws SAXException
	{
		Utils.logDebug(TAG, "startDocument");
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		mElementName = qName;
		if (mElementName.equals(XmlRpcTag.TAG_IMAGE))
		{
			isImage = true;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException
	{
		String resultValue = new String(ch, start, length);
		setValueEntity(resultValue);
	}

	private void setValueEntity(String value)
	{
		if (isImage)
		{
			if (mElementName.equals(XmlRpcTag.TAG_URL))
			{
				profile.setImgUrl(value);
			}
		}
		else
		{
			if (mElementName.equals(XmlRpcTag.TAG_DESCRIPTION))
			{
				profile.setDescription(value);
			}
			else if (mElementName.equals(XmlRpcTag.TAG_LAST_BUILD_DATE))
			{
				profile.setLastBuildDate(value);
			}
			else if (mElementName.equals(XmlRpcTag.TAG_TITLE))
			{
				profile.setTitleChanel(value);
			}
			else if (mElementName.equals(XmlRpcTag.TAG_LINK))
			{
				profile.setLinkChanel(value);
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		mElementName = "";
		if (qName.equals(XmlRpcTag.TAG_IMAGE))
		{
			isImage = false;
		}
	}

	@Override
	public void endDocument() throws SAXException
	{
		Utils.logDebug(TAG, "endDocument");
		super.endDocument();
	}

	public Profile getProfile()
	{
		return profile;
	}

}
