package com.jr.haliotest.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Position object that stores latt and longitude values
 *
 * @author jonathan
 */
public class Position implements Parcelable{

	private double longetude;

	private double lattitude;

	private double lowerLeftLatitude;

	private double lowerLeftLongitude;

	private double upperRightLatitude;

	private double upperRightLongitude;

	/**
	 *
	 */
	private static final double LATTITUDE_KM_DEGREE = 110;

	private double LONGITUDE_KM_DEGREE = 113;





	public Position() {

	}

	public Position(double longetude, double lattitude) {
		this.longetude = longetude;
		this.lattitude = lattitude;
	}

	public double getLongetude() {
		return longetude;
	}

	public void setLongetude(double longetude) {
		this.longetude = longetude;
	}

	public double getLattitude() {
		return lattitude;
	}

	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}

	public double getLowerLeftLatitude(double distance) {

		lowerLeftLatitude = getLattitude() - (distance / LATTITUDE_KM_DEGREE);
		return lowerLeftLatitude;
	}

	public double getLowerLeftLongitude(double distance) {
		lowerLeftLongitude = getLongetude() - (distance / LONGITUDE_KM_DEGREE);
		return lowerLeftLongitude;
	}

	public double getUpperRightLatitude(double distance) {
		upperRightLatitude = getLattitude() + (distance / LATTITUDE_KM_DEGREE);
		return upperRightLatitude;
	}

	public double getUpperRightLongitude(double distance) {
		upperRightLongitude = getLongetude() + (distance / LONGITUDE_KM_DEGREE);
		return upperRightLongitude;
	}

	@Override
	public int describeContents() {
	
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDouble(lattitude);
		dest.writeDouble(longetude);
		
	}
	
	 public static final Parcelable.Creator<Position> CREATOR
     = new Parcelable.Creator<Position>() {

		@Override
		public Position createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Position(source);
		}

		@Override
		public Position[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Position[size];
		}
		 
	 };
	 
	 private Position(Parcel in){
		 setLattitude(in.readDouble());
		 setLongetude(in.readDouble());
	 }

}
