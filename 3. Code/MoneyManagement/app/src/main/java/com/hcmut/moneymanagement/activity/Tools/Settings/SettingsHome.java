package com.hcmut.moneymanagement.activity.Tools.Settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.activity.CustomListView.Model.ListViewModel;
import com.hcmut.moneymanagement.activity.Tools.Settings.LockApp.ConfigLockApp;
import com.hcmut.moneymanagement.activity.Tools.Settings.LockApp.DialogPassword;
import com.hcmut.moneymanagement.activity.Tools.ToolsAdapter;

import java.util.ArrayList;
import java.util.Locale;

public class SettingsHome extends AppCompatActivity {
    ListView lv;
    Toolbar mToolbar;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());


        setContentView(R.layout.activity_settings_home);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        String title = getString(R.string.setting_tittle);
        getSupportActionBar().setTitle(title);

        lv = (ListView) findViewById(R.id.lvSettings);
        addItemToListView();
        selecteItemInListView();
    }

    //add item to listview
    private void addItemToListView() {
        final ArrayList<ListViewModel> arr = new ArrayList<>();
        ListViewModel setting1 = new ListViewModel("ic_profile", "Change currency", "Thong tin ve thu nhap cua ban");
        ListViewModel setting2 = new ListViewModel("ic_profile", "Change username", "Thong tin ve chi tieu cua ban");
        boolean isNoLock = ConfigLockApp.config.getString(ConfigLockApp.IS_LOCK,"").isEmpty();
        ListViewModel setting3;
        if(isNoLock) {
            setting3 = new ListViewModel("ic_profile", "Lock app", "Thong tin ve vi tien cua ban");
        } else {
            setting3 = new ListViewModel("ic_profile", "Remove lock", "Thong tin ve vi tien cua ban");
        }
        ListViewModel setting4 = new ListViewModel("ic_profile", getApplicationContext().getString(R.string.change_laguage), "Thong tin ve vi tien cua ban");
        arr.add(setting1);
        arr.add(setting2);
        arr.add(setting3);
        arr.add(setting4);

        ToolsAdapter mayArr = new ToolsAdapter(this, R.layout.tool_item, arr);

        mayArr.notifyDataSetChanged();
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
                if(position == 0) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(context);
                    CharSequence items[] = new CharSequence[] {"USD","VND"};
                    final SharedPreferences mPrefs = getSharedPreferences("language", MODE_PRIVATE);
                    String currency = mPrefs.getString("currency", "0");
                    int pos;
                    if (currency.equals("1")) {
                        pos = 1;
                    } else {
                        pos = 0;
                    }

                    adb.setSingleChoiceItems(items, pos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface d, int n) {
                            SharedPreferences.Editor edit = mPrefs.edit();
                            edit.putString("currency", Integer.toString(n));

                            edit.commit();
                            d.dismiss();
                            finish();
                        }
                    });
                    adb.setPositiveButton("Cancel", null);
                    adb.setTitle("Choose type currency");
                    adb.show();
                } else if(position == 1) {

                } else if(position == 2) {
                    boolean isNoLock = ConfigLockApp.config.getString(ConfigLockApp.IS_LOCK,"").isEmpty();
                    if(isNoLock) {
                        boolean isNoSetPass = ConfigLockApp.config.getString(ConfigLockApp.KEY_PATTERNLOCK,"").isEmpty();
                        //set new pass
                        if(isNoSetPass){
                            DialogPassword dp = new DialogPassword(context,DialogPassword.TYPE_SET_NEW_PASSWORD);
                            dp.show();
                        }
                        else{
                            new AlertDialog.Builder(context)
                                    .setTitle(R.string.txt_info)
                                    .setMessage(R.string.question_set_new_password)
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            DialogPassword dp = new DialogPassword(context,DialogPassword.TYPE_CONFIRM_OLD_PASSWORD);
                                            dp.show();
                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            ConfigLockApp.editorConfig.putString(ConfigLockApp.IS_LOCK,"LOCK");
                                            ConfigLockApp.editorConfig.apply();
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                    } else {
                        DialogPassword dp = new DialogPassword(context,DialogPassword.TYPE_UNLOCK);
                        dp.show();
                    }
                } else if(position == 3) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(context);
                    CharSequence items[] = new CharSequence[] {context.getApplicationContext().getString(R.string.english),
                            context.getApplicationContext().getString(R.string.vietnamese)};
                    final SharedPreferences mPrefs = getSharedPreferences("language", MODE_PRIVATE);
                    String lang = mPrefs.getString("language", "0");
                    int pos;
                    if (lang.equals("1")) {
                        pos = 1;
                    } else {
                        pos = 0;
                    }

                    adb.setSingleChoiceItems(items, pos, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface d, int n) {
                            SharedPreferences.Editor edit = mPrefs.edit();
                            edit.putString("language", Integer.toString(n));

                            edit.commit();
                            d.dismiss();
                            finish();
                        }
                    });
                    adb.setPositiveButton("Cancel", null);
                    adb.setTitle("Choose language");
                    adb.show();
                }
            }
        });
    }

}