package com.kichukkhon.android.travelpartner.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.kichukkhon.android.travelpartner.Class.Tour;
import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Util.AppUtils;

import java.util.ArrayList;

/**
 * Created by Bridget on 8/24/2016.
 */
public class TourAdapter extends RecyclerView.Adapter<TourAdapter.ViewHolder>{

    ArrayList<Tour> tourList;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTourName;
        TextView tvStarDate;
        TextView tvEndDate;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            tvTourName=(TextView) itemView.findViewById(R.id.tvTourName);
            tvStarDate=(TextView) itemView.findViewById(R.id.tvStartDate);
            tvEndDate=(TextView)itemView.findViewById(R.id.tvEndDate);
        }
    }

    public TourAdapter(Context context,ArrayList<Tour> tourList) {
        this.tourList = tourList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View rowView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tour_row_layout,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        String tourName=tourList.get(position).getTourName();
        viewHolder.tvTourName.setText(tourName);

        long startDate=tourList.get(position).getStartDateTime();
        viewHolder.tvStarDate.setText(AppUtils.getFriendlyDayString(context, startDate));

        long endDate=tourList.get(position).getEndDateTime();
        viewHolder.tvEndDate.setText(AppUtils.getFriendlyDayString(context,endDate));
    }

    @Override
    public int getItemCount() {
        return tourList.size();
    }


}
