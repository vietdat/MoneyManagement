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
import com.google.firebase.auth.FirebaseUser;
import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.activity.login.screen.Login;

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
        Log.d(TAG, "Sign up");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        btnSignUp.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignUp.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = editTextUserName.getText().toString();
        final String email = editTextEmail.getText().toString();
        final String password = editTextPassword.getText().toString();

        // TODO: Implement your own signup logic here.
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess(email, password);
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);


    }

    private void onSignupSuccess(String email, String password) {
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
                            Toast.makeText(SignUp.this,"Auth fail",Toast.LENGTH_LONG).show();
                        }
                        else  {
                            Toast.makeText(SignUp.this,"Successful!",Toast.LENGTH_LONG).show();

                            //login
                            //call function initUserData
                            //call function write: viet user name.

                            startActivity(new Intent(SignUp.this,Login.class));
                        }
                    }
                });
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
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

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
            editTextUserName.setError("at least 3 characters");
            valid = false;
        } else {
            editTextUserName.setError(null);
        }

        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        if(email.equals(user.getEmail().trim())) {
            editTextUserName.setError("Email already used! Want to login or recover your password?");
            valid = false;
        } else {
            editTextUserName.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("enter a valid email address");
            valid = false;
        } else {
            editTextEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            editTextPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            editTextPassword.setError(null);
        }

        if (!password.equals(confirmPassword)){
            editTextConfirmPassword.setError("Password and confirm password don't match");
            valid = false;
        } else {
            editTextConfirmPassword.setError(null);
        }

        return valid;
    }
}
