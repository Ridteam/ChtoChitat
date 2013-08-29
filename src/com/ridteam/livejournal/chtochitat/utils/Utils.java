package com.ridteam.livejournal.chtochitat.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;

public class Utils
{

	public static final String TAG = "Utils";
	private static final boolean isDebug = true;

	/**
	 * chack if internet connection is open
	 * 
	 * @param activity
	 * @return true if internet connection is open
	 */
	public static boolean isExistConnection(FragmentActivity activity)
	{
		if (activity != null)
		{
			try
			{
				ConnectivityManager conMgr = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
				if (conMgr != null && conMgr.getActiveNetworkInfo().isConnectedOrConnecting())
				{
					return true;
				}
			}
			catch (Exception e)
			{
				logError(TAG, e);
			}
		}
		return false;
	}

	/**
	 * write exception message to log
	 * 
	 * @param tag
	 * @param e
	 */
	public static void logError(final String tag, final Exception e)
	{
		if (e != null && e.getMessage() != null)
		{
			Log.e(tag, e.getMessage());
		}
	}

	/**
	 * write message of debug to log
	 * 
	 * @param tag
	 * @param sDebug
	 */
	public static void logDebug(final String tag, final String sDebug)
	{
		if (isDebug)
		{
			Log.d(tag, sDebug);
		}
	}

	public static boolean hasGingerbread()
	{
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
	}

	public static boolean hasHoneycomb()
	{
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	public static boolean hasHoneycombMR1()
	{
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
	}

	public static boolean hasJellyBean()
	{
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
	}

	public static boolean hasIceCreamSandwitch()
	{
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}

	/**
	 * encode MD5 to String
	 * 
	 * @param text
	 * @return encode text
	 */
	public static String encodeMD5(String text)
	{
		try
		{
			final MessageDigest md = MessageDigest.getInstance("MD5");
			return bytesToHex(md.digest(text.getBytes()));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * decode String to MD5
	 * 
	 * @param text
	 * @return decode text
	 */
	public static String decodeMD5(String text)
	{
		try
		{
			MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
			digest.update(text.getBytes());
			byte messageDigest[] = digest.digest();
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++)
				hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
			return hexString.toString();

		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
			return "";
		}
	}

	private static String bytesToHex(byte[] buf)
	{
		final StringBuilder b = new StringBuilder(buf.length * 2);

		for (int i = 0; i < buf.length; i++)
		{
			final int cell = (int) (buf[i] & 0xFF);
			if (cell < 16)
			{
				b.append("0");
			}

			b.append(Integer.toString(cell, 16));
		}

		return b.toString();
	}

	/**
	 * encode Base64 to text
	 * 
	 * @param text
	 * @return encode text
	 */
	public static String encodeBase64(String text)
	{
		byte[] data = null;
		try
		{
			data = text.getBytes("UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Base64.encodeToString(data, Base64.DEFAULT);
	}

	/**
	 * decode text to Base64
	 * 
	 * @param text
	 * @return decode text
	 */
	public static String decodeBase64(String text)
	{
		byte[] data = null;
		try
		{
			data = Base64.decode(text, Base64.DEFAULT);
		}
		catch (IllegalArgumentException e)
		{
			logError(TAG + " decodeBase64", e);
			return text;
		}
		try
		{
			return new String(data, "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Check String for Base64
	 * 
	 * @param text
	 * @return if text is Base64 return true
	 */
	public static boolean isBase64(String text)
	{
		if (text.replace(" ", "").length() % 4 != 0)
		{
			return false;
		}
		return true;
	}

	// TODO: test
	public static void writeStringToFile(String text, Context context, String filename)
	{
		PrintStream out = null;
		File file = new File(context.getFilesDir().getPath().toString() + File.separator + filename + ".txt");
		if (!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		Log.i(TAG, file.getAbsolutePath());
		try
		{
			out = new PrintStream(new FileOutputStream(file.getAbsolutePath()));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		out.print(text);
		out.close();
	}

	public static void copyStream(InputStream is, OutputStream os)
	{
		final int buffer_size = 1024;
		try
		{
			byte[] bytes = new byte[buffer_size];
			for (;;)
			{
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		}
		catch (Exception ex)
		{
		}
	}

}
