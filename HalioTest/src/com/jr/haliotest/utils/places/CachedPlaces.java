package com.jr.haliotest.utils.places;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RichardsJ
 * 
 */
public class CachedPlaces {

	private static CachedPlaces cachedPlaces;

	private static List<Place> placeList = new ArrayList<Place>();

	/**
	 * Open cached places object
	 * 
	 * @return
	 */
	public static CachedPlaces openCach() {
		if (cachedPlaces == null) {
			cachedPlaces = new CachedPlaces();
		}

		return cachedPlaces;
	}

	/**
	 * get cached places
	 * 
	 * @return
	 */
	public List<Place> getCachedPlaces() {
		return placeList;

	}

	/**
	 * Save cached places
	 * 
	 * @param places
	 */
	public void saveCachedPlaces(List<Place> places) {
		placeList = places;
	}

	private CachedPlaces() {

	}

}
