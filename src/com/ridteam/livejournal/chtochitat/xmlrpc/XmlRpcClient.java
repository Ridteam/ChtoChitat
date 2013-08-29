package com.ridteam.livejournal.chtochitat.xmlrpc;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.ridteam.livejournal.chtochitat.utils.Utils;

public class XmlRpcClient
{

	public static final String TAG = "XmlRpcClient";

	private static final String PROTOCOL = "http";
	private static final String HOST = "www.livejournal.com";
	private static final String HOSST_PATH = "/interface/xmlrpc";
	private static final int PORT = 80;

	public XmlRpcClient()
	{

	}

	/**
	 * Send request to LiveJournal api
	 * 
	 * @param sRequest
	 *            - Formatted XMLRPC data
	 * @return request from LiveJournal
	 */
	public String getResponse(final String sRequest)
	{
		String sResponse = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		try
		{
			URI uri = new URI(PROTOCOL, null, HOST, PORT, HOSST_PATH, null, null);
			HttpPost httpPost = new HttpPost(uri);
			StringEntity sEntity = new StringEntity(sRequest);
			httpPost.setEntity(sEntity);
			HttpResponse response = httpClient.execute(httpPost);
			sResponse = EntityUtils.toString(response.getEntity());
			//TODO: need to delete
			PrintStream out = null;
			try
			{
				out = new PrintStream(new FileOutputStream("data/data/com.ridteam.livejournal.chtochitat/response.txt"));
				out.print(sResponse);
			}
			finally
			{
				if (out != null)
					out.close();
			}
			Utils.logDebug(TAG + " getResponse", sResponse);
		}
		catch (ClientProtocolException e)
		{
			Utils.logError(TAG, e);
		}
		catch (IOException e)
		{
			Utils.logError(TAG, e);
		}
		catch (URISyntaxException e)
		{
			Utils.logError(TAG, e);
		}
		return sResponse;
	}

}
