package com.kichukkhon.android.travelpartner.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kichukkhon.android.travelpartner.Class.Expense;
import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Util.AppUtils;

import java.util.ArrayList;

/**
 * Created by Bridget on 8/26/2016.
 */
public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {
    ArrayList<Expense> expenseList=new ArrayList<>();
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvPurpose;
        TextView tvAmount;
        TextView tvDate;

        public ViewHolder(View itemView) {
            super(itemView);

            tvPurpose=(TextView)itemView.findViewById(R.id.tvPurpose);
            tvAmount=(TextView)itemView.findViewById(R.id.tvAmount);
            tvDate=(TextView)itemView.findViewById(R.id.tvDate);
        }

    }

    public ExpenseAdapter(ArrayList<Expense> expenseList, Context context) {
        this.expenseList = expenseList;
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
        String purpose=expenseList.get(position).getPurpose();
        double amount=expenseList.get(position).getAmount();
        long date=expenseList.get(position).getDateTime();

        viewHolder.tvPurpose.setText(purpose);
        viewHolder.tvAmount.setText(String.valueOf(amount));
        viewHolder.tvDate.setText(AppUtils.getFriendlyDayString(context,date));

    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }
}
