import java.io.IOException;
import java.util.Locale;

import android.content.Context;
import android.location.Geocoder;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import ca.ualberta.cs.cmput301t03app.controllers.GeoLocationTracker;
import ca.ualberta.cs.cmput301t03app.models.GeoLocation;
import ca.ualberta.cs.cmput301t03app.views.MainActivity;


// These Junit tests are used to test the Geolocation functionality of the model GeoLocation
// and controller GeoLocationTracker
public class GeolocationTest extends ActivityInstrumentationTestCase2<MainActivity>
{
	public GeolocationTest(){
		super (MainActivity.class);
	}
	
	//This part focuses on testing the model Geolocation.
	
	// Create a Geolocation with specific coordinates and
	// see if we get the correct city.
	public void testGetCityFromLocation() throws IOException{ 
		Context context= getInstrumentation()
				.getTargetContext();
		GeoLocation loc=new GeoLocation(53.53,113.5);
		Geocoder gcd = new Geocoder(context, Locale.getDefault());
		assertTrue("No service",gcd.isPresent());
		Log.d("Geotest",(gcd.getFromLocation(53.33, 113.5, 1)).get(0).getLocality());
		String cityName=loc.getCityFromLoc(context);
		assertNotNull("No city",cityName);
		assertTrue("Wrong City",cityName.equals("Edmonton"));
	}
	
	public void testGeoLocationTracker(){
		Context context= getInstrumentation()
				.getTargetContext();
		GeoLocation loc=new GeoLocation();
		GeoLocationTracker glt = new GeoLocationTracker(context,loc);
		glt.getLocation();
		Log.d("Geotest",String.valueOf(loc.getLatitude()));
		Log.d("Geotest",String.valueOf(loc.getLongitude()));
		assertNotNull("It's null!", glt);
		
	}
}
