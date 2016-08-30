package com.kichukkhon.android.travelpartner.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kichukkhon.android.travelpartner.Class.Forecast;
import com.kichukkhon.android.travelpartner.R;

import java.util.ArrayList;

/**
 * Created by Bridget on 28/2/2016.
 */
public class ForecastWeatherAdapter extends ArrayAdapter {

    static private class ViewHolder {
        ImageView imgForecastWeather;
        TextView tvForecastDay, tvForecastDesc, tvForecastHighTemp, tvForecastLowTemp;

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
            //viewHolder.imgForecastWeather=(ImageView) rowView.findViewById(R.id.forecastImg);

            rowView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) rowView.getTag();

        String highTemp = String.valueOf(forecastList.get(position).getHigh());
        String lowTemp = String.valueOf(forecastList.get(position).getLow());

        viewHolder.tvForecastDay.setText(forecastList.get(position).getDay());
        viewHolder.tvForecastDesc.setText(forecastList.get(position).getDescription());
        viewHolder.tvForecastHighTemp.setText(highTemp + "° ↑");
        viewHolder.tvForecastLowTemp.setText(lowTemp + "° ↓");

        return rowView;
    }
}
