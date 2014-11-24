package ca.ualberta.cs.cmput301t03app.models;


/**
 * This class represents a location defined by it's 
 * latitude and longitude.
 *
 */
public class GeoLocation {
	
	private double latitude;
	private double longitude;
	private String cityName = "None";

	public GeoLocation() {}
	
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
}
