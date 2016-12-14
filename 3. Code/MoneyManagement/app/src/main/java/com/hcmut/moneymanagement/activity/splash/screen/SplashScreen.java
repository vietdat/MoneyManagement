package com.hcmut.moneymanagement.activity.splash.screen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.activity.login.screen.LoginScreen;
import com.hcmut.moneymanagement.models.Utils;

import java.util.Locale;

/**
 * Created by Admin on 29-Sep-16.
 */
public class SplashScreen extends Activity {

    //Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle saveIntanceState){
        super.onCreate(saveIntanceState);

        SharedPreferences mPrefs = getSharedPreferences("language", MODE_PRIVATE);
        SharedPreferences mPrefs2 = getSharedPreferences("currency", MODE_PRIVATE);
        String lang = mPrefs.getString("language", "0");
        String currency = mPrefs2.getString("currency", "0");
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

        Utils.getDatabase();

        setContentView(R.layout.splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //This method will be executed once the timer is over
                //Start your app main activity
                Intent i = new Intent(SplashScreen.this, LoginScreen.class);
                startActivity(i);

                //close activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
