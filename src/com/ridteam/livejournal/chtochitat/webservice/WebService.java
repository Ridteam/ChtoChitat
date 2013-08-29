package com.ridteam.livejournal.chtochitat.webservice;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import android.content.Context;

import com.ridteam.livejournal.chtochitat.utils.Utils;
import com.ridteam.livejournal.chtochitat.xmlrpc.XmlRpcClient;
import com.ridteam.livejournal.chtochitat.xmlrpc.XmlRpcDeserializer;
import com.ridteam.livejournal.chtochitat.xmlrpc.XmlRpcRequest;
import com.ridteam.livejournal.chtochitat.xmlrpc.entities.event.EventEntity;
import com.ridteam.livejournal.chtochitat.xmlrpc.entities.profile.Profile;
import com.ridteam.livejournal.chtochitat.xmlrpc.entities.profile.ProfileSaxParser;

public class WebService
{

	public static final String TAG = "WebService";

	public static final String URL_USER_PROFILE = "http://%s.livejournal.com/data/rss";

	private Context mContext = null;

	public WebService()
	{

	}

	public WebService(Context context)
	{
		mContext = context;
	}

	/**
	 * Load list of events.<br>
	 * <b>Warning:</b> need run in asynchronous task
	 * 
	 * @param login
	 * @param password
	 * @return
	 */
	public ArrayList<EventEntity> getListOfMessagesFromWall(String login, String password)
	{
		ArrayList<EventEntity> listEventEntity = new ArrayList<EventEntity>();
		XmlRpcRequest xmlRequest = new XmlRpcRequest();
		XmlRpcClient xmlClient = new XmlRpcClient();
		String result = xmlClient.getResponse(xmlRequest.getEventsRequestXML(login, password, null));
		if (result != null)
		{
			XmlRpcDeserializer xmlDeserializer = new XmlRpcDeserializer();
			listEventEntity = xmlDeserializer.getEvents(result);
		}
		return listEventEntity;
	}

	/**
	 * Load event.<br>
	 * <b>Warning:</b> need run in asynchronous task
	 * 
	 * @param login
	 * @param password
	 * @param itemId
	 * @return
	 */
	public EventEntity getMessageFromWall(String login, String password, int itemId)
	{
		EventEntity event = new EventEntity();
		XmlRpcRequest xmlRequest = new XmlRpcRequest();
		XmlRpcClient xmlClient = new XmlRpcClient();
		String result = xmlClient.getResponse(xmlRequest.getEventRequestXML(login, password, null, itemId));
		if (result != null)
		{
			XmlRpcDeserializer xmlDeserializer = new XmlRpcDeserializer();
			event = xmlDeserializer.getEvent(result);
		}
		return event;
	}

	/**
	 * Load profile.<br>
	 * <b>Warning:</b> need run in asynchronous task
	 * 
	 * @param userNickName
	 *            - nick of user
	 * @return
	 */
	public Profile getProfile(String userNickName)
	{
		String url = String.format(URL_USER_PROFILE, userNickName);
		HttpTransport httpTransport = new HttpTransport();
		InputStream result = httpTransport.executeGetRequest(url);
		Profile profile = null;
		if (result != null)
		{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			try
			{
				SAXParser parser = factory.newSAXParser();
				ProfileSaxParser saxp = new ProfileSaxParser();
				parser.parse(result, saxp);
				profile = saxp.getProfile();
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
				if (result != null)
				{
					try
					{
						result.close();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		return profile;
	}

}
