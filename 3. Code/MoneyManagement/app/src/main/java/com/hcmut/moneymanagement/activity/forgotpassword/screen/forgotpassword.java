package com.hcmut.moneymanagement.activity.forgotpassword.screen;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.activity.login.screen.Login;

public class forgotpassword extends AppCompatActivity implements View.OnClickListener {

    private EditText editText_email;
    private Button btn_resetPassword;
    private TextView text_back;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        firebaseAuth = FirebaseAuth.getInstance();

        editText_email = (EditText) findViewById(R.id.input_email);
        btn_resetPassword = (Button) findViewById(R.id.btn_resetpassword);
        text_back = (TextView) findViewById(R.id.back);

        progressDialog = new ProgressDialog(this);

        btn_resetPassword.setOnClickListener(this);
        text_back.setOnClickListener(this);

    }

    private void reserPassword() {
        String email = editText_email.getText().toString().trim();
        if(TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, getResources().getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
            //Stopping the function execution further
            return;
        }

        progressDialog.setMessage(getResources().getString(R.string.reset_password));
        progressDialog.show();

        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, new OnCompleteListener() {

                    @Override
                    public void onComplete(@NonNull Task task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            showFeedbackDialog();

                        }
                    }
                });
    }

    private void showFeedbackDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(forgotpassword.this);
        builder.setTitle(getString(R.string.feedback_forgotpassword_title));
        builder.setMessage(getString(R.string.feedback_forgotpassword_message));

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //start the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), Login.class));
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        if(view == btn_resetPassword) {
            reserPassword();
        }

        if(view == text_back) {
            finish();
            startActivity(new Intent(this,Login.class));
        }
    }
}
