package com.ridteam.livejournal.chtochitat.webservice;

import java.util.ArrayList;

import android.content.Context;

import com.ridteam.livejournal.chtochitat.xmlrpc.XmlRpcClient;
import com.ridteam.livejournal.chtochitat.xmlrpc.XmlRpcDeserializer;
import com.ridteam.livejournal.chtochitat.xmlrpc.XmlRpcRequest;
import com.ridteam.livejournal.chtochitat.xmlrpc.entities.event.EventEntity;

public class WebService
{

	public static final String TAG = "WebService";

	private Context mContext = null;

	public WebService()
	{

	}

	public WebService(Context context)
	{
		mContext = context;
	}

	/**
	 * Load list of events.<br><b>Warning:</b> need run in asynchronous task
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

}
