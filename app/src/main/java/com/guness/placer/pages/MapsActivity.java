package com.guness.placer.pages;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.ColorInt;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.guness.placer.R;
import com.guness.placer.models.DirectionResponse;
import com.guness.placer.models.Place;
import com.guness.placer.models.PlaceResponse;
import com.guness.placer.models.Route;
import com.guness.placer.services.LocationService;
import com.guness.placer.tasks.FindDirectionTask;
import com.guness.placer.tasks.FindPlacesTask;
import com.guness.placer.tasks.OnDirectionResponseListener;
import com.guness.placer.tasks.OnPlaceResponseListener;
import com.guness.placer.utils.Utils;

import java.util.ArrayList;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationButtonClickListener, View.OnClickListener {

    private GoogleMap mMap;
    private LocationService mService;
    private Location mLocation;
    boolean mBound = false;
    boolean mFirstMoved = false;
    private ArrayList<Marker> mMarkers;
    private ArrayList<Polyline> mRoutes;
    private ArrayList<String> mPlaceIds;
    private View mButton;
    private View mProgressBar;

    private Bitmap[] mPlaceMarkers;

    @ColorInt
    private int[] mRouteColors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mButton = findViewById(R.id.button);
        mProgressBar = findViewById(R.id.progressBar);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mMarkers = new ArrayList<>();
        mPlaceIds = new ArrayList<>();
        mRoutes = new ArrayList<>();

        mPlaceMarkers = Utils.loadPlaceMarkers(this);
        mRouteColors = Utils.loadRouteColors(this);
        mButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, LocationService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMyLocationButtonClickListener(this);
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            LocationService.LocalBinder binder = (LocationService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            mService.startLocationUpdates(MapsActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @Override
    public void onLocationChanged(final Location location) {
        mLocation = location;
        if (mMap != null) {
            LatLng myLocation = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
            if (!mFirstMoved) {
                mFirstMoved = true;
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        int index = mMarkers.indexOf(marker);
        if (index >= 0) {
            for (Polyline polyline : mRoutes) {
                polyline.remove();
            }
            mRoutes.clear();

            new FindDirectionTask(this, mLocation.getLatitude(), mLocation.getLongitude(), mPlaceIds.get(index)).execute(new OnDirectionResponseListener() {
                @Override
                public void onSuccess(DirectionResponse response) {
                    int count = 0;
                    for (Route route : response.routes) {
                        Polyline line = mMap.addPolyline(new PolylineOptions()
                                .addAll(PolyUtil.decode(route.overview_polyline.points))
                                .width(5)
                                .color(mRouteColors[count]));

                        count++;
                        count %= mRouteColors.length;

                        mRoutes.add(line);
                    }
                }
            });
        }
        return false;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        if (mLocation != null) {
            LatLng myLocation = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        if (mLocation != null) {
            mProgressBar.setVisibility(View.VISIBLE);
            new FindPlacesTask(this, mLocation.getLatitude(), mLocation.getLongitude()).execute(new OnPlaceResponseListener() {
                @Override
                public void onSuccess(PlaceResponse response) {
                    for (Marker marker : mMarkers) {
                        marker.remove();
                    }
                    mMarkers.clear();
                    mPlaceIds.clear();

                    mProgressBar.setVisibility(View.GONE);

                    int count = 0;
                    for (Place place : response.results) {
                        try {
                            Marker marker = mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(place.geometry.location.lat, place.geometry.location.lng))
                                    .title(place.name)
                                    .icon(BitmapDescriptorFactory.fromBitmap(mPlaceMarkers[count]))
                            );
                            mMarkers.add(marker);
                            mPlaceIds.add(place.place_id);
                            count++;
                            if (count == response.results.length) {
                                count--;
                            }
                        } catch (Exception e) {
                            //Null checks are skipped
                        }
                    }
                }
            });
        }
    }
}
