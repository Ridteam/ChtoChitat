package com.ridteam.livejournal.chtochitat.xmlrpc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ridteam.livejournal.chtochitat.utils.Utils;
import com.ridteam.livejournal.chtochitat.xmlrpc.entities.event.EventEntity;
import com.ridteam.livejournal.chtochitat.xmlrpc.entities.event.EventSaxParser;

/**
 * Deserialize request of XMLRPC protocol
 */
public class XmlRpcDeserializer
{

	private static final String TAG = "XmlRpcDeserializer";

	public XmlRpcDeserializer()
	{

	}

	/**
	 * 
	 * @param request
	 *            - request from LiveJournal XMLRPC protocol
	 * @return list of events
	 */
	public ArrayList<EventEntity> getEvents(final String request)
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		ArrayList<EventEntity> listEventEntity = null;
		ByteArrayInputStream bais = null;
		InputSource is = null;
		try
		{
			SAXParser parser = factory.newSAXParser();
			EventSaxParser saxp = new EventSaxParser();
			bais = new ByteArrayInputStream(request.getBytes());
			is = new InputSource(bais);
			parser.parse(is, saxp);
			listEventEntity = saxp.getListEvents();
		}
		catch (ParserConfigurationException e)
		{
			Utils.logError(TAG, e);
		}
		catch (SAXException e)
		{
			Utils.logError(TAG, e);
		}
		catch (IOException e)
		{
			Utils.logError(TAG, e);
		}
		finally
		{
			if (bais != null)
			{
				try
				{
					bais.close();
				}
				catch (IOException e)
				{
					Utils.logError(TAG, e);
				}
			}
		}
		return listEventEntity;
	}

	public EventEntity getEvent(final String request)
	{
		ArrayList<EventEntity> listEventEntity = getEvents(request);
		if (listEventEntity != null && listEventEntity.size() > 0)
		{
			return listEventEntity.get(0);
		}
		else
		{
			return null;
		}
	}

}
