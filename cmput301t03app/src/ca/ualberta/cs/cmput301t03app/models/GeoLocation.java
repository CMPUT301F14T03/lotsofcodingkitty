package ca.ualberta.cs.cmput301t03app.models;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * This class represents a location defined by it's 
 * latitude and longitude. The city name can be converted from 
 * location coordinates, or can be set manually by the user.
 *
 */
public class GeoLocation {
	
	private double latitude;
	private double longitude;
	private String cityName = "None";

	public GeoLocation() {
	}

	// method for testing geolocation

	public GeoLocation(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public String getCityName() {
		return cityName;
	}

	/**
	 * Takes a geolocation object and returns a city name.
	 * This method requires the use of Geocoder.
	 * 
	 * @param Geolocation location
	 * @param context
	 * @return  a string with the city name
	 */
	public String getCityFromLoc(Context context) {
		String cityName = null;
		Geocoder gcd = new Geocoder(context, Locale.getDefault());
		List<Address> addresses;
		try {
			addresses = gcd.getFromLocation(getLatitude(), getLongitude(), 1);
			if (addresses.size() > 0)
				cityName = addresses.get(0).getLocality();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cityName;
	}
}
