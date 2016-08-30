package com.kichukkhon.android.travelpartner.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kichukkhon.android.travelpartner.Class.Forecast;
import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Util.AppUtils;

import java.util.ArrayList;

/**
 * Created by Bridget on 28/2/2016.
 */
public class ForecastWeatherAdapter extends ArrayAdapter {

    static private class ViewHolder {
        TextView tvForecastDay, tvForecastDesc, tvForecastHighTemp, tvForecastLowTemp, tvForecastIcon;

    }

    ArrayList<Forecast> forecastList;
    Context context;


    public ForecastWeatherAdapter(Context context, ArrayList<Forecast> forecastList) {
        super(context, R.layout.forecast_row, forecastList);
        this.forecastList = forecastList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            rowView = inflater.inflate(R.layout.forecast_row, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.tvForecastDay = (TextView) rowView.findViewById(R.id.tvDay);
            viewHolder.tvForecastDesc = (TextView) rowView.findViewById(R.id.tvDescription);
            viewHolder.tvForecastHighTemp = (TextView) rowView.findViewById(R.id.tvHigh);
            viewHolder.tvForecastLowTemp = (TextView) rowView.findViewById(R.id.tvLow);
            viewHolder.tvForecastIcon = (TextView) rowView.findViewById(R.id.tvForecastIcon);

            rowView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) rowView.getTag();

        String highTemp = String.valueOf(forecastList.get(position).getHigh());
        String lowTemp = String.valueOf(forecastList.get(position).getLow());
        int conditionCode = forecastList.get(position).getCode();

        viewHolder.tvForecastDay.setText(forecastList.get(position).getDate());
        viewHolder.tvForecastDesc.setText(forecastList.get(position).getDescription());
        viewHolder.tvForecastHighTemp.setText(highTemp + "° ↑");
        viewHolder.tvForecastLowTemp.setText(lowTemp + "° ↓");

        Typeface weatherFont = Typeface.createFromAsset(context.getAssets(), "weather.ttf");
        viewHolder.tvForecastIcon.setTypeface(weatherFont);
        viewHolder.tvForecastIcon.setText(AppUtils.getIconForYahooWeatherCondition(context, conditionCode));

        return rowView;
    }
}
