package com.hcmut.moneymanagement.activity.AddTransactionActivity;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Calendar;

/**
 * Created by Admin on 16-Oct-16.
 * Class use add date to view
 */
public class ViewAddTransaction {

    private Calendar calendar;
    private EditText dateView;
    private Context context;
    private MaterialBetterSpinner materialDesignSpinner;

    public ViewAddTransaction (Context context, EditText dateView, MaterialBetterSpinner materialDesignSpinner) {
        this.context = context;
        this.dateView = dateView;
        this.materialDesignSpinner = materialDesignSpinner;
    }
    //Add Type of transaction
    //menu income, expense
    private void addTypeTransaction() {
        String[] SPINNERLIST = {"Income", "Expense", "Saving"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        materialDesignSpinner.setAdapter(arrayAdapter);
    }
    //Add date of transaction
    private void
    //Add ..
}
