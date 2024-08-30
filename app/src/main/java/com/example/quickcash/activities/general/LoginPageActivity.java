package com.example.quickcash.activities.general;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Activity for user login.
 */
public class LoginPageActivity extends AppCompatActivity {
    EditText emailID;
    EditText passwordID;
    Button logintButton;
    FirebaseAuth auth;
    TextView textView;

    /**
     * Called when the activity is starting.
     */
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        validateUser(currentUser);
    }

    /**
     * Called when the activity is created.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        auth = FirebaseAuth.getInstance();
        setupSignUpRedirect();
        setupLoginButton();
    }

    private void validateUser(FirebaseUser currentUser) {
        if(currentUser != null){
            navigateToActivity(MapsActivity.class);
        }
    }

    private void setupSignUpRedirect() {
        textView = findViewById(R.id.loginPageRegisterNow);
        textView.setOnClickListener(view -> navigateToActivity(SignUpActivity.class));
    }

    private void setupLoginButton() {
        initializeViews();
        setLoginButtonClickListener();
    }

    private void initializeViews() {
        emailID = findViewById(R.id.inputLoginPageEmail);
        passwordID = findViewById(R.id.inputLoginPagePassword);
        logintButton = findViewById(R.id.btnLoginPageSubmit);
    }

    private void setLoginButtonClickListener() {
        logintButton.setOnClickListener(view -> {
            String email = String.valueOf(emailID.getText());
            String password = String.valueOf(passwordID.getText());

            if (TextUtils.isEmpty(email)) {
                makeToastMessage("Email is required");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                makeToastMessage("Password is required");
                return;
            }

            authenticateUser(email, password);
        });
    }

    private void authenticateUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        makeToastMessage("login successful");
                        navigateToActivity(MapsActivity.class);
                    }else {
                        makeToastMessage("Invalid email/password. Please try again");
                    }
                });
    }

    private void navigateToActivity(Class<?> destinationClass) {
        Intent intent = new Intent(LoginPageActivity.this, destinationClass);
        startActivity(intent);
        finish();
    }

    private void makeToastMessage(String message){
        TextView textViewForEspressoTests = findViewById(R.id.loginHiddenToastTestHelper);
        textViewForEspressoTests.setText(message);
        Toast.makeText(LoginPageActivity.this, message, Toast.LENGTH_SHORT).show();
    }

}
