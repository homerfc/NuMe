package com.example.cmput301f17t27.nume;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by Colman on 12/11/2017.
 */

public class MapController {

    public static Location getLocation(Context context) {
        /*
            This function gets the last known location of the device.

            Args - None
            Returns - A Location object (Null if location services are
                not enabled)
        */

        try {
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            boolean GPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


            if (!networkEnabled && !GPSEnabled) {
                // cannot get location
            }

            else {
                if (GPSEnabled && lm != null)  {
                    return lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
                else if (networkEnabled && lm!=null) {
                    return lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            }
        }

        catch (SecurityException ex)  {
            //Security exception
        }

        return null;
    }
}
