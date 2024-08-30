package com.example.quickcash;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quickcash.activities.employee.EmployeeLandingActivity;
import com.example.quickcash.activities.employee.EmployeeVisualizeJobHistoryActivity;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EmployeeVisualizeJobHistoryActivityInstrumentedTest {
    private ActivityScenario<EmployeeVisualizeJobHistoryActivity> scenario;
    private String testUserEmail = "abc123@gmail.com";
    private String testUserPassword = "mypassword";

    @Before
    public void setup() {
        Intents.init();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        try {
            Tasks.await(auth.signInWithEmailAndPassword(testUserEmail, testUserPassword));
        } catch (Exception e) {
            e.printStackTrace();
        }
        scenario = ActivityScenario.launch(EmployeeVisualizeJobHistoryActivity.class);
    }

    @After
    public void tearDown() {
        FirebaseAuth.getInstance().signOut();
        scenario.close();
        Intents.release();
    }

    @Test
    public void toolbarDisplayed() {
        onView(withId(R.id.userReportHeader)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void backButtonDisplayed() {
        onView(withId(R.id.userReportBackButton)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void recyclerViewDisplayed() {
        onView(withId(R.id.userRatingRecyclerView)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void userRatingBarDisplayed() {
        onView(withId(R.id.userRatingBar)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void userRatingTextViewDisplayed() {
        onView(withId(R.id.userRatingTextView)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void incomeChartDisplayed() {
        onView(withId(R.id.paymentSummaryLineChart)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void incomeDailyTotalTextViewDisplayed() {
        onView(withId(R.id.paymentSummaryLeftText)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void incomeCompleteTotalTextViewDisplayed() {
        onView(withId(R.id.paymentSummaryRightText)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void backButtonNavigatesToEmployeeLandingActivity() {
        Espresso.onView(withId(R.id.userReportBackButton)).perform(click());
        intended(hasComponent(EmployeeLandingActivity.class.getName()));
    }

}
