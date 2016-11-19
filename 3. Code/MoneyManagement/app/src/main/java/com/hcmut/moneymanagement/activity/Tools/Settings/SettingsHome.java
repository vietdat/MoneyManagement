package com.hcmut.moneymanagement.activity.Tools.Settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.activity.CustomListView.Model.ListViewModel;
import com.hcmut.moneymanagement.activity.Tools.ToolsAdapter;

import java.util.ArrayList;

public class SettingsHome extends AppCompatActivity {

    ListView lv;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_home);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = getString(R.string.setting_tittle);
        getSupportActionBar().setTitle(title);

        lv = (ListView) findViewById(R.id.lvSettings);
        addItemToListView();
    }


    //add item to listview
    private void addItemToListView() {
        final ArrayList<ListViewModel> arr = new ArrayList<>();
        ListViewModel setting1 = new ListViewModel("ic_profile", "Change currency", "Thong tin ve thu nhap cua ban");
        ListViewModel setting2 = new ListViewModel("ic_profile", "Change username", "Thong tin ve chi tieu cua ban");
        ListViewModel setting3 = new ListViewModel("ic_profile", "Lock app", "Thong tin ve vi tien cua ban");
        ListViewModel setting4 = new ListViewModel("ic_profile", "Change language", "Thong tin ve vi tien cua ban");
        arr.add(setting1);
        arr.add(setting2);
        arr.add(setting3);
        arr.add(setting4);

        ToolsAdapter mayArr = new ToolsAdapter(this, R.layout.tool_item, arr);

        lv.setAdapter(mayArr);
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

        if(id == android.R.id.home){
            SettingsHome.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void selecteItemInListView() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Wallet wallet = walletModel.wallets.get(position);
//                Intent intent = new Intent(getActivity(), WalletDetai.class);
//                String key = walletModel.keys.get(position);
//                intent.putExtra("key", key);
//                intent.putExtra("wallet", (Serializable) wallet);
//                startActivity(intent);
            }
        });
    }

}