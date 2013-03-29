package com.jr.haliotest.utils;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * @author RichardsJ Load images in a seperate thread
 */
public class ImageLoader {

	private HashMap<String, SoftReference<Bitmap>> cachedImages = new HashMap<String, SoftReference<Bitmap>>();
	private String TAG = "ImageLoader";
	private ImageLoaderListener listener;
	
	private static final int THUMBNAIL_HEIGHT = 150;
	
	private static final int THUMBNAIL_WIDTH = 150;

	Bitmap thumbImage = null;

	public interface ImageLoaderListener {
		void onImageLoaded(Bitmap image, String url);
	}

	public ImageLoader(ImageLoaderListener listener) {
		this.listener = listener;

	}

	/**
	 * Get thumb image from url link and once downloaded and
	 * downsized/compressed, notify the callback with the new bitmap image. This
	 * method uses lazy loading technique where we cached the loaded images so
	 * thety can be reused again.
	 * 
	 * @param url
	 */
	public void getThumbImage(String url) {
		final String urlString = url;

		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				listener.onImageLoaded((Bitmap) msg.obj, urlString);

			}
		};

		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				Bitmap image;
				if (cachedImages.containsKey(urlString)) {
					image = cachedImages.get(urlString).get();

				} else {
					image = loadImageImage(urlString, calculateSampleSize(urlString));
					cachedImages.put(urlString,
							new SoftReference<Bitmap>(image));
				}
				thumbImage = image;
				Message msg = handler.obtainMessage(0, thumbImage);
				handler.sendMessage(msg);

			}

		};
		new Thread(runnable).start();
	}

	/**
	 * Calculate sample size use to downscale image
	 * 
	 * @return
	 */
	private int calculateSampleSize(String urlString) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		
		try {
			URL url = new URL(urlString);
			BitmapFactory.decodeStream(url.openConnection()
					.getInputStream(), null, options);
			
			int imageHeight = options.outHeight;
			int imageWidth = options.outWidth;
			if(imageWidth > THUMBNAIL_WIDTH || imageHeight > THUMBNAIL_HEIGHT){
				
				 final int heightRatio = Math.round((float) imageHeight / (float) THUMBNAIL_HEIGHT);
			        final int widthRatio = Math.round((float) imageWidth / (float) THUMBNAIL_WIDTH);
			        
			        return heightRatio < widthRatio ? heightRatio : widthRatio;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Get original image from url without downsizing/compressing it for full
	 * viewing
	 * 
	 * @param url
	 */
	public void getOriginalImage(String url) {
		final String urlString = url;
		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				listener.onImageLoaded((Bitmap) msg.obj, urlString);

			}
		};
		new Thread() {
			@Override
			public void run() {
				Bitmap image = loadImageImage(urlString, 0);
				Message msg = handler.obtainMessage(0, image);
				handler.sendMessage(msg);
			};
		}.start();
	}

	/**
	 * Download an image from a URL provided and apply some compression/downsize
	 * if specificed
	 * 
	 * @param urlString
	 *            The url link for the image
	 * @param sampleSize
	 *            the sample compression we wish to use
	 * @return the bitmap image
	 */
	private Bitmap loadImageImage(String urlString, int sampleSize) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		if (sampleSize != 0) {
			options.inSampleSize = sampleSize;
		}

		try {
			URL url = new URL(urlString);
			return BitmapFactory.decodeStream(url.openConnection()
					.getInputStream(), null, options);

		} catch (IOException e) {

			Log.e(TAG, "ImageLoader exception", e);
			return null;
		}

	}

}
