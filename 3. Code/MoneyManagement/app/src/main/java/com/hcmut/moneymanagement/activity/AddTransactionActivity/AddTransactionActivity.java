package com.hcmut.moneymanagement.activity.AddTransactionActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.hcmut.moneymanagement.activity.NavDrawItem.activity.FragmentDrawer;
import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.activity.transaction.Transaction_Home;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Calendar;

public class AddTransactionActivity extends AppCompatActivity
                                    implements FragmentDrawer.FragmentDrawerListener,
                                                OnClickListener {

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private Calendar calendar;
    private EditText dateView;
    private MaterialBetterSpinner typeTransaction;
    private MaterialBetterSpinner wallet;
    private MaterialBetterSpinner category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);


        init();


    }

    private void  init(){
        //init
        dateView = (EditText) findViewById(R.id.input_date);
        typeTransaction = (MaterialBetterSpinner) findViewById(R.id.typeTransaction);
        category = (MaterialBetterSpinner) findViewById(R.id.category);
        wallet = (MaterialBetterSpinner) findViewById(R.id.wallet);

        Controller_AddTransaction add_default = new Controller_AddTransaction(this,typeTransaction,category,wallet);

        add_default.showTypeTransaction();
        add_default.showCategorys();
        add_default.showWallets();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_transaction, menu);
        return true;
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }


    //navigation
    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new Transaction_Home();
                title = getString(R.string.transaction_title);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }


    public void onStart(){
        super.onStart();

        EditText txtDate=(EditText)findViewById(R.id.input_date);
        txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View view, boolean hasfocus){
                if(hasfocus){
                    DateDialog dialog=new DateDialog(view);
                    android.app.FragmentTransaction ft =getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");

                }
            }

        });
    }

    @Override
    public void onClick(View view) {
        if (view == dateView) {

        }
    }

}
