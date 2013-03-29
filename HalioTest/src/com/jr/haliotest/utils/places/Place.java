/**
 * Copyright Monitise
 */
package com.jr.haliotest.utils.places;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.util.Log;

/**
 * @author RichardsJ
 */
public class Place {

	public static final String KEY_FORMATTED_ADDRESS = "formatted_address";
	private String formattedAddress;

	public static final String KEY_GEOMETRY = "geometry";
	private Geometry geometry;

	public static final String KEY_ICON = "icon";
	private String icon;

	public static final String KEY_ID = "id";
	private String id;

	public static final String KEY_NAME = "name";
	private String name;

	public static final String KEY_RATING = "rating";
	private String rating;

	public static final String KEY_REFERENCE = "reference";
	private String reference;

	public static final String KEY_TYPES = "types";
	private String[] types;

	/**
	 * Get JSON array and create Place object out of it
	 *
	 * @param json
	 */
	public Place(JSONObject json) {

		try {
			setFormattedAddress(json.getString(KEY_FORMATTED_ADDRESS));
			setGeometry(json.getJSONObject(KEY_GEOMETRY));
			setIcon(json.getString(KEY_ICON));
			setId(json.getString(KEY_ID));
			setName(json.getString(KEY_NAME));
			setRating(json.getString(KEY_RATING));
			setReference(json.getString(KEY_REFERENCE));
			setTypes(json.getJSONArray(KEY_TYPES));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e("", "Place exception", e);
		}

	}

	public String getFormattedAddress() {
		return formattedAddress;
	}

	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(JSONObject geometry) throws JSONException {

		this.geometry = new Geometry(geometry);

	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String[] getTypes() {
		return types;
	}

	public void setTypes(JSONArray types) throws JSONException {

		int typeSize = types.length();
		this.types = new String[typeSize];

		for (int i = 0; i < typeSize; i++) {
			this.types[i] = types.getString(i);
		}


	}

}
