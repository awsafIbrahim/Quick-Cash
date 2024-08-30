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
import com.example.quickcash.objects.Employee;
import com.example.quickcash.objects.Employer;
import com.example.quickcash.utilities.EmployeeManager;
import com.example.quickcash.utilities.EmployerManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

/*
 * Citations
 * We used the video from youtube to learn about firebase.
 * Author:Codes Easy
 * URL:https://www.youtube.com/watch?v=QAKq8UBv4GI&t=412s&ab_channel=CodesEasy
 */

/**
 * Activity for user registration.
 */
public class SignUpActivity extends AppCompatActivity {
    EditText usernameID;
    EditText emailID;
    EditText passwordID;
    Button submitButton;
    FirebaseAuth auth;
    TextView textView;

    /**
     * Called when the activity is starting.
     */
    @Override
    public void onStart() {
        super.onStart();
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
        setContentView(R.layout.activity_sign_up);
        auth =FirebaseAuth.getInstance();
        setupLoginRedirect();
        setupSignUpButton();
    }

    private void setupLoginRedirect() {
        textView = findViewById(R.id.signUpLoginNow);
        textView.setOnClickListener(v -> navigateToActivity(LoginPageActivity.class));
    }

    private void setupSignUpButton() {
        initializeViews();
        setSignUpButtonClickListener();
    }

    private void initializeViews() {
        usernameID=findViewById(R.id.inputSignUpUsername);
        emailID=findViewById(R.id.inputSignUpEmail);
        passwordID=findViewById(R.id.inputSignUpPassword);
        submitButton=findViewById(R.id.btnSignUpSubmit);
    }

    private void setSignUpButtonClickListener() {
        submitButton.setOnClickListener(view -> {
            String username=String.valueOf(usernameID.getText());
            String email=String.valueOf(emailID.getText());
            String password=String.valueOf(passwordID.getText());

            if(TextUtils.isEmpty(username)){
                makeToastMessage("Username is required");
                return;
            }

            if(TextUtils.isEmpty(email)){
                makeToastMessage("Email is required");
                return;
            }

            if(TextUtils.isEmpty(password)){
                makeToastMessage("Password is required");
                return;
            }

            if(password.length() < 6 || !password.matches("[A-Za-z0-9]+")) {
                makeToastMessage("Password must contain at least 6 characters, Allowed characters – A-Za-z0-9");
                return;
            }

            authenticateNewUser(email, password);
        });
    }

    private void authenticateNewUser(String email, String password) {
        /*
         * Citations
         * We used this link  to learn how to read and write to the website
         * Author: Firebase Docs
         * URL: https://firebase.google.com/docs/auth/android/password-auth
         */
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        makeToastMessage("Account created");
                        addUserToDatabase();
                        navigateToActivity(LoginPageActivity.class);
                    }else {
                        Exception taskException = task.getException();
                        if (taskException instanceof FirebaseAuthUserCollisionException) {
                            makeToastMessage("Account with this email id already exist. Kindly click on login or try again with a different email id");
                        }else {
                            makeToastMessage("Authentication failed");
                        }
                    }
                });
    }

    private void addUserToDatabase() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String username = String.valueOf(usernameID.getText());
        String email = String.valueOf(emailID.getText());

        Employee employee = new Employee(id, username, email);
        Employer employer = new Employer(id, username, email);

        EmployeeManager employeeManager = new EmployeeManager();
        EmployerManager employerManager = new EmployerManager();

        employeeManager.addEmployeeToDatabase(employee);
        employerManager.addEmployerToDatabase(employer);
    }

    private void navigateToActivity(Class<?> destinationClass) {
        Intent intent = new Intent(SignUpActivity.this, destinationClass);
        startActivity(intent);
        finish();
    }

    private void makeToastMessage(String message){
        TextView textViewForEspressoTests = findViewById(R.id.signUpHiddenToastTestHelper);
        textViewForEspressoTests.setText(message);
        Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
    }

}
