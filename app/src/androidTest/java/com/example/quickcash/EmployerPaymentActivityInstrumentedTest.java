package com.example.quickcash;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.core.app.ActivityScenario;

import com.example.quickcash.R;
import com.example.quickcash.activities.employer.EmployerPaymentActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EmployerPaymentActivityInstrumentedTest {

    @Test
    public void testMakePaymentButtonIsDisplayed() {
        ActivityScenario.launch(EmployerPaymentActivity.class);
        onView(withId(R.id.makePayment)).check(matches(isDisplayed()));
    }

    @Test
    public void testPaymentStatusTextViewIsDisplayed() {
        ActivityScenario.launch(EmployerPaymentActivity.class);
        onView(withId(R.id.paymentStatus)).check(matches(isDisplayed()));
    }

    @Test
    public void testDetailsTextViewIsDisplayed() {
        ActivityScenario.launch(EmployerPaymentActivity.class);
        onView(withId(R.id.details)).check(matches(isDisplayed()));
    }


}
