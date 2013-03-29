package com.jr.haliotest.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Storage clas that stores responses and users last known location
 * 
 * @author jonathan
 * 
 */
public class Storage {

	private static final String RESPONSE = "Response";

	private static final String LATT = "LATT";

	private static final String LNG = "LNG";

	private static SharedPreferences sharedPreference;

	private static Context mConext;

	private static String PREF_NAME = "HalioTest";

	/**
	 * Save response
	 * 
	 * @param response
	 * @param context
	 */
	public static void saveResponse(String response, Context context) {
		mConext = context;
		sharedPreference = mConext.getSharedPreferences(PREF_NAME,
				mConext.MODE_PRIVATE);
		Editor editor = sharedPreference.edit();

		editor.putString(RESPONSE, response);

		editor.commit();

	}

	/**
	 * get previously saved response
	 * 
	 * @param context
	 * @return
	 */
	public static String getSavedResponse(Context context) {
		mConext = context;
		sharedPreference = mConext.getSharedPreferences(PREF_NAME,
				mConext.MODE_PRIVATE);

		return sharedPreference.getString(RESPONSE, null);
	}

	/**
	 * Save user position
	 * 
	 * @param position
	 * @param context
	 */
	public static void saveUserPosition(Position position, Context context) {
		mConext = context;
		sharedPreference = mConext.getSharedPreferences(PREF_NAME,
				mConext.MODE_PRIVATE);
		Editor editor = sharedPreference.edit();

		editor.putString(LATT, Double.toString(position.getLattitude()));
		editor.putString(LNG, Double.toString(position.getLongetude()));

		editor.commit();

	}

	/**
	 * get last saved position
	 * @param context
	 * @return
	 */
	public static Position getSavedPosition(Context context) {
		mConext = context;
		sharedPreference = mConext.getSharedPreferences(PREF_NAME,
				mConext.MODE_PRIVATE);

		return new Position(Double.parseDouble(sharedPreference.getString(LNG,
				null)), Double.parseDouble(sharedPreference.getString(LATT,
				null)));
	}

}
