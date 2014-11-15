package ca.ualberta.cs.cmput301t03app.controllers;

import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import ca.ualberta.cs.cmput301t03app.models.GeoLocation;

/**
 * This class is used as a controller for GeoLocation to get 
 * the user's current location and city. Both the network and gps
 * can be used to find the location, only if it is enabled.
 * 
 * This code is taken and modified from
 * http://stackoverflow.com/questions/3145089/what-is-the-simplest-and-most-robust-way-to-get-the-users-current-location-in-a
 * Author: Fedor
 * http://stackoverflow.com/users/95313/fedor
 */

public class GeoLocationTracker {
	
	private GeoLocation geoLocation;
	private Timer timer;
	private LocationManager locationManager;
	private Context context;
	
	private boolean gpsEnabled = false;
	private boolean networkEnabled = false;
	
	public GeoLocationTracker(Context context, GeoLocation location) {
		this.geoLocation = location;
		this.context = context;
	}
	
	public boolean getLocation(Context context, GeoLocation location)
    {
        if (locationManager==null) {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }

        //exceptions will be thrown if provider is not permitted.
        try {
        	gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        	
        } catch (Exception e) {
        		
        }
        
        try {
        	networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex){ 
        	
        }

        //don't start listeners if no provider is enabled
        if (!gpsEnabled && !networkEnabled) {
            return false;
        }
        
        if (gpsEnabled) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
        }
        if (networkEnabled) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
        }
        
        timer = new Timer();
        timer.schedule(new GetLastLocation(), 20000);
        return true;
    }
	
    LocationListener locationListenerGps = new LocationListener() {
    	
        public void onLocationChanged(Location location) {
            timer.cancel();
            geoLocation.setLatitude(location.getLatitude());
            geoLocation.setLongitude(location.getLongitude());
            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerNetwork);
        }
        public void onProviderDisabled(String provider) {
        	
        }
        public void onProviderEnabled(String provider) {
        	
        }
        public void onStatusChanged(String provider, int status, Bundle extras) {
        	
        }
    };

    LocationListener locationListenerNetwork = new LocationListener() {
    	
        public void onLocationChanged(Location location) {
            timer.cancel();
            geoLocation.setLatitude(location.getLatitude());
            geoLocation.setLongitude(location.getLongitude());
            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerGps);
        }
        public void onProviderDisabled(String provider) {
        	
        }
        public void onProviderEnabled(String provider) {
        	
        }
        public void onStatusChanged(String provider, int status, Bundle extras) {
        	
        }
    };
	
    class GetLastLocation extends TimerTask {
        @Override
        public void run() {
        	locationManager.removeUpdates(locationListenerGps);
        	locationManager.removeUpdates(locationListenerNetwork);

             Location net_loc=null, gps_loc=null;
             
             if (gpsEnabled) {
                 gps_loc=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
             }
             
             if (networkEnabled) {
                 net_loc=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
             }

             //if there are both values use the latest one
             if (gps_loc!=null && net_loc!=null) {
            	 
                 if (gps_loc.getTime()>net_loc.getTime()) {
                 	geoLocation.setLatitude(gps_loc.getLatitude());
             		geoLocation.setLongitude(gps_loc.getLongitude());
                 }
                 else {
                  	geoLocation.setLatitude(net_loc.getLatitude());
              		geoLocation.setLongitude(net_loc.getLongitude());
                 }
                 return;
             }

             if (gps_loc!=null) {
              	geoLocation.setLatitude(gps_loc.getLatitude());
          		geoLocation.setLongitude(gps_loc.getLongitude());
                 return;
             }
             if (net_loc!=null) {
               	geoLocation.setLatitude(net_loc.getLatitude());
           		geoLocation.setLongitude(net_loc.getLongitude());
                 return;
             }
        }
    }
    
    /**
     * Creates a pop up dialog box to notify the user that GPS is not enabled
     * and gives the option for the user to be redirected to enable it.
     */
    protected void showGPSDisabledAlertBox() {
    	AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
    	alertBuilder.setMessage("GPS is disabled on this device. Do you want to enable it?")
    	.setCancelable(false)
    	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, final int id) {
                context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        })
        .setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, final int id) {
                 dialog.cancel();
            }
        });
    }
}
