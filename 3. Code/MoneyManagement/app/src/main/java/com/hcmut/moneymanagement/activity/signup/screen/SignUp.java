package com.hcmut.moneymanagement.activity.signup.screen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.activity.login.screen.Login;
import com.hcmut.moneymanagement.models.UserModel;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignupActivity";

    private EditText editTextUserName;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private EditText editTextEmail;
    private TextView textViewSignUpLink;
    private Button btnSignUp;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextUserName = (EditText) findViewById(R.id.input_name);
        editTextPassword = (EditText) findViewById(R.id.input_password);
        editTextConfirmPassword = (EditText) findViewById(R.id.input_confirm_password);
        editTextEmail = (EditText) findViewById(R.id.input_email);
        btnSignUp = (Button) findViewById(R.id.btn_signup);
        textViewSignUpLink = (TextView) findViewById(R.id.link_login);

        mAuth = FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(this);
        textViewSignUpLink.setOnClickListener(this);

    }

    private void userSignUp() {
        if (!validate()) {
            onSignupFailed();
            return;
        }
        else {
            btnSignUp.setEnabled(false);

            progressDialog = new ProgressDialog(SignUp.this, R.style.AppTheme_Dark_Dialog);

            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(getResources().getString(R.string.create_account));

            String name = editTextUserName.getText().toString();
            final String email = editTextEmail.getText().toString();
            final String password = editTextPassword.getText().toString();

            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.show();
                    onSignupHandler(email, password);
                }
            });
        }
    }


    private void onSignupHandler(String email, String password) {
        btnSignUp.setEnabled(true);
        setResult(RESULT_OK, null);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUp.this,getResources().getString(R.string.auth_fail),Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                        else  {
                            createUserData();
                            progressDialog.dismiss();
                            Toast.makeText(SignUp.this,getResources().getString(R.string.successful),Toast.LENGTH_LONG).show();
                            startActivity(new Intent(SignUp.this,Login.class));
                        }
                    }
                });
    }

    public void createUserData() {
        final String userName = editTextUserName.getText().toString().trim();

        UserModel userModel = new UserModel();

        userModel.initUserData();
        userModel.write("username",userName);
    }

    @Override
    public void onClick(View view) {
        if(view == btnSignUp) {
            userSignUp();
        }

        if(view == textViewSignUpLink) {
            finish();
            startActivity(new Intent(this,Login.class));
        }
    }

    //If sign up fail => call it.
    private void onSignupFailed() {
        Toast.makeText(getBaseContext(), getResources().getString(R.string.sign_up_fail), Toast.LENGTH_SHORT).show();
        btnSignUp.setEnabled(true);
    }

    //Check validate name, email, password
    public boolean validate() {
        boolean valid = true;

        String name = editTextUserName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            editTextUserName.setError(getResources().getString(R.string.least_3_character));
            valid = false;
        } else {
            editTextUserName.setError(null);
        }

//        FirebaseUser user = mAuth.getInstance().getCurrentUser();
//        if(email.equals(user.getEmail().trim())) {
//            editTextUserName.setError("Email already used! Want to login or recover your password?");
//            valid = false;
//        } else {
//            editTextUserName.setError(null);
//        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError(getResources().getString(R.string.enter_valid_email));
            valid = false;
        } else {
            editTextEmail.setError(null);
        }

        if (password.isEmpty()) {
            editTextPassword.setError(getResources().getString(R.string.enter_password));
            valid = false;
        } else {
            editTextPassword.setError(null);
        }

        if (!password.equals(confirmPassword)){
            editTextConfirmPassword.setError(getResources().getString(R.string.password_not_match));
            valid = false;
        } else {
            editTextConfirmPassword.setError(null);
        }

        return valid;
    }
}
