package com.kichukkhon.android.travelpartner.Activity;

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.kichukkhon.android.travelpartner.Adapter.PlaceListAdapter;
import com.kichukkhon.android.travelpartner.Class.PlaceBean;
import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Util.Constants;
import com.kichukkhon.android.travelpartner.Util.GetCurrentLocation;
import com.kichukkhon.android.travelpartner.Util.PlaceUtils;
import com.kichukkhon.android.travelpartner.VolleyAppController.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Places_list extends BaseActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    protected static final String TAG = "PlaceList";
    RecyclerView listOfPlaces;
    //GetCurrentLocation location;
    String kind;
    private List<PlaceBean> placeBeanList;

    public static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    private GoogleApiClient mGoogleApiClient;

    public LocationRequest mLocationRequest;

    double latitude, longitude;

    LatLng latLng = new LatLng(0, 0);

    PlaceListAdapter placesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_list);

        placeBeanList = new ArrayList<>();


        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarWithAppbar);
        setSupportActionBar(toolbar);

        kind = getIntent().getStringExtra(Constants.PLACE_TYPE_ID_KEY);
        toolbar.setTitle(kind.replace("_", " "));

        //google api client for location
        if (!hasGooglePlayServices()) {
            Toast.makeText(this, "Google play services not found.", Toast.LENGTH_LONG).show();
            return;
        }

        checkPermission();



       /* if (isPermissionGranted(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            CreateList();
        }*/

        /*listOfPlaces.addOnItemTouchListener(new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent detailActivity = new Intent(context, PlaceDetail.class);
                detailActivity.putExtra("placeId", list.get(position).getPlaceref());
                detailActivity.putExtra("kind", kind);
                startActivity(detailActivity);
            }
        }));*/

    }

    public void getNearbyPlaces() {
        String latlng = latitude + "," + longitude;
        String url = PlaceUtils.BuildNearbyPlaceSearchUrl(latlng, kind);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray resultArray = response.getJSONArray("results");

                    double latitude, longitude;
                    String id, name, vicinity, type;
                    boolean isOpen = false;
                    float rating;

                    for (int i = 0; i < resultArray.length(); i++) {

                        JSONObject jsonObject = resultArray.getJSONObject(i);
                        PlaceBean pb = new PlaceBean();

                        if (jsonObject.has("geometry")) {
                            JSONObject geometry = jsonObject.getJSONObject("geometry");
                            if (geometry.has("location")) {
                                JSONObject location = geometry.getJSONObject("location");
                                latitude = location.getDouble("lat");
                                longitude = location.getDouble("lng");
                            } else {
                                latitude = 0.0;
                                longitude = 0.0;
                            }
                        } else {
                            latitude = 0.0;
                            longitude = 0.0;
                        }

                        if (jsonObject.has("name")) {
                            name = jsonObject.getString("name");
                        } else {
                            name = "Not available";
                        }
                        if (jsonObject.has("rating")) {
                            rating = (float) jsonObject.getDouble("rating");
                        } else {
                            rating = 0.0f;
                        }
                        if (jsonObject.has("opening_hours")) {
                            JSONObject opening_hours = jsonObject.getJSONObject("opening_hours");
                            if (opening_hours.has("open_now")) {
                                isOpen = opening_hours.getBoolean("open_now");
                            } else {
                                isOpen = false;
                            }
                        } else {
                            pb.setIsOpen(false);
                        }
                        if (jsonObject.has("place_id")) {
                            id = jsonObject.getString("place_id");
                        } else {
                            id = "Not Available";
                        }
                        if (jsonObject.has("vicinity")) {
                            vicinity = jsonObject.getString("vicinity");
                        } else {
                            vicinity = "Not Available";
                        }
                        if (jsonObject.has("types")) {
                            JSONArray types = jsonObject.getJSONArray("types");
                            StringBuilder sb = new StringBuilder();
                            for (int j = 0; j < types.length(); j++) {
                                if (j != (types.length() - 1)) {
                                    sb.append(types.getString(j) + " | ");
                                } else {
                                    sb.append(types.getString(j));
                                }
                            }
                            type = sb.toString();
                        } else {
                            type = "Not Available";
                        }

                        pb.setLatitude(latitude);
                        pb.setLongitude(longitude);
                        pb.setPlaceRef(id);
                        pb.setIsOpen(isOpen);
                        pb.setName(name);
                        pb.setRating(rating);
                        pb.setVicinity(vicinity);
                        pb.setType(type);
                        pb.setKind(kind);

                        placeBeanList.add(pb);
                    }

                    CreateView();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    Toast.makeText(Places_list.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

    public void disconnectGoogleApiClient() {
        if (mGoogleApiClient != null)
            mGoogleApiClient.disconnect();
    }

    private void checkPermission() {
        if (isPermissionGranted(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            setUpGoogleApiClient();
        } else {
            ActivityCompat.requestPermissions((AppCompatActivity) this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    synchronized void setUpGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
       /* mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(Constants.INTERVAL_FOR_NEARBY_LOCATION);
        mLocationRequest.setFastestInterval(Constants.FASTEST_INTERVAL_FOR_NEARBY_LOCATION);*/

        if (!isPermissionGranted(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            return;
        }

        if (!checkGPSisOpen()) {
            Toast.makeText(this, "Enable location services for accurate data.", Toast.LENGTH_SHORT)
                    .show();
            Intent viewIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(viewIntent);
        } else {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            getLocation(lastLocation);
        }
    }

    private void getLocation(Location location) {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            latLng = new LatLng(latitude, longitude);

            getNearbyPlaces();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        setUpGoogleApiClient();
    }

    private boolean hasGooglePlayServices() {
        return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this) ==
                ConnectionResult.SUCCESS;
    }

    private boolean checkGPSisOpen() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public static boolean isPermissionGranted(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) ==
                PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setUpGoogleApiClient();
                } else {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }

    public void CreateView() {
        placesAdapter = new PlaceListAdapter(this, placeBeanList, latLng);
        listOfPlaces = (RecyclerView) findViewById(R.id.rvTourList);
        listOfPlaces.setHasFixedSize(true);
        listOfPlaces.setLayoutManager(new LinearLayoutManager(this));
        listOfPlaces.setAdapter(placesAdapter);
    }
}
