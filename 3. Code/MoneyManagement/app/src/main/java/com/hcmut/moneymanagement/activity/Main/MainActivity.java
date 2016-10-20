package com.hcmut.moneymanagement.activity.Main;

import android.content.Intent;
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

import com.google.firebase.auth.FirebaseAuth;
import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.activity.NavDrawItem.activity.FragmentDrawer;
import com.hcmut.moneymanagement.activity.Transaction.TransactionHome;
import com.hcmut.moneymanagement.activity.Wallet.WalletHome;
import com.hcmut.moneymanagement.activity.login.screen.Login;


public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener, View.OnClickListener {

    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private MenuItem mSearchAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);

//        final UserModel userModel = new UserModel();
//        //userModel.initUserData();
//        userModel.write("username", "Nguyễn Hoàng Anh");

        //final TextView txtUsername = new TextView(this);

        // Handle username example
//        DatabaseReference userReference = userModel.getReference();
//        userReference.child(userModel.encrypt("username"))
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Object object = dataSnapshot.getValue();
//                String value = userModel.decrypt(object.toString());
//                Log.w("Username", value);
//                //txtUsername.setText(value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.w("Database Error:", databaseError.toString());
//            }
//        });

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_done);
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

        switch (id) {
//            case R.id.action_settings:
//                return true;
            case R.id.action_done:
                //handleMenuSearch();
                return true;
        }

        return super.onOptionsItemSelected(item);
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

            //Log out
            case 3:
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
