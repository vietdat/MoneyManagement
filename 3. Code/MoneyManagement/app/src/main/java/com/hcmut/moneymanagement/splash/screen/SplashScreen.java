package com.hcmut.moneymanagement.splash.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.login.screen.LoginScreen;

/**
 * Created by Admin on 29-Sep-16.
 */
public class SplashScreen extends Activity {

    //Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle saveIntanceState){
        super.onCreate(saveIntanceState);
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
