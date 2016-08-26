package com.kichukkhon.android.travelpartner.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kichukkhon.android.travelpartner.Adapter.ExpenseAdapter;
import com.kichukkhon.android.travelpartner.Class.Expense;
import com.kichukkhon.android.travelpartner.Class.Tour;
import com.kichukkhon.android.travelpartner.Database.ExpenseDBManager;
import com.kichukkhon.android.travelpartner.Database.TourDBManager;
import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Util.Constants;

import java.util.ArrayList;

public class ExpenseInfoActivity extends AppCompatActivity {
    TextView tvBudget;
    TextView tvCurrent;
    TextView tvCurrentAmount;
    TextView tvBudgetAmount;
    RecyclerView recyclerView;
    FloatingActionButton fab;
    ExpenseAdapter expenseAdapter;
    Expense expense;
    ArrayList<Expense> expenseList;
    ExpenseDBManager expenseDBManager;
    TourDBManager tourDBManager;
    Tour tour;
    int currentTourId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_information);

        tvBudget = (TextView) findViewById(R.id.tvBudget);
        tvCurrentAmount = (TextView) findViewById(R.id.tvCurrentAmount);
        tvBudgetAmount = (TextView) findViewById(R.id.tvAmount);
        tvCurrent = (TextView) findViewById(R.id.tvCAmount);
        recyclerView = (RecyclerView) findViewById(R.id.rvExpenseList);
        fab=(FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ExpenseInfoActivity.this, TourEntryDestinationActivity.class);
                startActivity(intent);
            }
        });

        expenseDBManager = new ExpenseDBManager(this);
        tourDBManager = new TourDBManager(this);

        Bundle extras = getIntent().getExtras();
        //currentTourId = extras.getInt(Constants.CURRENT_TOUR_ID_KEY, 1);
        currentTourId=1;

        tour = tourDBManager.getTourInfoById(currentTourId);
        getExpenseData();
    }

    public void getExpenseData() {
        double budget = tour.getBudget();

        tvBudgetAmount.setText(String.valueOf(budget));
        expenseList = expenseDBManager.getExpenseInfoByTourId(currentTourId);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        expenseAdapter = new ExpenseAdapter(expenseList, this);
        expenseAdapter.notifyDataSetChanged();

        recyclerView.setAdapter(expenseAdapter);
    }
}
