package com.hcmut.moneymanagement.activity.Tools.Settings.LockApp;

import android.content.Context;
import android.content.SharedPreferences;

import com.hcmut.moneymanagement.activity.login.screen.Login;

public class ConfigLockApp
{
        public final  static  SharedPreferences config = Login.context.getSharedPreferences(ConfigLockApp.SETTING_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        public final  static  SharedPreferences.Editor editorConfig = config.edit();
        //name of file stores parameter config
        public final  static  String SETTING_SHARED_PREFERENCE = "SETTING_SHARED_PREFERENCE";
        //name of parameter IS_LOCK
        public final  static  String IS_LOCK = "IS_LOCK";
        //name of parameter KEY_PATTERNLOCK
        public final  static  String KEY_PATTERNLOCK = "KEY_PATTERNLOCK";
        public final  static  String KEY_TEMP_PATTERNLOCK = "KEY_TEMP_PATTERNLOCK";
}
