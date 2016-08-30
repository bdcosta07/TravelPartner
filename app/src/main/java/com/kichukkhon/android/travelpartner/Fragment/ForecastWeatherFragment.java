package com.kichukkhon.android.travelpartner.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kichukkhon.android.travelpartner.Adapter.ForecastWeatherAdapter;
import com.kichukkhon.android.travelpartner.Class.Forecast;
import com.kichukkhon.android.travelpartner.Class.Tour;
import com.kichukkhon.android.travelpartner.Database.TourDBManager;
import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Util.AppUtils;
import com.kichukkhon.android.travelpartner.Util.Preference;
import com.kichukkhon.android.travelpartner.VolleyAppController.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastWeatherFragment extends Fragment {
    private static final String ARG_PAGE = "page";
    TextView tvLocation;
    TextView tvNextDays;
    private int mPageNumber;
    ArrayList<Forecast> forecastWeatherList;
    ForecastWeatherAdapter forecastAdapter;
    ListView listView;
    String location;

    public ForecastWeatherFragment() {
    }

    public static ForecastWeatherFragment newInstance(int pageNumber) {
        ForecastWeatherFragment fragment = new ForecastWeatherFragment();
        Bundle args = new Bundle();

        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPageNumber = getArguments().getInt(ARG_PAGE);
        }

        //get location by current tour ID
        Preference preference = new Preference(getActivity());
        int currentTourId = preference.getCurrentlySelectedTourId();

        TourDBManager dbManager = new TourDBManager(getActivity());
        Tour tour = dbManager.getTourInfoById(currentTourId);

        location = "Dhaka";

        if (tour != null) {
            location = tour.getDestination();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_forcast_weather, container, false);

        listView = (ListView) rootView.findViewById(R.id.lvForecastList);
        tvLocation = (TextView) rootView.findViewById(R.id.tvLocation);
        tvNextDays = (TextView) rootView.findViewById(R.id.tvNextDays);

        forecastWeatherList = new ArrayList<>();
        forecastAdapter = new ForecastWeatherAdapter(getActivity(), forecastWeatherList);
        getForecastWeather();


        // Inflate the layout for this fragment
        return rootView;
    }

    public int getPageNumber() {
        return mPageNumber;
    }

    public void getForecastWeather() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, AppUtils.BuildYahooURL(location), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject query = response.getJSONObject("query");
                    JSONObject result = query.getJSONObject("results");
                    JSONObject channel = result.getJSONObject("channel");
                    JSONObject item = channel.getJSONObject("item");
                    JSONArray forecast = item.getJSONArray("forecast");

                    JSONObject location = channel.getJSONObject("location");
                    String city = location.getString("city");

                    tvLocation.setText(city);
                    tvNextDays.setText("Next 10 days");

                    for (int i = 0; i < forecast.length(); i++) {
                        JSONObject jsonObject = forecast.getJSONObject(i);

                        String date = jsonObject.getString("date");
                        int highTemp = Integer.parseInt(jsonObject.getString("high"));
                        int lowTemp = Integer.parseInt(jsonObject.getString("low"));
                        String description = jsonObject.getString("text");
                        int code = Integer.parseInt(jsonObject.getString("code"));

                        Forecast forecastClass = new Forecast();
                        forecastClass.setDate(date);
                        forecastClass.setHigh(highTemp);
                        forecastClass.setLow(lowTemp);
                        forecastClass.setDescription(description);
                        forecastClass.setCode(code);

                        forecastWeatherList.add(forecastClass);
                    }

                    listView.setAdapter(forecastAdapter);
                    forecastAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    Toast.makeText(getContext(), "Check your internet connection", Toast.LENGTH_SHORT);

                }
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

}
