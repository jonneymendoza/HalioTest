package com.jr.haliotest.utils.places;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.jr.haliotest.utils.Position;
import com.jr.haliotest.utils.Storage;

/**
 * Get places from google api web service and store teh response into the shared
 * preferences so that next time the user launches or refreshes the searching of
 * places, it will load previously saved places. This class also parses the
 * request from json to actual java POJO that is used to populate the UI of the
 * app. This class also checks the network connection before attempting to make
 * a Rest API call to the google server places API.
 * 
 * @author RichardsJ
 */
public class PlacesUtil {

	private static final String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?";

	private static final String apiKey = "AIzaSyCKILt1GAdl3bs3-XBfiB64P6CuqLt_ZWo";

	private List<Place> places;

	private Position position;

	private static final String KEY_RESULTS = "results";

	/**
	 * default radius to do a search in meters
	 */
	private static final double DEFAULT_RADIUS = 8046;

	private static final String KEY_KEY_PARAM = "key";

	private static final String KEY_TYPE_PARAM = "types";

	private String type;

	private String query;

	private static final String KEY_QUERY = "query";

	private static final String KEY_LOCATION_PARAM = "location";

	private static final String KEY_RADIUS_PARAM = "radius";

	private static final String KEY_SENSOR_PARAM = "sensor";

	private Context mConext;


	/**
	 * 
	 * Get list of places near area
	 * @param context The context
	 * @param type
	 *            the type of place we are looking for
	 * @param query
	 *            the query string
	 * @param position
	 *            the current position of user
	 * @return a list of places
	 * @throws JSONException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public List<Place> getPlaces(Context context, String type, String query,
			Position position) throws JSONException, ClientProtocolException,
			IOException {
		places = new ArrayList<Place>();
		mConext = context;
		this.query = query;
		this.type = type;
		this.position = position;

		JSONArray array = getJsonResponse();

		for (int i = 0; i < array.length(); i++) {
			places.add(new Place(array.getJSONObject(i)));
		}
		return places;
	}

	/**
	 * Store response into a JSON Array
	 * 
	 * @return
	 * @throws JSONException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private JSONArray getJsonResponse() throws JSONException,
			ClientProtocolException, IOException {

		String httpResonse = makeHtpRequest();

		// if response is null, try and retrieve saved response from shared
		// preference
		JSONObject json = httpResonse != null ? new JSONObject(makeHtpRequest())
				: new JSONObject(Storage.getSavedResponse(mConext));

		JSONArray jsonArray = json.getJSONArray(KEY_RESULTS);

		return jsonArray;
	}

	/**
	 * Make RESTful HTTP call to google places API
	 * 
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private String makeHtpRequest() throws ClientProtocolException, IOException {
		if (hasNetworkConnection()) {
			HttpClient httpClient = new DefaultHttpClient();

			String parsedURL = url
					+ URLEncodedUtils.format(getParams(), HTTP.UTF_8);

			Log.d("", "parsedURL = " + parsedURL);

			HttpRequestBase requestMethod = new HttpGet(parsedURL);

			HttpResponse response = httpClient.execute(requestMethod);

			String responseString = EntityUtils.toString(response.getEntity());
			Storage.saveResponse(responseString, mConext);
			return responseString;
		} else {
			return null;
		}

	}

	/**
	 * Construct request parameters
	 * 
	 * @return
	 */
	private List<NameValuePair> getParams() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair(KEY_QUERY, query));
		params.add(new BasicNameValuePair(KEY_LOCATION_PARAM, position
				.getLattitude() + "," + position.getLongetude()));
		params.add(new BasicNameValuePair(KEY_RADIUS_PARAM, Double
				.toString(DEFAULT_RADIUS)));
		params.add(new BasicNameValuePair(KEY_TYPE_PARAM, type));
		params.add(new BasicNameValuePair(KEY_SENSOR_PARAM, "false"));
		params.add(new BasicNameValuePair(KEY_KEY_PARAM, apiKey));

		return params;

	}

	/**
	 * Check if device has a network connection
	 * 
	 * @return
	 */
	private boolean hasNetworkConnection() {

		NetworkInfo info = (NetworkInfo) ((ConnectivityManager) mConext
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo();

		if (info == null || !info.isConnected()) {
			return false;
		}
		return true;

	}

}
