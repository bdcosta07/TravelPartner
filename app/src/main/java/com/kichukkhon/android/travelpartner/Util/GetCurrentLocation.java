package com.kichukkhon.android.travelpartner.Util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Ratul on 8/27/2016.
 */
public class GetCurrentLocation
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    private GoogleApiClient mGoogleApiClient;

    public LocationRequest mLocationRequest;

    public Context mContext;

    public double latitude;
    public double longitude;

    public GetCurrentLocation(Context context) {
        mContext = context;

        if (!hasGooglePlayServices()) {
            Toast.makeText(mContext, "Google play services not found.", Toast.LENGTH_LONG).show();
            return;
        }

        checkPermission();
    }

    public void disconnectGoogleApiClient() {
        if (mGoogleApiClient != null)
            mGoogleApiClient.disconnect();
    }

    private void checkPermission() {
        if (isPermissionGranted(mContext, Manifest.permission.ACCESS_FINE_LOCATION)) {
            setUpGoogleApiClient();
        } else {
            ActivityCompat.requestPermissions((AppCompatActivity) mContext,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    synchronized void setUpGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setUpGoogleApiClient();
                } else {
                    Toast.makeText(mContext, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(500);

        if (!isPermissionGranted(mContext, Manifest.permission.ACCESS_FINE_LOCATION)) {
            return;
        }

        if (!checkGPSisOpen()) {
            Toast.makeText(mContext, "Enable location services for accurate data.", Toast.LENGTH_SHORT)
                    .show();
            Intent viewIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            mContext.startActivity(viewIntent);
        } else {
            LocationServices.FusedLocationApi
                    .requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

            getLocation(LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient));
        }
    }

    private void getLocation(Location location) {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        getLocation(location);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        setUpGoogleApiClient();
    }

    private boolean hasGooglePlayServices() {
        return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(mContext) ==
                ConnectionResult.SUCCESS;
    }

    private boolean checkGPSisOpen() {
        LocationManager manager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public static boolean isPermissionGranted(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) ==
                PackageManager.PERMISSION_GRANTED;
    }
}
