package com.ridteam.livejournal.chtochitat.webservice;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.util.Log;

import com.ridteam.livejournal.chtochitat.utils.Utils;

public class HttpTransport
{

	public static final String TAG = "HttpTransport";

	private static final int CONNECTION_TIMEOT = 10000;

	public InputStream executeGetRequest(String url)
	{
		Utils.logDebug(TAG, url);
		InputStream responseStream = null;
		HttpGet httpGet;
		HttpContext localContext = new BasicHttpContext();
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, CONNECTION_TIMEOT);
		HttpClient client = new DefaultHttpClient();

		try
		{
			URI uri = new URI(url);
			httpGet = new HttpGet(uri);
			httpGet.setParams(httpParameters);
			HttpResponse response = client.execute(httpGet, localContext);
			Utils.logDebug(TAG, String.valueOf(response.getStatusLine().getStatusCode()));
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
			{
				responseStream = response.getEntity().getContent();
			}
			else
			{
				Log.e(TAG, url);
				Log.e(TAG, response.getStatusLine().toString());
			}

		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
		return responseStream;
	}

	private String byteArrayToHexString(byte[] array)
	{
		StringBuffer hexString = new StringBuffer();
		for (byte b : array)
		{
			int intVal = b & 0xff;
			if (intVal < 0x10)
				hexString.append("0");
			hexString.append(Integer.toHexString(intVal));
		}
		return hexString.toString();
	}

}
