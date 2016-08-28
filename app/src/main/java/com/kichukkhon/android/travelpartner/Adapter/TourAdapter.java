package com.kichukkhon.android.travelpartner.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kichukkhon.android.travelpartner.Activity.TourDetailsActivity;
import com.kichukkhon.android.travelpartner.Class.Tour;
import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Util.AppUtils;
import com.kichukkhon.android.travelpartner.Util.Constants;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Bridget on 8/24/2016.
 */
public class TourAdapter extends RecyclerView.Adapter<TourAdapter.ViewHolder> {

    ArrayList<Tour> tourList = new ArrayList<>();
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTourName;
        TextView tvDuration;
        TextView tvTimeLeft;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, TourDetailsActivity.class);
                    intent.putExtra(Constants.CURRENT_TOUR_ID_KEY, tourList.get(getAdapterPosition()).getTourName());
                    context.startActivity(intent);
                }
            });

            tvTourName = (TextView) itemView.findViewById(R.id.tvTourName);
            tvDuration = (TextView) itemView.findViewById(R.id.tvTourDuration);
            tvTimeLeft = (TextView) itemView.findViewById(R.id.tvTimeLeft);
        }
    }

    public TourAdapter(Context context, ArrayList<Tour> tourList) {
        this.tourList = tourList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View rowView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tour_row_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        String tourName = tourList.get(position).getTourName();
        viewHolder.tvTourName.setText(tourName);

        long startDate = tourList.get(position).getStartDateTime();
        long endDate = tourList.get(position).getEndDateTime();
        String duration = AppUtils.getFormattedDate(context, startDate) + " - " + AppUtils.getFormattedDate(context, endDate);
        viewHolder.tvDuration.setText(duration);

        Calendar calendar = Calendar.getInstance();
        String timeLeft = AppUtils.getFriendlyDateDiff(calendar.getTimeInMillis(), startDate);
        viewHolder.tvTimeLeft.setText(timeLeft);
    }

    @Override
    public int getItemCount() {
        return tourList.size();
    }


}
