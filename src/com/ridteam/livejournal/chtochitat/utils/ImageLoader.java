package com.ridteam.livejournal.chtochitat.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.widget.ImageView;

import com.ridteam.livejournal.chtochitat.R;
import com.ridteam.livejournal.chtochitat.webservice.HttpTransport;

public class ImageLoader
{
	public static final String TAG = "UserProfileImageLoader";

	private MemoryCache memoryCache = new MemoryCache();
	private FileCache fileCache = null;
	private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
	private ExecutorService executorService = null;
	private int COUNT_THREAD_IN_POOL = 5;

	private final static int DEFAULT_REQUIRED_SIZE = 100;

	public ImageLoader(Context context)
	{
		fileCache = new FileCache(context);
		executorService = Executors.newFixedThreadPool(COUNT_THREAD_IN_POOL);
	}

	private int getDefaultImageResource(ImageType type)
	{
		switch (type)
		{
			case PROFILE_THUMB:
				return R.drawable.nouserpic;
			default:
				return R.drawable.nouserpic;
		}
	}

	public enum ImageType
	{
		PROFILE_THUMB;
	}

	public void displayImage(String url, ImageView imageView, ImageType type)
	{
		imageViews.put(imageView, url);
		Bitmap bitmap = memoryCache.get(url);
		if (bitmap != null)
		{
			imageView.setImageBitmap(bitmap);
		}
		else
		{
			queuePhoto(url, imageView, type, DEFAULT_REQUIRED_SIZE);
			imageView.setImageResource(getDefaultImageResource(type));
		}
	}

	private void queuePhoto(String url, ImageView imageView, ImageType type, int requiredSize)
	{
		PhotoToLoad p = new PhotoToLoad(url, imageView, type, requiredSize);
		executorService.submit(new PhotosLoader(p));
	}

	private Bitmap getBitmap(String url, int requiredSize)
	{
		File f = fileCache.getFile(url);

		Bitmap b = decodeFile(f, requiredSize);
		if (b != null)
			return b;

		Utils.logDebug(TAG, "load image from " + url);
		Bitmap bitmap = null;
		InputStream is = null;
		OutputStream os = null;
		try
		{
			HttpTransport httpTransport = new HttpTransport();
			is = httpTransport.executeGetRequest(url);
			os = new FileOutputStream(f);
			Utils.copyStream(is, os);
			bitmap = decodeFile(f, requiredSize);
		}
		catch (Throwable ex)
		{
			ex.printStackTrace();
			if (ex instanceof OutOfMemoryError)
				memoryCache.clear();
		}
		finally
		{
			if (os != null)
			{
				try
				{
					os.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return bitmap;
	}

	// decodes image and scales it to reduce memory consumption
	private Bitmap decodeFile(File f, int requiredSize)
	{
		try
		{
			// decode image size

			ExifInterface exif;
			int rotation = ExifInterface.ORIENTATION_NORMAL;
			try
			{
				exif = new ExifInterface(f.getAbsolutePath());
				rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// Find the correct scale value. It should be the power of 2.
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true)
			{
				if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			Bitmap result = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
			if (rotation == ExifInterface.ORIENTATION_NORMAL || rotation == 0)
			{
				return result;
			}
			else
			{
				Matrix m = getMatrixForRotation(rotation);
				Bitmap bitmap = Bitmap.createBitmap(result, 0, 0, result.getWidth(), result.getHeight(), m, true);
				// result.recycle();
				return bitmap;
			}
		}
		catch (FileNotFoundException e)
		{
		}
		return null;
	}

	private static Matrix getMatrixForRotation(int rotation)
	{
		Matrix m = new Matrix();
		int rotationInDegrees = exifToDegrees(rotation);
		if (rotation != 0f)
		{
			m.preRotate(rotationInDegrees);
		}
		return m;
	}

	private static int exifToDegrees(int exifOrientation)
	{
		if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90)
		{
			return 90;
		}
		else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180)
		{
			return 180;
		}
		else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270)
		{
			return 270;
		}
		return 0;
	}

	// Task for the queue
	private class PhotoToLoad
	{
		public String url;
		public ImageView imageView;
		public ImageType type;
		public int requiredSize;

		public PhotoToLoad(String u, ImageView i, ImageType type, int size)
		{
			url = u;
			imageView = i;
			this.type = type;
			this.requiredSize = size;
		}
	}

	class PhotosLoader implements Runnable
	{
		PhotoToLoad photoToLoad;

		PhotosLoader(PhotoToLoad photoToLoad)
		{
			this.photoToLoad = photoToLoad;
		}

		@Override
		public void run()
		{
			if (imageViewReused(photoToLoad))
				return;
			Bitmap bmp = getBitmap(photoToLoad.url, photoToLoad.requiredSize);
			memoryCache.put(photoToLoad.url, bmp);
			if (imageViewReused(photoToLoad))
				return;
			BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
			Activity a = (Activity) photoToLoad.imageView.getContext();
			a.runOnUiThread(bd);
		}
	}

	boolean imageViewReused(PhotoToLoad photoToLoad)
	{
		String tag = imageViews.get(photoToLoad.imageView);
		if (tag == null || !tag.equals(photoToLoad.url))
			return true;
		return false;
	}

	// Used to display bitmap in the UI thread
	class BitmapDisplayer implements Runnable
	{
		Bitmap bitmap;
		PhotoToLoad photoToLoad;

		public BitmapDisplayer(Bitmap b, PhotoToLoad p)
		{
			bitmap = b;
			photoToLoad = p;
		}

		public void run()
		{
			if (imageViewReused(photoToLoad))
				return;
			if (bitmap != null)
				photoToLoad.imageView.setImageBitmap(bitmap);
			else
				photoToLoad.imageView.setImageResource(getDefaultImageResource(photoToLoad.type));
		}
	}

	public void clearCache()
	{
		memoryCache.clear();
		fileCache.clear();
	}

}
