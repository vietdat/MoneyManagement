package com.hcmut.moneymanagement.activity.Graph;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.models.ExpenseCategoryModel;
import com.hcmut.moneymanagement.models.IncomeCategoryModel;
import com.hcmut.moneymanagement.models.TransactionModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GraphHome extends Fragment implements View.OnClickListener {

    private ArrayAdapter<String> typeAdapter;
    private ArrayAdapter<String> typeAdapter2;
    private Spinner spinner1, spinner2;
    private BarChart chart;
    private PieChart pieChart;
    private IncomeCategoryModel incomeCategoryModel;
    private ExpenseCategoryModel expenseCategoryModel;
    String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_graph_home, container, false);
        spinner1 = (Spinner) rootView.findViewById(R.id.type);
        spinner2 = (Spinner) rootView.findViewById(R.id.category);
        chart = (BarChart) rootView.findViewById(R.id.chart);
        pieChart = (PieChart) rootView.findViewById(R.id.piechart);

        String[] types = {getContext().getString(R.string.income),
                getContext().getString(R.string.expense),
                getContext().getString(R.string.netIncome)};
        typeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, types);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        String[] type2 = {getContext().getString(R.string.byTime),
                getContext().getString(R.string.byCategory)};
        typeAdapter2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, type2);
        typeAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        incomeCategoryModel = new IncomeCategoryModel();
        incomeCategoryModel.initListViewAdapter(getContext());
        expenseCategoryModel = new ExpenseCategoryModel() ;
        expenseCategoryModel.initListViewAdapter(getContext());

        //Set adapter to ....
        spinner1.setAdapter(typeAdapter);
        spinner2.setAdapter(typeAdapter2);

        pieChart.setVisibility(View.INVISIBLE);

        drawIncomeAndTime();

        changeSpiner();
        return rootView;
    }

    private void changeSpiner() {
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position == 0) {
                    spinner2.setEnabled(true);
                    if(spinner2.getSelectedItemPosition() == 0) {
                        pieChart.setVisibility(View.INVISIBLE);
                        chart.setVisibility(View.VISIBLE);
                        drawIncomeAndTime();
                    } else {
                        chart.setVisibility(View.INVISIBLE);
                        pieChart.setVisibility(View.VISIBLE);
                        drawIncomeAndCategory(11);
                    }
                } else if(position == 1) {
                    spinner2.setEnabled(true);
                    if(spinner2.getSelectedItemPosition() == 0) {
                        pieChart.setVisibility(View.INVISIBLE);
                        chart.setVisibility(View.VISIBLE);
                        drawExpenseAndTime();
                    } else {
                        chart.setVisibility(View.INVISIBLE);
                        pieChart.setVisibility(View.VISIBLE);
                        drawExpenseAndCategory(11);
                    }
                } else {
                    pieChart.setVisibility(View.INVISIBLE);
                    drawNetIncomeAndTime();
                    spinner2.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

            ;
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position == 0) {
                    pieChart.setVisibility(View.INVISIBLE);
                    chart.setVisibility(View.VISIBLE);
                    if(spinner1.getSelectedItemPosition() == 0) {
                        drawIncomeAndTime();
                    } else {
                        drawExpenseAndTime();
                    }
                } else {
                    chart.setVisibility(View.INVISIBLE);
                    pieChart.setVisibility(View.VISIBLE);
                    if(spinner1.getSelectedItemPosition() == 1) {
                        drawIncomeAndCategory(11);
                    } else {
                        drawExpenseAndCategory(11);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

            ;
        });
    }


    //Draw income - time
    private void drawIncomeAndTime () {
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        final int arrMonth[] = {currentMonth, currentMonth-1, currentMonth-2, currentMonth-3, currentMonth-4};
        final int arrAmount[] = {0, 0, 0, 0, 0};

        //query data
        final TransactionModel transactionModel = new TransactionModel();
        transactionModel.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               for (DataSnapshot transactionSnapshot : dataSnapshot.getChildren()) {
                   Object objType = transactionSnapshot.child(transactionModel.encrypt("type")).getValue();
                    if(objType.equals("Income")) {
                        final Object objAmount = transactionSnapshot.child(transactionModel.encrypt("money")).getValue();
                        final Object objDate = transactionSnapshot.child(transactionModel.encrypt("date")).getValue();
                        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            Date itemDate = format.parse(objDate.toString());

                            Calendar cal = Calendar.getInstance();
                            cal.setTime(itemDate);
                            int itemMonth = cal.get(Calendar.MONTH) + 1;
                            if(itemMonth == arrMonth[0]) {
                                arrAmount[0] = arrAmount[0] + Integer.parseInt(objAmount.toString());
                            } else if(itemMonth == arrMonth[1]) {
                                arrAmount[1] = arrAmount[1] + Integer.parseInt(objAmount.toString());
                            } else if(itemMonth == arrMonth[2]) {
                                arrAmount[2] = arrAmount[2] + Integer.parseInt(objAmount.toString());
                            } else if(itemMonth == arrMonth[3]) {
                                arrAmount[3] = arrAmount[3] + Integer.parseInt(objAmount.toString());
                            } else if(itemMonth == arrMonth[4]) {
                                arrAmount[4] = arrAmount[4] + Integer.parseInt(objAmount.toString());
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
               }

               ArrayList<BarEntry> entries = new ArrayList<>();
                entries.add(new BarEntry(arrAmount[0], 0));
                entries.add(new BarEntry(arrAmount[1], 1));
                entries.add(new BarEntry(arrAmount[2], 2));
                entries.add(new BarEntry(arrAmount[3], 3));
                entries.add(new BarEntry(arrAmount[4], 4));
                ArrayList<String> labels = new ArrayList<String>();
                labels.add(monthNames[arrMonth[0] - 1]);
               labels.add(monthNames[arrMonth[1] - 1]);
               labels.add(monthNames[arrMonth[2] - 1]);
               labels.add(monthNames[arrMonth[3] - 1]);
               labels.add(monthNames[arrMonth[4] - 1]);

                BarDataSet dataset = new BarDataSet(entries, "Total amount");

                BarData data = new BarData(labels,dataset);

                chart.setData(data);
               chart.notifyDataSetChanged();
               chart.invalidate();
               dataset.setColors(ColorTemplate.COLORFUL_COLORS);
           }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    private void drawExpenseAndTime () {
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        final int arrMonth[] = {currentMonth, currentMonth-1, currentMonth-2, currentMonth-3, currentMonth-4};
        final int arrAmount[] = {0, 0, 0, 0, 0};

        //query data
        final TransactionModel transactionModel = new TransactionModel();
        transactionModel.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot transactionSnapshot : dataSnapshot.getChildren()) {
                    Object objType = transactionSnapshot.child(transactionModel.encrypt("type")).getValue();
                    if(objType.equals("Expense")) {
                        final Object objAmount = transactionSnapshot.child(transactionModel.encrypt("money")).getValue();
                        final Object objDate = transactionSnapshot.child(transactionModel.encrypt("date")).getValue();
                        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            Date itemDate = format.parse(objDate.toString());

                            Calendar cal = Calendar.getInstance();
                            cal.setTime(itemDate);
                            int itemMonth = cal.get(Calendar.MONTH) + 1;
                            if(itemMonth == arrMonth[0]) {
                                arrAmount[0] = arrAmount[0] + Integer.parseInt(objAmount.toString());
                            } else if(itemMonth == arrMonth[1]) {
                                arrAmount[1] = arrAmount[1] + Integer.parseInt(objAmount.toString());
                            } else if(itemMonth == arrMonth[2]) {
                                arrAmount[2] = arrAmount[2] + Integer.parseInt(objAmount.toString());
                            } else if(itemMonth == arrMonth[3]) {
                                arrAmount[3] = arrAmount[3] + Integer.parseInt(objAmount.toString());
                            } else if(itemMonth == arrMonth[4]) {
                                arrAmount[4] = arrAmount[4] + Integer.parseInt(objAmount.toString());
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }

                ArrayList<BarEntry> entries = new ArrayList<>();
                entries.add(new BarEntry(arrAmount[0], 0));
                entries.add(new BarEntry(arrAmount[1], 1));
                entries.add(new BarEntry(arrAmount[2], 2));
                entries.add(new BarEntry(arrAmount[3], 3));
                entries.add(new BarEntry(arrAmount[4], 4));
                ArrayList<String> labels = new ArrayList<String>();
                labels.add(monthNames[arrMonth[0] - 1]);
                labels.add(monthNames[arrMonth[1] - 1]);
                labels.add(monthNames[arrMonth[2] - 1]);
                labels.add(monthNames[arrMonth[3] - 1]);
                labels.add(monthNames[arrMonth[4] - 1]);

                BarDataSet dataset = new BarDataSet(entries, "Total amount");

                BarData data = new BarData(labels,dataset);

                chart.setData(data);
                chart.notifyDataSetChanged();
                chart.invalidate();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    private void drawNetIncomeAndTime () {
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        final int arrMonth[] = {currentMonth, currentMonth-1, currentMonth-2, currentMonth-3, currentMonth-4};
        final int arrAmount[] = {0, 0, 0, 0, 0};

        //query data
        final TransactionModel transactionModel = new TransactionModel();
        transactionModel.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot transactionSnapshot : dataSnapshot.getChildren()) {
                    Object objType = transactionSnapshot.child(transactionModel.encrypt("type")).getValue();
                    if(objType.equals("Income")) {
                        final Object objAmount = transactionSnapshot.child(transactionModel.encrypt("money")).getValue();
                        final Object objDate = transactionSnapshot.child(transactionModel.encrypt("date")).getValue();
                        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            Date itemDate = format.parse(objDate.toString());

                            Calendar cal = Calendar.getInstance();
                            cal.setTime(itemDate);
                            int itemMonth = cal.get(Calendar.MONTH) + 1;
                            if(itemMonth == arrMonth[0]) {
                                arrAmount[0] = arrAmount[0] + Integer.parseInt(objAmount.toString());
                            } else if(itemMonth == arrMonth[1]) {
                                arrAmount[1] = arrAmount[1] + Integer.parseInt(objAmount.toString());
                            } else if(itemMonth == arrMonth[2]) {
                                arrAmount[2] = arrAmount[2] + Integer.parseInt(objAmount.toString());
                            } else if(itemMonth == arrMonth[3]) {
                                arrAmount[3] = arrAmount[3] + Integer.parseInt(objAmount.toString());
                            } else if(itemMonth == arrMonth[4]) {
                                arrAmount[4] = arrAmount[4] + Integer.parseInt(objAmount.toString());
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else if (objType.equals("Expense")) {
                        final Object objAmount = transactionSnapshot.child(transactionModel.encrypt("money")).getValue();
                        final Object objDate = transactionSnapshot.child(transactionModel.encrypt("date")).getValue();
                        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            Date itemDate = format.parse(objDate.toString());

                            Calendar cal = Calendar.getInstance();
                            cal.setTime(itemDate);
                            int itemMonth = cal.get(Calendar.MONTH) + 1;
                            if(itemMonth == arrMonth[0]) {
                                arrAmount[0] = arrAmount[0] - Integer.parseInt(objAmount.toString());
                            } else if(itemMonth == arrMonth[1]) {
                                arrAmount[1] = arrAmount[1] - Integer.parseInt(objAmount.toString());
                            } else if(itemMonth == arrMonth[2]) {
                                arrAmount[2] = arrAmount[2] - Integer.parseInt(objAmount.toString());
                            } else if(itemMonth == arrMonth[3]) {
                                arrAmount[3] = arrAmount[3] - Integer.parseInt(objAmount.toString());
                            } else if(itemMonth == arrMonth[4]) {
                                arrAmount[4] = arrAmount[4] - Integer.parseInt(objAmount.toString());
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }

                ArrayList<BarEntry> entries = new ArrayList<>();
                entries.add(new BarEntry(arrAmount[0], 0));
                entries.add(new BarEntry(arrAmount[1], 1));
                entries.add(new BarEntry(arrAmount[2], 2));
                entries.add(new BarEntry(arrAmount[3], 3));
                entries.add(new BarEntry(arrAmount[4], 4));
                ArrayList<String> labels = new ArrayList<String>();
                labels.add(monthNames[arrMonth[0] - 1]);
                labels.add(monthNames[arrMonth[1] - 1]);
                labels.add(monthNames[arrMonth[2] - 1]);
                labels.add(monthNames[arrMonth[3] - 1]);
                labels.add(monthNames[arrMonth[4] - 1]);

                BarDataSet dataset = new BarDataSet(entries, "Total amount");

                BarData data = new BarData(labels,dataset);

                chart.setData(data);
                chart.notifyDataSetChanged();
                chart.invalidate();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    //Draw income - category
    private void drawIncomeAndCategory (final int month) {
        final float arrAmount[] = new float[100];

        //query data
        final TransactionModel transactionModel = new TransactionModel();
        transactionModel.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot transactionSnapshot : dataSnapshot.getChildren()) {
                    Object objType = transactionSnapshot.child(transactionModel.encrypt("type")).getValue();
                    if(objType.equals("Income")) {
                        final Object objAmount = transactionSnapshot.child(transactionModel.encrypt("money")).getValue();
                        final Object objDate = transactionSnapshot.child(transactionModel.encrypt("date")).getValue();
                        final Object objCate = transactionSnapshot.child(transactionModel.encrypt("category")).getValue();
                        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            Date itemDate = format.parse(objDate.toString());

                            Calendar cal = Calendar.getInstance();
                            cal.setTime(itemDate);
                            int itemMonth = cal.get(Calendar.MONTH) + 1;

                            if(itemMonth == month) {
                                for(int i = 0; i < incomeCategoryModel.keys.size(); i ++) {
                                    if(objCate.toString().equals(incomeCategoryModel.keys.get(i))) {
                                        arrAmount[i] = arrAmount[i] + Float.parseFloat(objAmount.toString());
                                    }
                                }
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }

                ArrayList<String> labels = new ArrayList<String>();
                ArrayList<Entry> entries = new ArrayList<>();

                for(int i = 0; i < incomeCategoryModel.keys.size(); i ++) {
                    if(arrAmount[i] > 0) {
                        entries.add(new Entry(arrAmount[i], i));
                        labels.add(incomeCategoryModel.names.get(i));
                    }
                }

                PieDataSet dataset = new PieDataSet(entries, "Total amount");

                PieData data = new PieData(labels,dataset);

                pieChart.setData(data);
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
                dataset.setColors(ColorTemplate.COLORFUL_COLORS);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    //Draw income - category
    private void drawExpenseAndCategory (final int month) {
        final float arrAmount[] = new float[100];

        //query data
        final TransactionModel transactionModel = new TransactionModel();
        transactionModel.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot transactionSnapshot : dataSnapshot.getChildren()) {
                    Object objType = transactionSnapshot.child(transactionModel.encrypt("type")).getValue();
                    if(objType.equals("Expense")) {
                        final Object objAmount = transactionSnapshot.child(transactionModel.encrypt("money")).getValue();
                        final Object objDate = transactionSnapshot.child(transactionModel.encrypt("date")).getValue();
                        final Object objCate = transactionSnapshot.child(transactionModel.encrypt("category")).getValue();
                        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            Date itemDate = format.parse(objDate.toString());

                            Calendar cal = Calendar.getInstance();
                            cal.setTime(itemDate);
                            int itemMonth = cal.get(Calendar.MONTH) + 1;

                            if(itemMonth == month) {
                                for(int i = 0; i < expenseCategoryModel.keys.size(); i ++) {
                                    if(objCate.toString().equals(expenseCategoryModel.keys.get(i))) {
                                        arrAmount[i] = arrAmount[i] + Float.parseFloat(objAmount.toString());
                                    }
                                }
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }

                ArrayList<String> labels = new ArrayList<String>();
                ArrayList<Entry> entries = new ArrayList<>();

                for(int i = 0; i < expenseCategoryModel.keys.size(); i ++) {
                    if(arrAmount[i] > 0) {
                        entries.add(new Entry(arrAmount[i], i));
                        labels.add(expenseCategoryModel.names.get(i));
                    }
                }

                PieDataSet dataset = new PieDataSet(entries, "Total amount");

                PieData data = new PieData(labels,dataset);

                pieChart.setData(data);
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
                dataset.setColors(ColorTemplate.COLORFUL_COLORS);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    //Draw
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View view) {

    }
}
