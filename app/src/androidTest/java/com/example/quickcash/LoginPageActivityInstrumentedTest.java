package com.example.quickcash;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quickcash.activities.general.LoginPageActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginPageActivityInstrumentedTest {

    private ActivityScenario<LoginPageActivity> scenario;
    private String testUserEmail = "abc123@gmail.com";
    private String testUserPassword = "mypassword";
    private String badEmail = "a";
    private String badPassword = "b";

    @Before
    public void setup() {
        scenario = ActivityScenario.launch(LoginPageActivity.class);
    }

    @After
    public void tearDown() {
        scenario.close();
    }

    @Test
    public void testEmptyEmailField() {
        Espresso.onView(withId(R.id.btnLoginPageSubmit))
                .perform(ViewActions.click());
        checkHiddenTextView("Email is required");
    }

    @Test
    public void testEmptyPasswordField() {
        Espresso.onView(withId(R.id.inputLoginPageEmail))
                .perform(ViewActions.typeText(testUserEmail));
        Espresso.onView(withId(R.id.btnLoginPageSubmit))
                .perform(ViewActions.click())
                .perform(ViewActions.closeSoftKeyboard());
        checkHiddenTextView("Password is required");
    }

    @Test
    public void testBadEmail() {
        Espresso.onView(withId(R.id.inputLoginPageEmail))
                .perform(ViewActions.typeText(badEmail));
        Espresso.onView(withId(R.id.inputLoginPagePassword))
                .perform(ViewActions.typeText(testUserPassword));
        Espresso.onView(withId(R.id.btnLoginPageSubmit))
                .perform(ViewActions.click())
                .perform(ViewActions.closeSoftKeyboard());
        checkHiddenTextView("Invalid email/password. Please try again");
    }

    @Test
    public void testBadPassword() {
        Espresso.onView(withId(R.id.inputLoginPageEmail))
                .perform(ViewActions.typeText(testUserEmail));
        Espresso.onView(withId(R.id.inputLoginPagePassword))
                .perform(ViewActions.typeText(badPassword));
        Espresso.onView(withId(R.id.btnLoginPageSubmit))
                .perform(ViewActions.click())
                .perform(ViewActions.closeSoftKeyboard());
        checkHiddenTextView("Invalid email/password. Please try again");
    }

    private void checkHiddenTextView(String expectedMessage) {
        scenario.onActivity(activity -> {
            TextView hiddenTextView = activity.findViewById(R.id.loginHiddenToastTestHelper);
            assertEquals("Expected message", expectedMessage, hiddenTextView.getText().toString());
        });
    }
}