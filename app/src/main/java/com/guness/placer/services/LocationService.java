package com.guness.placer.services;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.util.Date;


public class LocationService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final String TAG = LocationService.class.getSimpleName();

    private final IBinder mBinder = new LocalBinder();

    private static final int DEFAULT_UPDATE = 10 * 1000 * 30;

    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String mLastUpdateTime;

    private LocationListener mLocationListener;

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    public LocationService() {
        Log.e(TAG, "LocationService()");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        if (intent != null) {
            Log.i("LocationService", "onStartCommand service @ " + SystemClock.elapsedRealtime());
        }

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        super.onCreate();

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        stopLocationUpdates();
        mGoogleApiClient.disconnect();
        super.onDestroy();
    }

    public void stopLocationUpdates() {
        Log.e(TAG, "stopLocationUpdates");
        mLocationListener = null;
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.e(TAG, "onConnected");
        Location location;
        try {
            location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } catch (SecurityException e) {
            Log.e(TAG, "Permission denied #1");
            stopSelf();
            return;
        }
        if (location != null) {
            onLocationChanged(location);
        }

        try {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(DEFAULT_UPDATE);
            locationRequest.setFastestInterval(5000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
        } catch (SecurityException e) {
            Log.e(TAG, "Permission denied #2");
            stopSelf();
        }

    }

    public void startLocationUpdates(@NonNull LocationListener locationListener) throws SecurityException {
        Log.e(TAG, "startLocationUpdates");
        mLocationListener = locationListener;
        if (mCurrentLocation != null) {
            mLocationListener.onLocationChanged(mCurrentLocation);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "onConnectionSuspended: " + i);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

        //mLatitudeText.setText(String.valueOf(mCurrentLocation.getLatitude()));
        //mLongitudeText.setText(String.valueOf(mCurrentLocation.getLongitude()));

        Log.d(TAG, "Location Latitude: " + location.getLatitude());
        Log.d(TAG, "Location Longitude: " + location.getLongitude());
        if (mLocationListener != null) {
            mLocationListener.onLocationChanged(mCurrentLocation);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed: " + connectionResult.getErrorMessage());
    }

    public class LocalBinder extends Binder {
        public LocationService getService() {
            // Return this instance of LocalService so clients can call public methods
            return LocationService.this;
        }
    }

}
