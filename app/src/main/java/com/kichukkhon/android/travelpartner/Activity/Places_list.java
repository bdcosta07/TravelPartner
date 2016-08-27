package com.kichukkhon.android.travelpartner.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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

public class Places_list extends BaseActivity {

    RecyclerView listOfPlaces;
    GetCurrentLocation location;
    String kind;
    private List<PlaceBean> placeBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_list);

        location = new GetCurrentLocation(this);

        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarWithAppbar);
        setSupportActionBar(toolbar);

        kind = getIntent().getStringExtra(Constants.PLACE_TYPE_ID_KEY);
        toolbar.setTitle(kind.replace("_", " "));

        placeBeanList = new ArrayList<>();

        getNearbyPlaces();

        PlaceListAdapter placesAdapter = new PlaceListAdapter(this, placeBeanList, location);
        listOfPlaces = (RecyclerView) findViewById(R.id.rvTourList);
        listOfPlaces.setHasFixedSize(true);
        listOfPlaces.setLayoutManager(new LinearLayoutManager(this));
        listOfPlaces.setAdapter(placesAdapter);


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
        String latlng = location.latitude + "," + location.longitude;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        location.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
