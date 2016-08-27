package com.kichukkhon.android.travelpartner.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.kichukkhon.android.travelpartner.Adapter.ExpenseAdapter;
import com.kichukkhon.android.travelpartner.Class.Expense;
import com.kichukkhon.android.travelpartner.Class.Tour;
import com.kichukkhon.android.travelpartner.Database.ExpenseDBManager;
import com.kichukkhon.android.travelpartner.Database.TourDBManager;
import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Util.AppUtils;
import com.kichukkhon.android.travelpartner.Util.Constants;

import java.util.ArrayList;

public class ExpenseInfoActivity extends AppCompatActivity {
    TextView tvBudget;
    TextView tvCurrent;
    TextView tvCurrentAmount;
    TextView tvBudgetAmount;
    EditText txtPurpose;
    EditText txtAmount;
    RecyclerView recyclerView;
    ExpenseAdapter expenseAdapter;
    Expense expense;
    ArrayList<Expense> expenseList;
    ExpenseDBManager expenseDBManager;
    TourDBManager tourDBManager;
    Tour tour;
    int currentTourId;
    View dialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_information);

        tvBudget = (TextView) findViewById(R.id.tvBudget);
        tvCurrentAmount = (TextView) findViewById(R.id.tvCurrentAmount);
        tvBudgetAmount = (TextView) findViewById(R.id.tvAmount);
        tvCurrent = (TextView) findViewById(R.id.tvCAmount);
        recyclerView = (RecyclerView) findViewById(R.id.rvExpenseList);


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

    public void fabAddExpense(View view) {
        setupDialog();

        AlertDialog.Builder builder=new AlertDialog.Builder(this,R.style.AppCompatAlertDialogStyle);

        builder.setTitle(getString(R.string.dialog_title));
        builder.setView(dialogView);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                /*String purpose=((EditText)dialogView.findViewById(R.id.txtPurpose))
                        .getText().toString().trim();
                String amount=((EditText)dialogView.findViewById(R.id.txtAmount))
                        .getText().toString().trim();*/

                txtPurpose=(EditText)dialogView.findViewById(R.id.txtPurpose);
                txtAmount=(EditText)dialogView.findViewById(R.id.txtAmount);

                String purpose=txtPurpose.getText().toString();
                double amount=Double.parseDouble(txtAmount.getText().toString());
                long date=System.currentTimeMillis();

                expense=new Expense();

                expense.setPurpose(purpose);
                expense.setAmount(amount);
                expense.setDateTime(date);

                boolean inserted=expenseDBManager.addExpense(expense);
                if (inserted) {
                    Toast.makeText(ExpenseInfoActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(ExpenseInfoActivity.this, "Data not Inserted", Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNegativeButton("Cancel",null);
        builder.show().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    private void setupDialog(){
        dialogView= LayoutInflater.from(this).inflate(R.layout.expense_dialog_layout,null,false);

        final TextInputLayout purposeInputLayout=(TextInputLayout)dialogView.findViewById(R.id.purposeTitle);
        final TextInputLayout amountInputLayout=(TextInputLayout)dialogView.findViewById(R.id.titleAmount);

        purposeInputLayout.setErrorEnabled(true);
        amountInputLayout.setErrorEnabled(true);
    }
}
