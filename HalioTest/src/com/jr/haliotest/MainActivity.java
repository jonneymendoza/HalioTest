package com.jr.haliotest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.jr.haliotest.utils.IntentFilters;
import com.jr.haliotest.utils.Position;
import com.jr.haliotest.utils.Storage;
import com.jr.haliotest.utils.UserLocation;
import com.jr.haliotest.utils.places.CachedPlaces;
import com.jr.haliotest.utils.places.Place;
import com.jr.haliotest.utils.places.PlacesAdapter;
import com.jr.haliotest.utils.places.PlacesUtil;

public class MainActivity extends Activity implements IntentFilters {

	public static final String TAG = "MainActivity";

	private ListView listview;

	private Handler mHandler;

	private Position position;

	private UserLocation userLocation;

	private List<Place> places = new ArrayList<Place>();

	private PlacesAdapter adapter;

	private PlacesUtil placeUtil;

	private ProgressDialog progress;

	class FindRestaurants extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			// get users current location

			progress.show();

			Log.d("", "onPreExecute");
			if (position == null) {
				position = Storage.getSavedPosition(getApplicationContext());
			}

			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {

			// find restaurants near user using google place web service
			placeUtil = new PlacesUtil();

			try {
				places = placeUtil.getPlaces(getApplicationContext(),
						getString(R.string.resturant_type), getString(R.string.resturant_type), position);
			} catch (ClientProtocolException e) {

				Log.d(TAG, "FindRestaurants exception" + e.toString());
			} catch (JSONException e) {

				Log.d(TAG, "FindRestaurants exception" + e.toString());
			} catch (IOException e) {

				Log.d(TAG, "FindRestaurants exception" + e.toString());
			}

		

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			// load results into UI

			super.onPostExecute(result);

			adapter.updateList(places);

			// save it to cach so that other activities can access this without
			// sending a parcelable object intent for
			// this
			CachedPlaces.openCach().saveCachedPlaces(places);
			progress.dismiss();
			
			if(places.size() < 1){
				Toast.makeText(
						getApplicationContext(),
						getString(R.string.empty_place_list),
						Toast.LENGTH_LONG).show();
			}

		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		progress = new ProgressDialog(this);
		progress.setTitle(R.string.progress_title);

		progress.setMessage(getString(R.string.progress_text));

		mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				Log.d("", "Handling callback");
				super.handleMessage(msg);
				position = (Position) msg.obj;
				Storage.saveUserPosition(position, getApplicationContext());

				Toast.makeText(
						getApplicationContext(),
						position.getLattitude() + " " + position.getLongetude(),
						Toast.LENGTH_LONG).show();

				userLocation.stopSearchLocation();

				FindRestaurants findRestaurants = new FindRestaurants();
				findRestaurants.execute();
			}

		};

		userLocation = new UserLocation(this, mHandler);

		initializeViews();

		userLocation.startSearchLocation();
	}

	private void initializeViews() {
		listview = (ListView) findViewById(R.id.listView1);
		adapter = new PlacesAdapter(getApplicationContext(), places);
		listview.setAdapter(adapter);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		int itemId = item.getItemId();
		if (itemId == R.id.refresh) {
			FindRestaurants findRestaurants = new FindRestaurants();
			findRestaurants.execute();
			return true;
		} else if (itemId == R.id.map_view_item) {
			Intent intent = new Intent(this, MapActivity.class);
			intent.putExtra(INTENT_EXTRA_POSITION, position);
			startActivity(intent);
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_screen_menu, menu);
		return true;
	}

}
