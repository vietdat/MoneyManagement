package com.hcmut.moneymanagement.activity.Tools.Settings.LockApp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amnix.materiallockview.MaterialLockView;
import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.activity.Main.MainActivity;

import java.util.List;

public class DialogPassword  extends Dialog implements
        View.OnClickListener {
    static final public  Integer TYPE_SET_NEW_PASSWORD = 1;
    static public  Integer TYPE_CONFIRM_NEW_PASSWORD = 2;
    static public  Integer TYPE_CONFIRM_OLD_PASSWORD = 3;
    static public  Integer TYPE_UNLOCK = 4;
    static public  Integer TYPE_LOCK_SCREEN = 5;

    public Context context;

    public Dialog d;
    public Button yes, no;
    private TextView  titleDialog;
    private MaterialLockView materialLockView;
    Integer typeDialog;
    DialogPassword dialogPassword;


    public DialogPassword(Context a,Integer typeDialog) {
        super(a);
        // TODO Auto-generated constructor stub
        this.context = a;
        this.typeDialog = typeDialog;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //config dialog
        dialogPassword = this;
        dialogPassword.setCanceledOnTouchOutside(false);
        dialogPassword.setCancelable(false);
        dialogPassword.setContentView(R.layout.dialog_password);

        //init obj view
        titleDialog = (TextView) findViewById(R.id.title_dialog);
        materialLockView = (MaterialLockView) findViewById(R.id.pattern_password);

        // created new password
        if(typeDialog.equals(TYPE_SET_NEW_PASSWORD)){
            //set tilte
            titleDialog.setText(R.string.title_set_new_password_dialog);
            //set event
            materialLockView.setOnPatternListener(new MaterialLockView.OnPatternListener() {
                @Override
                public void onPatternDetected(List<MaterialLockView.Cell> pattern, String SimplePattern) {
                    ConfigLockApp.editorConfig.putString(ConfigLockApp.KEY_TEMP_PATTERNLOCK,SimplePattern);
                    ConfigLockApp.editorConfig.apply();
                    dialogPassword.hide();
                    DialogPassword dp = new DialogPassword(context,DialogPassword.TYPE_CONFIRM_NEW_PASSWORD);
                    dp.show();
                    super.onPatternDetected(pattern, SimplePattern);
                }
            });

        }// confirm new password
        else if(typeDialog.equals(TYPE_CONFIRM_NEW_PASSWORD)){
            //set tilte
            titleDialog.setText(R.string.title_confirm_password_dialog);
            //set event
            materialLockView.setOnPatternListener(new MaterialLockView.OnPatternListener() {
                @Override
                public void onPatternDetected(List<MaterialLockView.Cell> pattern, String SimplePattern)  {
                    String tempPass = ConfigLockApp.config.getString(ConfigLockApp.KEY_TEMP_PATTERNLOCK,"");
                    if(!SimplePattern.isEmpty()&&SimplePattern.equals(tempPass)){
                        ConfigLockApp.editorConfig.putString(ConfigLockApp.KEY_PATTERNLOCK,SimplePattern);
                        ConfigLockApp.editorConfig.putString(ConfigLockApp.KEY_TEMP_PATTERNLOCK,"");
                        ConfigLockApp.editorConfig.putString(ConfigLockApp.IS_LOCK,"LOCK");
                        ConfigLockApp.editorConfig.apply();
                        Toast.makeText(context,"Cài mật khẩu thành công",Toast.LENGTH_LONG).show();
                        //checkLockApp.setChecked(true);
                        dialogPassword.hide();
                    }else{
                        Toast.makeText(context,"Mật khẩu không đúng",Toast.LENGTH_LONG).show();
                    }
                    super.onPatternDetected(pattern, SimplePattern);
                }
            });
        }//confirm old pass word to set new password
        else if(typeDialog.equals(TYPE_CONFIRM_OLD_PASSWORD)){
            //set tilte
            titleDialog.setText(R.string.title_confirm_old_password_dialog);
            // set event
            materialLockView.setOnPatternListener(new MaterialLockView.OnPatternListener() {
                @Override
                public void onPatternDetected(List<MaterialLockView.Cell> pattern, String SimplePattern) {
                    String pass = ConfigLockApp.config.getString(ConfigLockApp.KEY_PATTERNLOCK,"");
                    if(!pass.isEmpty()&&SimplePattern.equals(pass)){
                        dialogPassword.hide();
                        DialogPassword dp = new DialogPassword(context,DialogPassword.TYPE_SET_NEW_PASSWORD);
                        dp.show();
                    }else{
                        Toast.makeText(context,"Mật khẩu không đúng",Toast.LENGTH_LONG).show();
                    }
                    super.onPatternDetected(pattern, SimplePattern);
                }
            });
        }// unlock app
        else if(typeDialog.equals(TYPE_UNLOCK)){
            //set tilte
            titleDialog.setText(R.string.title_confirm_password_dialog);
            // set event
            materialLockView.setOnPatternListener(new MaterialLockView.OnPatternListener() {
                @Override
                public void onPatternDetected(List<MaterialLockView.Cell> pattern, String SimplePattern){
                    String pass = ConfigLockApp.config.getString(ConfigLockApp.KEY_PATTERNLOCK,"");
                    if(pass.isEmpty()||SimplePattern.equals(pass)){
                        ConfigLockApp.editorConfig.remove(ConfigLockApp.IS_LOCK);
                        ConfigLockApp.editorConfig.apply();
                        Toast.makeText(context,"Gỡ khóa thành công",Toast.LENGTH_LONG).show();
                        //checkLockApp.setChecked(false);
                        dialogPassword.hide();


                    }else{
                        Toast.makeText(context,"Mật khẩu không đúng",Toast.LENGTH_LONG).show();
                    }
                    super.onPatternDetected(pattern, SimplePattern);
                }
            });
        }//lock screen when is checked lock
        else if(typeDialog.equals(TYPE_LOCK_SCREEN)){
            //set tilte
            titleDialog.setText(R.string.title_confirm_password_dialog);
            //set event
            materialLockView.setOnPatternListener(new MaterialLockView.OnPatternListener() {
                @Override
                public void onPatternDetected(List<MaterialLockView.Cell> pattern, String SimplePattern) {
                    String pass = ConfigLockApp.config.getString(ConfigLockApp.KEY_PATTERNLOCK,"");
                    if(pass.isEmpty()||SimplePattern.equals(pass)){
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                        dialogPassword.hide();
                    }else{
                        materialLockView.setDisplayMode(MaterialLockView.DisplayMode.Wrong);
                        Toast.makeText(context,getContext().getString(R.string.wrong_password),Toast.LENGTH_LONG).show();
                    }

                    super.onPatternDetected(pattern, SimplePattern);
                }
            });
        }
    }
    @Override
    public void onClick(View v) {

    }
}
