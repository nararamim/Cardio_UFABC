package br.edu.ufabc.padm.cardioufabc.services;

import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.xml.sax.helpers.LocatorImpl;

/**
 * Created by guilhermeosaka on 07/12/2016.
 */

public class LocationService extends IntentService {
    public static volatile boolean running = true;

    private static final String LOGTAG = LocationService.class.getSimpleName();
    public static final String LOCATION_CHANGED = "LOCATION_CHANGED";
    private static final int TWO_MINUTES = 1000 * 60 * 2;

    public LocationManager manager;
    public CustomLocationListener listener;
    private Location currentLocation;


    public LocationService() {
        super("LocationService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = new CustomLocationListener();

        try {
            // atualiza a cada 3 segundos ou a cada mudança de localização
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, listener);
        } catch (SecurityException e) {
            Log.e(LOGTAG, "Permissão negada", e);
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
    }

    // Retirado de: https://developer.android.com/guide/topics/location/strategies.html
    /** Determines whether one Location reading is better than the current Location fix
     * @param location  The new Location that you want to evaluate
     * @param currentLocation  The current Location fix, to which you want to compare the new one
     */
    protected boolean isBetterLocation(Location location, Location currentLocation) {
        if (currentLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // customizado para não atualizar se a latitude e longitudade não diferir de 5 casas decimais
        // diminui consideravelmente o número de atualizações
        double latitude = Math.floor(location.getLatitude() * 100000) / 100000;
        double longitude = Math.floor(location.getLongitude() * 100000) / 100000;
        double currentLatitude = Math.floor(currentLocation.getLatitude() * 100000) / 100000;
        double currentLongitude = Math.floor(currentLocation.getLongitude() * 100000) / 100000;
        if (latitude == currentLatitude && longitude == currentLongitude)
            return false;


        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate) {
            return true;
        }
        return false;
    }

    public class CustomLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            if (isBetterLocation(location, currentLocation) && running) {
                currentLocation = location;
                Intent intent = new Intent(LOCATION_CHANGED);
                intent.putExtra("Latitude", location.getLatitude());
                intent.putExtra("Longitude", location.getLongitude());
                LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent);
            } else if (!running) {
                try {
                    manager.removeUpdates(this);
                    manager = null;
                    listener = null;
                    stopSelf();
                } catch (SecurityException e) {
                    Log.e(LOGTAG, "Permissão negada", e);
                }
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onProviderDisabled(String provider) {}
    }
}
