package com.hcmut.moneymanagement.activity.login.screen;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.activity.Main.MainActivity;
import com.hcmut.moneymanagement.activity.Tools.Settings.LockApp.ConfigLockApp;
import com.hcmut.moneymanagement.activity.Tools.Settings.LockApp.DialogPassword;
import com.hcmut.moneymanagement.activity.forgotpassword.screen.forgotpassword;
import com.hcmut.moneymanagement.activity.signup.screen.SignUp;

public class Login extends Activity implements View.OnClickListener{
    public static Context context;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignIn;
    private TextView textViewSignup;
    private TextView textForgotPassword;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        context = this;
        firebaseAuth = FirebaseAuth.getInstance();

        //Check already login
        if(firebaseAuth.getCurrentUser() != null){
            boolean isNoLock = ConfigLockApp.config.getString(ConfigLockApp.IS_LOCK,"").isEmpty();
            if(isNoLock) {
                finish();
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            } else {
                DialogPassword dp = new DialogPassword(Login.this,DialogPassword.TYPE_LOCK_SCREEN);
                dp.show();
            }

        }

        editTextEmail = (EditText) findViewById(R.id.input_email);
        editTextPassword = (EditText) findViewById(R.id.input_password);
        buttonSignIn = (Button) findViewById(R.id.btn_login);
        textViewSignup = (TextView) findViewById(R.id.link_signup);
        textForgotPassword = (TextView) findViewById(R.id.textview_forgotpass);

        progressDialog = new ProgressDialog(this);

        buttonSignIn.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
        textForgotPassword.setOnClickListener(this);
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, getResources().getString(R.string.enter_email), Toast.LENGTH_SHORT).show();

            //Stopping the function execution further
            return;
        }

        if(TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, getResources().getString(R.string.enter_password), Toast.LENGTH_SHORT).show();

            //Stopping the function execution further
            return;
        }

        //if validations are ok
        //we will first show a progress dialog


        progressDialog.setMessage(getResources().getString(R.string.register_user));
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignIn) {
            userLogin();
        }

        if(view == textViewSignup) {
            finish();
            startActivity(new Intent(this,SignUp.class));
        }

        if(view == textForgotPassword) {
            finish();
            startActivity(new Intent(this,forgotpassword.class));
        }
    }
}
