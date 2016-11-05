package com.hcmut.moneymanagement.activity.Tools.Tips;

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

public class TipsHome extends AppCompatActivity {

    ListView lv;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_home);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = getString(R.string.tips_tittle);
        getSupportActionBar().setTitle(title);

        lv = (ListView) findViewById(R.id.lvTips);
        addItemToListView();
    }


    //add item to listview
    private void addItemToListView() {
        final ArrayList<ListViewModel> arr = new ArrayList<>();
        ListViewModel tips1 = new ListViewModel("ic_profile", "6 mẹo đơn giản giúp bạn tiết kiệm tiền tốt nhất", "Thong tin ve thu nhap cua ban");
        ListViewModel tips2 = new ListViewModel("ic_profile", "6 cách ở tiết kiệm nhất khi đi du lịch", "Thong tin ve chi tieu cua ban");
        ListViewModel tips3 = new ListViewModel("ic_profile", "Lý do vợ chồng tôi đi vay dù không thiếu tiền", "Thong tin ve vi tien cua ban");
        ListViewModel tips4 = new ListViewModel("ic_profile", "Thu nhập không nhiều, vợ chồng tôi vẫn có cách tiết kiệm\n", "Thong tin ve vi tien cua ban");
        ListViewModel tips5 = new ListViewModel("ic_profile", "Cách chi tiêu thông minh của bà mẹ 7 con\n", "Thong tin ve vi tien cua ban");
        ListViewModel tips6 = new ListViewModel("ic_profile", "Lên kế hoạch chi tiêu hàng tháng", "Thong tin ve vi tien cua ban");

        arr.add(tips1);
        arr.add(tips2);
        arr.add(tips3);
        arr.add(tips4);
        arr.add(tips5);
        arr.add(tips6);

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
            TipsHome.this.finish();
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
