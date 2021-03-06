package com.ridteam.livejournal.chtochitat.xmlrpc.entities.event;

import java.util.ArrayList;
import java.util.Date;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.ridteam.livejournal.chtochitat.utils.Utils;
import com.ridteam.livejournal.chtochitat.xmlrpc.XmlRpcResponseField;
import com.ridteam.livejournal.chtochitat.xmlrpc.XmlRpcTag;

public class EventSaxParser extends DefaultHandler
{

	private static final String TAG = "EventSaxParser";

	private String mElementName = "";
	private String mName = "";
	private ArrayList<EventEntity> mListEntity = new ArrayList<EventEntity>();
	private EventEntity mEntity = null;
	private boolean isValue = false;
	private boolean isBase64 = false;

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
		if (mElementName.equals(XmlRpcTag.TAG_STRUCT))
		{
			mEntity = new EventEntity();
		}
		else if (mElementName.equals(XmlRpcTag.TAG_VALUE))
		{
			isValue = true;
		}
		else if (mElementName.equals(XmlRpcTag.TAG_BASE64))
		{
			isBase64 = true;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException
	{
		String resultValue = new String(ch, start, length);
		if (mElementName.equals(XmlRpcTag.TAG_NAME))
		{
			mName = resultValue;
			return;
		}

		setValueEntity(resultValue);
	}

	private void setValueEntity(String value)
	{
		if (isBase64)
		{
			value = Utils.decodeBase64(value);
		}
		if (isValue)
		{
			if (isValue && mName.equals(XmlRpcResponseField.ITEMID))
			{
				mEntity.setItemid(Integer.valueOf(value));
			}
			// else if (isValue && mName.equals(XmlRpcResponseField.SUBJECT))
			// {
			// mEntity.setSubject(value);
			// }
			else if (isValue && mName.equals(XmlRpcResponseField.EVENT))
			{
				mEntity.setEvent(value);
			}
			else if (isValue && mName.equals(XmlRpcResponseField.EVENTTIME))
			{
				mEntity.setEventTime(Utils.getDateFromDateString(value));
			}
			else if (isValue && mName.equals(XmlRpcResponseField.CAN_COMMENT))
			{
				switch (Integer.valueOf(value))
				{
					case 0:
						mEntity.setCanComment(false);
						break;
					case 1:
						mEntity.setCanComment(true);
						break;
					default:
						break;
				}

			}
			else if (isValue && mName.equals(XmlRpcResponseField.URL))
			{
				mEntity.setUrl(value);
			}
			else if (isValue && mName.equals(XmlRpcResponseField.ANUM))
			{
				mEntity.setAnum(Integer.valueOf(value));
			}
			else if (isValue && mName.equals(XmlRpcResponseField.EVENT_TIMESTAMP))
			{
				mEntity.setEventTimeStamp(Utils.getDateFromString(value));
			}
			else if (isValue && mName.equals(XmlRpcResponseField.REPLAY_COUNT))
			{
				mEntity.setReplyCount(Integer.valueOf(value));
			}
			else if (isValue && mName.equals(XmlRpcResponseField.POSTER))
			{
				mEntity.setPoster(value);
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		mElementName = "";
		if (qName.equals(XmlRpcTag.TAG_VALUE))
		{
			isValue = false;
		}
		if (qName.equals(XmlRpcTag.TAG_BASE64))
		{
			isBase64 = false;
		}
		else if (qName.equals(XmlRpcTag.TAG_STRUCT))
		{
			mListEntity.add(mEntity);
		}
	}

	@Override
	public void endDocument() throws SAXException
	{
		Utils.logDebug(TAG, "endDocument");
		super.endDocument();
	}

	public ArrayList<EventEntity> getListEvents()
	{
		return mListEntity;
	}

}
