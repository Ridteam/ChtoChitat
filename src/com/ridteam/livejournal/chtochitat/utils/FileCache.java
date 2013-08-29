package com.ridteam.livejournal.chtochitat.utils;

import java.io.File;

import android.content.Context;

public class FileCache
{
	private File cacheDir;
	private static final String TAG = "FileCache";
	private static final String FOLDER = "ChtoChitatCacheDir";

	public FileCache(Context context)
	{
		// Find the dir to save cached images
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
		{
			cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), FOLDER);
		}
		else
		{
			cacheDir = context.getCacheDir();
		}
		if (!cacheDir.exists())
		{
			cacheDir.mkdirs();
		}
	}

	public File getFile(String url)
	{
		// I identify images by hashcode. Not a perfect solution, good for the
		// demo.
		String filename = String.valueOf(url.hashCode());
		// Another possible solution (thanks to grantland)
		// String filename = URLEncoder.encode(url);
		File f = new File(cacheDir, filename);
		return f;

	}

	public void clear()
	{
		Utils.logDebug(TAG, "clear");
		File[] files = cacheDir.listFiles();
		if (files == null)
		{
			return;
		}
		for (File f : files)
		{
			f.delete();
		}
	}
}
