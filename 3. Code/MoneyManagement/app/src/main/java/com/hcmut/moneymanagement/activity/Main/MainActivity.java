package com.hcmut.moneymanagement.activity.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.activity.Budget.BudgetHome;
import com.hcmut.moneymanagement.activity.Category.CategoryHome;
import com.hcmut.moneymanagement.activity.Events.EventsHome;
import com.hcmut.moneymanagement.activity.FastInput.FastInputHome;
import com.hcmut.moneymanagement.activity.Graph.GraphHome;
import com.hcmut.moneymanagement.activity.NavDrawItem.activity.FragmentDrawer;
import com.hcmut.moneymanagement.activity.Savings.SavingsHome;
import com.hcmut.moneymanagement.activity.Tools.ToolsHome;
import com.hcmut.moneymanagement.activity.Transaction.TransactionHome;
import com.hcmut.moneymanagement.activity.Wallets.WalletHome;
import com.hcmut.moneymanagement.activity.login.screen.Login;
import com.hcmut.moneymanagement.models.WalletModel;

import java.util.Locale;


public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener, View.OnClickListener {

    private static String TAG = MainActivity.class.getSimpleName();
    public static String currency = "USD";
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private MenuItem mSearchAction;
    private WalletModel walletModel;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        SharedPreferences mPrefs = getSharedPreferences("language", MODE_PRIVATE);
        String lang = mPrefs.getString("language", "0");

        String languageToLoad;
        if (lang.equals("1")) {
            languageToLoad = "vi"; // your language
        } else {
            languageToLoad = "en"; // your language
        }
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config,
                getResources().getDisplayMetrics());


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        // display the first navigation drawer view on app launch
        displayView(0);


    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences mPrefs = getSharedPreferences("language", MODE_PRIVATE);
        String lang = mPrefs.getString("language", "0");

        String languageToLoad;
        if (lang.equals("1")) {
            languageToLoad = "vi"; // your language
        } else {
            languageToLoad = "en"; // your language
        }
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config,
                getResources().getDisplayMetrics());


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
    }


    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }


    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new TransactionHome();
                title = getString(R.string.transaction_title);
                break;
            case 1:
                fragment = new WalletHome();
                title = getString(R.string.wallet_title);
                break;
            case 2:
                fragment = new CategoryHome();
                title = getResources().getString(R.string.category);
                break;
            case 3:
                fragment = new SavingsHome();
                title = getResources().getString(R.string.saving);
                break;
            case 4:
                fragment = new BudgetHome();
                title = getResources().getString(R.string.budgets);
                break;
            case 5:
                fragment = new EventsHome();
                title = getResources().getString(R.string.events);
                break;
            case 6:
                fragment = new FastInputHome();
                title = getResources().getString(R.string.events);
                break;
            case 7:
                fragment = new GraphHome();
                title = getResources().getString(R.string.graphs);
                break;
            case 8:
                fragment = new ToolsHome();
                title = getResources().getString(R.string.tools);
                break;
            //Log out
            case 9:
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                finish();
                startActivity(new Intent(MainActivity.this,Login.class));
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

    @Override
    public void onClick(View view) {

    }
}
