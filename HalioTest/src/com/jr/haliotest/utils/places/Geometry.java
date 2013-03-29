
package com.jr.haliotest.utils.places;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author RichardsJ
 *
 */
public class Geometry {

	public static final String KEY_LOCATION = "location";
	private Location location;


	/**
	 * constructor that creates a geometry object from a jsonObject passed here
	 *
	 * @param geometry
	 * @throws JSONException
	 */
	public Geometry(JSONObject geometry) throws JSONException {
		location = new Location(geometry.getJSONObject(KEY_LOCATION));
	}


	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public class Location {
		public static final String KEY_LAT = "lat";
		public static final String KEY_LONG = "lng";

		private double lat;
		private double lng;

		/**
		 * @param jsonObject
		 * @throws JSONException
		 */
		public Location(JSONObject jsonObject) throws JSONException {
			setLat(jsonObject.getDouble(KEY_LAT));
			setLng(jsonObject.getDouble(KEY_LONG));
		}

		public double getLat() {
			return lat;
		}

		public void setLat(double lat) {
			this.lat = lat;
		}

		public double getLng() {
			return lng;
		}

		public void setLng(double lng) {
			this.lng = lng;
		}

	}

}
