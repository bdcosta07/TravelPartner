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
import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Settings.SettingsUtils;
import com.kichukkhon.android.travelpartner.Util.AppUtils;
import com.kichukkhon.android.travelpartner.Util.Constants;
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
    // TODO: Rename and change types of parameters
    TextView tvLocation;
    TextView tvNextDays;
    private int mPageNumber;
    ArrayList<Forecast> forecastWeatherList;
    ForecastWeatherAdapter forecastAdapter;
    ListView listView;

    String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22Dhaka%22)&format=json";


    public ForecastWeatherFragment() {
    }

    // TODO: Rename and change types and number of parameters
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
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_forcast_weather, container, false);

        listView = (ListView) rootView.findViewById(R.id.lvForecastList);
        tvLocation = (TextView) rootView.findViewById(R.id.tvLocation);
        tvNextDays=(TextView)rootView.findViewById(R.id.tvNextDays);

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
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject query = response.getJSONObject("query");
                    JSONObject result = query.getJSONObject("results");
                    JSONObject channel = result.getJSONObject("channel");
                    JSONObject item = channel.getJSONObject("item");
                    JSONArray forecast = item.getJSONArray("forecast");

                    JSONObject location=channel.getJSONObject("location");
                    String city=location.getString("city");

                    tvLocation.setText(city);
                    tvNextDays.setText("Next 10 days");

                    for (int i = 0; i < forecast.length(); i++) {
                        JSONObject jsonObject = forecast.getJSONObject(i);

                        String day = jsonObject.getString("day");
                        int highTemp = Integer.parseInt(jsonObject.getString("high"));
                        int lowTemp = Integer.parseInt(jsonObject.getString("low"));
                        String description = jsonObject.getString("text");

                        Forecast forecastClass = new Forecast();
                        forecastClass.setDay(day);
                        forecastClass.setHigh(highTemp);
                        forecastClass.setLow(lowTemp);
                        forecastClass.setDescription(description);

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
