package com.kichukkhon.android.travelpartner.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.kichukkhon.android.travelpartner.Adapter.ExpenseAdapter;
import com.kichukkhon.android.travelpartner.Class.Expense;
import com.kichukkhon.android.travelpartner.Database.ExpenseDBManager;
import com.kichukkhon.android.travelpartner.R;

import java.util.ArrayList;

public class ExpenseInformation extends AppCompatActivity {
    TextView tvBudget;
    TextView tvCurrentAmount;
    RecyclerView recyclerView;
    ExpenseAdapter expenseAdapter;
    Expense expense;
    ArrayList<Expense> expenseList;
    ExpenseDBManager expenseDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_information);

        tvBudget=(TextView)findViewById(R.id.tvBudget);
        tvCurrentAmount=(TextView)findViewById(R.id.tvCurrentAmount);
        recyclerView=(RecyclerView)findViewById(R.id.rvExpenseList);

        expenseDBManager=new ExpenseDBManager(this);
    }

    public void getExpenseData(){
        expenseList=expenseDBManager.getAllExpenseInfo();

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        expenseAdapter=new ExpenseAdapter(expenseList,this);
        expenseAdapter.notifyDataSetChanged();

        recyclerView.setAdapter(expenseAdapter);
    }
}
