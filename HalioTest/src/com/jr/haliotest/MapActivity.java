package com.jr.haliotest;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jr.haliotest.utils.IntentFilters;
import com.jr.haliotest.utils.Position;
import com.jr.haliotest.utils.places.CachedPlaces;
import com.jr.haliotest.utils.places.Place;
import android.support.v4.app.FragmentManager;

/**
 * @author RichardsJ
 * 
 */
public class MapActivity extends FragmentActivity implements IntentFilters {

	private static final String ME_TEXT = "Me";

	private GoogleMap googleMap;

	private Position mPosition;

	private CameraPosition mCameraPosition;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_view_layout);
		mPosition = getIntent().getParcelableExtra(INTENT_EXTRA_POSITION);
		mCameraPosition = new CameraPosition(new LatLng(
				mPosition.getLattitude(), mPosition.getLongetude()), 14, 0, 0);
		initialiseView();
	}


	private void initialiseView() {

		googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(
				R.id.map)).getMap();

		if (googleMap == null) {
			Log.d("", "Map wasnt loaded properly");
		} else {
			Log.d("", "Map loaded fine");
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		}

		googleMap.moveCamera(CameraUpdateFactory
				.newCameraPosition(mCameraPosition));

		googleMap.addMarker(new MarkerOptions()
				.position(
						new LatLng(mPosition.getLattitude(), mPosition
								.getLongetude()))
				.title(ME_TEXT)
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

		addRestaurantsMarker();

	}

	/**
	 * Plot map with restaurants found
	 */
	private void addRestaurantsMarker() {
		List<Place> cachedPlaces = CachedPlaces.openCach().getCachedPlaces();
		for (Place place : cachedPlaces) {

	
			googleMap.addMarker(new MarkerOptions().position(
					new LatLng(place.getGeometry().getLocation().getLat(),
							place.getGeometry().getLocation().getLng())).title(
					place.getName()).snippet(place.getFormattedAddress()));
		}
	}
}
