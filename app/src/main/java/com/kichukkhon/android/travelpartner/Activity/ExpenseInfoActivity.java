package com.kichukkhon.android.travelpartner.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kichukkhon.android.travelpartner.Adapter.ExpenseAdapter;
import com.kichukkhon.android.travelpartner.Class.Expense;
import com.kichukkhon.android.travelpartner.Class.Tour;
import com.kichukkhon.android.travelpartner.Database.ExpenseDBManager;
import com.kichukkhon.android.travelpartner.Database.TourDBManager;
import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Util.Constants;

import java.util.ArrayList;

public class ExpenseInfoActivity extends AppCompatActivity {
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
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_information);

        toolbar = (Toolbar) findViewById(R.id.toolbarWithAppbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Expense Info");
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        tvBudgetAmount = (TextView) findViewById(R.id.tvAmount);
        tvCurrentAmount = (TextView) findViewById(R.id.tvCurrentAmount);
        recyclerView = (RecyclerView) findViewById(R.id.rvExpenseList);


        expenseDBManager = new ExpenseDBManager(this);
        tourDBManager = new TourDBManager(this);

        currentTourId = getIntent().getIntExtra(Constants.CURRENT_TOUR_ID_KEY, 1);

        tour = tourDBManager.getTourInfoById(currentTourId);

        getExpenseData();
        calculateCurrentBalance(tour.getBudget(), expenseList);

    }

    public void getExpenseData() {
        double budget = tour.getBudget();
        tvBudgetAmount.setText(String.valueOf(budget));

        expenseList = expenseDBManager.getExpenseInfoByTourId(currentTourId);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        expenseAdapter = new ExpenseAdapter(this, expenseList);
        expenseAdapter.notifyDataSetChanged();

        recyclerView.setAdapter(expenseAdapter);
    }

    public void fabAddExpense(View view) {
        setupDialog();

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);

        builder.setTitle(getString(R.string.dialog_title));
        builder.setView(dialogView);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                txtPurpose = (EditText) dialogView.findViewById(R.id.txtPurpose);
                txtAmount = (EditText) dialogView.findViewById(R.id.txtAmount);

                String purpose = txtPurpose.getText().toString().trim();
                double amount = Double.parseDouble(txtAmount.getText().toString().trim());
                long date = System.currentTimeMillis();

                expense = new Expense();

                expense.setPurpose(purpose);
                expense.setAmount(amount);
                expense.setDateTime(date);
                expense.setTourId(currentTourId);

                boolean inserted = expenseDBManager.addExpense(expense);
                if (inserted) {
                    Toast.makeText(ExpenseInfoActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(ExpenseInfoActivity.this, "Data not Inserted", Toast.LENGTH_SHORT).show();

                getExpenseData();
                calculateCurrentBalance(tour.getBudget(), expenseList);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    private void setupDialog() {
        dialogView = LayoutInflater.from(this).inflate(R.layout.expense_dialog_layout, null, false);

        final TextInputLayout purposeInputLayout = (TextInputLayout) dialogView.findViewById(R.id.purposeTitle);
        final TextInputLayout amountInputLayout = (TextInputLayout) dialogView.findViewById(R.id.titleAmount);

        purposeInputLayout.setErrorEnabled(true);
        amountInputLayout.setErrorEnabled(true);
    }

    private double calculateCurrentBalance(double budget, ArrayList<Expense> expenseList) {
        double totalExpense = 0.0;

        for (Expense expense : expenseList) {
            totalExpense += expense.getAmount();
        }
        double result = budget - totalExpense;
        tvCurrentAmount.setText(String.valueOf(result));
        return result;
    }


}
