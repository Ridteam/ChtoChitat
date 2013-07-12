package com.ridteam.livejournal.chtochitat.xmlrpc;

import java.io.IOException;

import com.ridteam.livejournal.chtochitat.xmlrpc.entities.BaseEntity;

public class XmlRpcRequest
{

	public static final String TAG = "XmlRpcClient";

	// TODO: fake
	public static final String LOGIN = "ridteamtest";
	public static final String PASSWORD = "Ridteam_6";
	public static final String JOURNAL = "chto_chitat";

	public XmlRpcRequest()
	{

	}

	/**
	 * Get formatted xml with request parametres for authorization
	 * 
	 * @param login
	 * @param password
	 * @return String formatted xml
	 */
	public String getAuthRequestXML(String login, String password)
	{
		BaseEntity entity = new BaseEntity();
		entity.put(XmlRpcRequestField.USERNAME, login);
		entity.put(XmlRpcRequestField.PASSWORD, password);
		entity.put(XmlRpcRequestField.VER, 1);

		XmlRpcSerializer serializer = new XmlRpcSerializer();
		String result = null;
		try
		{
			result = serializer.writeParamsToXmlRpc(entity, XmlRpcRequestField.METHOD_LOGIN);
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (IllegalStateException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	public String getEventsRequestXML(String login, String password, String journal)
	{
		BaseEntity entity = new BaseEntity();
		entity.put(XmlRpcRequestField.USERNAME, LOGIN);
		entity.put(XmlRpcRequestField.PASSWORD, PASSWORD);
		entity.put(XmlRpcRequestField.VER, 1);
		entity.put(XmlRpcRequestField.TRUNCATE, 50);
		entity.put(XmlRpcRequestField.PREFERSUBJECT, Boolean.valueOf(false));
		entity.put(XmlRpcRequestField.NOPROPS, Boolean.valueOf(false));
		entity.put(XmlRpcRequestField.NOTAGS, Boolean.valueOf(false));
		entity.put(XmlRpcRequestField.SELECTTYPE, String.valueOf("lastn"));
		entity.put(XmlRpcRequestField.LINEENDINGS, String.valueOf("space"));
		entity.put(XmlRpcRequestField.USEJOURNAL, JOURNAL);
		entity.put(XmlRpcRequestField.TRIM_WIDGETS, 50);
		entity.put(XmlRpcRequestField.PARSELJTS, Boolean.valueOf(true));

		XmlRpcSerializer serializer = new XmlRpcSerializer();
		String result = null;
		try
		{
			result = serializer.writeParamsToXmlRpc(entity, XmlRpcRequestField.METHOD_GETEVENTS);
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (IllegalStateException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return result;
	}

}
