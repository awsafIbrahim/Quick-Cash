package com.example.quickcash;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quickcash.activities.general.SignUpActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class SignUpActivityActivityInstrumentedTest {
    private ActivityScenario<SignUpActivity> scenario;
    private String testUserUsername = "John Doe";
    private String testUserEmail = "abc123@gmail.com";
    private String testUserPassword = "mypassword";
    private String badEmail = "a";
    private String badPassword = "b";

    @Before
    public void setup() {
        scenario = ActivityScenario.launch(SignUpActivity.class);
    }

    @After
    public void tearDown() {
        scenario.close();
    }

    @Test
    public void testEmptyUsernameField() {
        Espresso.onView(withId(R.id.btnSignUpSubmit))
                .perform((ViewActions.click()));
        checkHiddenTextView("Username is required");
    }

    @Test
    public void testEmptyEmailField() {
        Espresso.onView(withId(R.id.inputSignUpUsername))
                        .perform(ViewActions.typeText(testUserUsername));
        closeSoftKeyboard();

        Espresso.onView(withId(R.id.btnSignUpSubmit))
                .perform(ViewActions.click());
        checkHiddenTextView("Email is required");
    }

    @Test
    public void testEmptyPasswordField() {
        Espresso.onView(withId(R.id.inputSignUpUsername))
                .perform(ViewActions.typeText(testUserUsername));
        Espresso.onView(withId(R.id.inputSignUpEmail))
                .perform(ViewActions.typeText(testUserEmail));
        closeSoftKeyboard();

        Espresso.onView(withId(R.id.btnSignUpSubmit))
                .perform(ViewActions.click());
        checkHiddenTextView("Password is required");
    }

    @Test
    public void testExistingEmail() {
        Espresso.onView(withId(R.id.inputSignUpUsername))
                .perform(ViewActions.typeText(testUserUsername));
        Espresso.onView(withId(R.id.inputSignUpEmail))
                .perform(ViewActions.typeText(testUserEmail));
        Espresso.onView(withId(R.id.inputSignUpPassword))
                .perform(ViewActions.typeText(testUserPassword));
        closeSoftKeyboard();

        Espresso.onView(withId(R.id.btnSignUpSubmit))
                .perform(ViewActions.click());
        allowTimeForResponse();
        checkHiddenTextView("Account with this email id already exist. Kindly click on login or try again with a different email id");
    }

    @Test
    public void testBadEmail() {
        Espresso.onView(withId(R.id.inputSignUpUsername))
                .perform(ViewActions.typeText(testUserUsername));
        Espresso.onView(withId(R.id.inputSignUpEmail))
                .perform(ViewActions.typeText(badEmail));
        Espresso.onView(withId(R.id.inputSignUpPassword))
                .perform(ViewActions.typeText(testUserPassword));
        closeSoftKeyboard();

        Espresso.onView(withId(R.id.btnSignUpSubmit))
                .perform(ViewActions.click());
        allowTimeForResponse();
        checkHiddenTextView("Authentication failed");
    }

    @Test
    public void testBadPassword() {
        Espresso.onView(withId(R.id.inputSignUpUsername))
                .perform(ViewActions.typeText(testUserUsername));
        Espresso.onView(withId(R.id.inputSignUpEmail))
                .perform(ViewActions.typeText(testUserEmail));
        Espresso.onView(withId(R.id.inputSignUpPassword))
                .perform(ViewActions.typeText(badPassword));
        closeSoftKeyboard();

        Espresso.onView(withId(R.id.btnSignUpSubmit))
                .perform(ViewActions.click());
        checkHiddenTextView("Password must contain at least 6 characters, Allowed characters – A-Za-z0-9");
    }

    private void checkHiddenTextView(String expectedMessage) {
        scenario.onActivity(activity -> {
            TextView hiddenTextView = activity.findViewById(R.id.signUpHiddenToastTestHelper);
            assertEquals("Expected message", expectedMessage, hiddenTextView.getText().toString());
        });
    }

    private void allowTimeForResponse() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
