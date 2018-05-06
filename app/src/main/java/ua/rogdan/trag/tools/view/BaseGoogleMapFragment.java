package ua.rogdan.trag.tools.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.Task;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.GeocodedWaypoint;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ua.rogdan.trag.R;
import ua.rogdan.trag.core.BaseFragment;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;

public abstract class BaseGoogleMapFragment extends BaseFragment {
    protected GoogleMap googleMap;
    protected MapFragment mapFragment;
    protected GoogleApiClient googleApiClient;
    protected LocationManager locationManager;
    protected boolean needSearchMyPosition;
    protected LatLng myPosition;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        providePresenter();
        initMap();
    }

    @IdRes
    protected abstract int getMapFragmentID();

    protected abstract void onMapReady();

    @CallSuper
    protected void initMap() {
        needSearchMyPosition = true;

        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(getMapFragmentID());
        mapFragment.getMapAsync(googleMap -> {
            BaseGoogleMapFragment.this.googleMap = googleMap;
            onMapReady();
        });

        startGoogleServices();
    }

    private void startGoogleServices() {
        stopManagingGoogleApiClient();
        googleApiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity(), connectionResult -> {/*ignore*/})
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        onGoogleApiConnected();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }

    private void onGoogleApiConnected() {
        initLocationListener();

        if (isMapReady()) {
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        }

        if (needSearchMyPosition) {
            if (!isLocationOn() || !isPermissionGranted()) {
                animateCamera(new LatLng(DEFAULT_LATITUDE, DEFAULT_LONGITUDE), DEFAULT_SCALE);
            }

            tryFindLocation();
        }
    }

    protected void tryFindLocation() {
        needSearchMyPosition = true;
        if (isLocationOn() && isPermissionGranted()) {
            if (isMapReady() && !googleMap.isMyLocationEnabled()) {
                initLocationListener();
            }

            zoomToMyPosition();
        } else {
            requestPermission();
        }
    }

    private void requestPermission() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        Task<LocationSettingsResponse> task = new SettingsClient(getContext()).checkLocationSettings(locationSettingsRequest);
        task.addOnSuccessListener(getActivity(), locationSettingsResponse -> {
            if (isPermissionGranted()) {
                zoomToMyPosition();
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_SETTINGS_REQUEST);
            }
        });

        task.addOnFailureListener(getActivity(), e -> {
            if (e instanceof ResolvableApiException) {
                try {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(getActivity(), LOCATION_SETTINGS_REQUEST);
                } catch (IntentSender.SendIntentException ignore) {
                }
            }
        });
    }

    private void zoomToMyPosition() {
        if (needSearchMyPosition) {
            if (myPosition != null) {
                needSearchMyPosition = false;
                animateCamera(myPosition, MARKER_CAMERA_HEIGHT);
            } else {
                needSearchMyPosition = true;
                zoomToLastKnownLocation();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void zoomToLastKnownLocation() {
        if (isPermissionGranted()) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

            if (location != null) {

                myPosition = new LatLng(location.getLatitude(), location.getLongitude());
                needSearchMyPosition = false;
                animateCamera(myPosition, CITY_CAMERA_HEIGHT);
            }
        }
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            myPosition = new LatLng(location.getLatitude(), location.getLongitude());

            if (needSearchMyPosition) {
                needSearchMyPosition = false;
                animateCamera(myPosition, MARKER_CAMERA_HEIGHT);
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    @SuppressLint("MissingPermission")
    private void initLocationListener() {
        if (isPermissionGranted()) {
            if (isMapReady()) {
                googleMap.setMyLocationEnabled(true);
            }

            LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    0, 10000, locationListener);

            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 0, 10000,
                    locationListener);
        }
    }

    public void stopManagingGoogleApiClient() {
        if (googleApiClient != null) {
            googleApiClient.stopAutoManage(getActivity());
            googleApiClient.disconnect();
        }
    }

    private void animateCamera(@NonNull LatLng location, int cameraHeight) {
        animateCamera(location, cameraHeight, null);
    }

    private void animateCamera(@NonNull LatLng location, int cameraHeight, @Nullable GoogleMap.CancelableCallback callback) {
        if (isMapReady()) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(location, cameraHeight);
            googleMap.animateCamera(cameraUpdate, callback);
        }
    }

    public boolean isLocationOn() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public boolean isPermissionGranted() {
        return ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean isMapReady() {
        return googleMap != null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_SETTINGS_REQUEST: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initLocationListener();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case LOCATION_SETTINGS_REQUEST:
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        googleMap.setMyLocationEnabled(true);
                    }
                    tryFindLocation();

                    break;
            }
        }
    }

    @Override
    public void onDestroyView() {
        FragmentManager fragmentManager = getActivity().getFragmentManager();
        if (mapFragment != null) {
            fragmentManager.beginTransaction().remove(mapFragment).commit();
        }

        super.onDestroyView();
    }

    protected GeoApiContext getGeoApiContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext.setQueryRateLimit(3)
                .setApiKey(getString(R.string.google_maps_key))
                .setConnectTimeout(1, TimeUnit.SECONDS)
                .setReadTimeout(1, TimeUnit.SECONDS)
                .setWriteTimeout(1, TimeUnit.SECONDS);
    }

    protected void addMarkersToMap(DirectionsResult results) {
        //googleMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[0].legs[0].startLocation.lat, results.routes[0].legs[0].startLocation.lng)).title(results.routes[0].legs[0].startAddress));
        //googleMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[0].legs[0].endLocation.lat, results.routes[0].legs[0].endLocation.lng)).title(results.routes[0].legs[0].startAddress).snippet(getEndLocationTitle(results)));
    }

    protected String getEndLocationTitle(DirectionsResult results) {
        return "Time :" + results.routes[0].legs[0].duration.humanReadable + " Distance :" + results.routes[0].legs[0].distance.humanReadable;
    }

    protected void addPolyline(DirectionsResult results) {
        List<LatLng> decodedPath = PolyUtil.decode(results.routes[0].overviewPolyline.getEncodedPath());
        googleMap.addPolyline(new PolylineOptions().addAll(decodedPath));
    }

    @Override
    public void onDestroy() {
        if (googleApiClient != null) {
            googleApiClient.stopAutoManage(getActivity());
            googleApiClient.disconnect();
        }
        super.onDestroy();
    }

    private static final int LOCATION_SETTINGS_REQUEST = 9087;
    private static final int MARKER_CAMERA_HEIGHT = 13;
    private static final int DEFAULT_SCALE = 5;
    private static final int CITY_CAMERA_HEIGHT = 11;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    //geographical center of Ukraine
    protected static final double DEFAULT_LATITUDE = 48.2258;
    protected static final double DEFAULT_LONGITUDE = 31.1056;
}
