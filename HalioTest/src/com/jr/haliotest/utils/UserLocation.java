package com.jr.haliotest.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * util class that gets users current location
 * 
 * @author jonathan
 * 
 */
public class UserLocation implements LocationListener {

	public static final int UPDATE_LATT = 0;

	private static final int TIME_INTERVAL = 1000;

	private static final int DISTANCE = 10;

	private LocationManager locationManagaer;

	private LocationProvider locationProvide;

	private Handler mHandler;

	public UserLocation(Context context, Handler handler) {
		locationManagaer = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		locationProvide = locationManagaer
				.getProvider(LocationManager.NETWORK_PROVIDER);

		mHandler = handler;

	}

	public void startSearchLocation() {
		Log.d("", "sart location search");
		locationManagaer.requestLocationUpdates(locationProvide.getName(),
				TIME_INTERVAL, DISTANCE, this);
	}

	public void stopSearchLocation() {
		locationManagaer.removeUpdates(this);
	}

	public Position getLastKnownLocation() throws NullPointerException {
		return new Position(locationManagaer.getLastKnownLocation(
				locationProvide.getName()).getLongitude(), locationManagaer
				.getLastKnownLocation(locationProvide.getName()).getLatitude());
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.d("", "location update on " + location.getLongitude());
		
		Position position = new Position(location.getLongitude(), location.getLatitude());
		
		Message message = Message.obtain(mHandler, UPDATE_LATT,
				position);
		
		mHandler.sendMessage(message);
		

	}

	@Override
	public void onProviderDisabled(String provider) {
		Log.d("", "onProviderDisabled");

	}

	@Override
	public void onProviderEnabled(String provider) {
		Log.d("", "onProviderEnabled");

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Log.d("", "onStatusChanged");

	}

}
