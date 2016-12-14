package com.hcmut.moneymanagement.activity.Budget;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.objects.Budget;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BudgetHis extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String startDate, endDate, type;
    Budget budget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_his);

        Bundle extras = getIntent().getExtras();
        budget = (Budget) getIntent().getSerializableExtra("budget");
        startDate = budget.getStartDate();
        endDate = budget.getEndDate();
        type = budget.getCategory();

        init();
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager, 3);
    }

    private void setupViewPager(ViewPager viewPager, int months) {
        SimpleDateFormat format = new SimpleDateFormat("MMMM");
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // NOT ALL OF TIME OPTIONS
        if( months != Integer.MAX_VALUE ){
            for( int i = 0; i<months; i++){
                Calendar c = Calendar.getInstance();
                c.add(Calendar.MONTH, -i);
                String str_month = format.format(c.getTime());
                int month = c.get(Calendar.MONTH) + 1;

                adapter.addFrag(new com.hcmut.moneymanagement.activity.Budget.FragmentIE(startDate, endDate, type, month), str_month);
            }
        }

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_month_number, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if( id== R.id.thisMonth){
            setupViewPager(viewPager, 1);
        }else if (id == R.id.threeMonths) {
            setupViewPager(viewPager, 3);
        }else if(id == android.R.id.home){
            this.finish();
        }

        return true;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}

