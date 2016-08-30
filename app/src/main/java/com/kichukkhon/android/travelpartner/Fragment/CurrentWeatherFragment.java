package com.kichukkhon.android.travelpartner.Fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kichukkhon.android.travelpartner.Class.Tour;
import com.kichukkhon.android.travelpartner.Database.TourDBManager;
import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Settings.SettingsUtils;
import com.kichukkhon.android.travelpartner.Util.AppUtils;
import com.kichukkhon.android.travelpartner.Util.Constants;
import com.kichukkhon.android.travelpartner.Util.Preference;
import com.kichukkhon.android.travelpartner.VolleyAppController.AppController;

import org.json.JSONException;
import org.json.JSONObject;

interface Updatable {
    public void getCurrentWeather();
}

public class CurrentWeatherFragment extends Fragment implements Updatable {
    TextView tvTemperature, tvLocation, tvDescription, tvCurrentDate;
    TextView tvWind, tvHumidity, tvSunrise, tvSunset, tvCelFar;
    TextView icoWeather;
    String location;

    public static final String ARG_PAGE = "page";

    private int mPageNumber;

    public static CurrentWeatherFragment create(int pageNumber) {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);

        return fragment;
    }

    public CurrentWeatherFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);

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

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.current_weather_fragment, container, false);

        tvTemperature = (TextView) rootView.findViewById(R.id.tvTemperature);
        tvLocation = (TextView) rootView.findViewById(R.id.tvLocation);
        tvDescription = (TextView) rootView.findViewById(R.id.tvDescription);
        tvCurrentDate = (TextView) rootView.findViewById(R.id.tvDate);
        tvSunrise = (TextView) rootView.findViewById(R.id.tvSunrise);
        tvSunset = (TextView) rootView.findViewById(R.id.tvSunset);
        tvWind = (TextView) rootView.findViewById(R.id.tvWind);
        tvHumidity = (TextView) rootView.findViewById(R.id.tvHumidity);
        tvCelFar = (TextView) rootView.findViewById(R.id.tvF_C);
        icoWeather = (TextView) rootView.findViewById(R.id.tvWeatherCondition);

        getCurrentWeather();
        //getCurrentHighLowTemp();


        return rootView;
    }


    public int getPageNumber() {
        return mPageNumber;
    }


    //get current weather data (JSON)
    public void getCurrentWeather() {
        String url = AppUtils.BuildYahooURL(location);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject query = response.getJSONObject("query");
                    JSONObject results = query.getJSONObject("results");
                    JSONObject channel = results.getJSONObject("channel");
                    //JSONObject unit = channel.getJSONObject("units");
                    String celFar = SettingsUtils.PreferredTemperatureUnit(getActivity());

                    JSONObject atmosphere = channel.getJSONObject("atmosphere");
                    String humidity = atmosphere.getString("humidity");

                    JSONObject astronomy = channel.getJSONObject("astronomy");
                    String sunrise = astronomy.getString("sunrise");
                    String sunset = astronomy.getString("sunset");
                    JSONObject item = channel.getJSONObject("item");
                    JSONObject condition = item.getJSONObject("condition");
                    String temperature = AppUtils.formatTemperature(getActivity(), Double.parseDouble(condition.getString("temp")));
                    int conditionCode = Integer.parseInt(condition.getString("code"));
                    String text = condition.getString("text");
                    String date = AppUtils.parseYahooWeatherDate(getActivity(), condition.getString("date"));

                    JSONObject location = channel.getJSONObject("location");
                    String city = location.getString("city");

                    JSONObject wind = channel.getJSONObject("wind");
                    String speed = wind.getString("speed");

                    tvCelFar.setText(celFar);
                    tvTemperature.setText(temperature);
                    tvDescription.setText(text);
                    tvCurrentDate.setText(date);
                    tvLocation.setText(city);
                    tvWind.setText("Wind  " + speed + "mph");
                    tvHumidity.setText("Humidity  " + humidity + "%");
                    tvSunrise.setText("Sunrise   " + sunrise);
                    tvSunset.setText("Sunset    " + sunset);

                    Typeface weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "weather.ttf");
                    icoWeather.setTypeface(weatherFont);
                    icoWeather.setText(AppUtils.getIconForYahooWeatherCondition(getActivity(), conditionCode));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_SHORT);

                }
            }
        }
        );
        AppController.getInstance().addToRequestQueue(request);
    }


    /*public void getCurrentHighLowTemp() {
        String openUrl = AppUtils.BuildOpenWeatherURL(location, Constants.OPENWEATHER_CALL_TYPE_CURRENT);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, openUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject main = response.getJSONObject("main");
                    String highTemp = AppUtils.formatTemperature(getActivity(), main.getDouble("temp_max"));
                    String lowTemp = AppUtils.formatTemperature(getActivity(), main.getDouble("temp_min"));

                    tvHighTemp.setText(highTemp);
                    tvLowTemp.setText(lowTemp);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_SHORT);

                }
            }
        }
        );
        AppController.getInstance().addToRequestQueue(request);
    }*/

}
