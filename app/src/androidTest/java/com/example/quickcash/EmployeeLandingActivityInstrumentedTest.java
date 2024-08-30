package com.example.quickcash;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quickcash.activities.employee.EmployeeLandingActivity;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EmployeeLandingActivityInstrumentedTest {
    private ActivityScenario<EmployeeLandingActivity> scenario;
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
        scenario = ActivityScenario.launch(EmployeeLandingActivity.class);
    }

    @After
    public void tearDown() {
        FirebaseAuth.getInstance().signOut();
        scenario.close();
        Intents.release();
    }

    @Test
    public void employeeToolbarDisplayed() {
        onView(withId(R.id.employeeToolbar)).check(matches(isDisplayed()));
    }

    @Test
    public void btnEmployeeToggleEmploymentViewDisplayed() {
        onView(withId(R.id.employeeToggleEmploymentView)).check(matches(isDisplayed()));
    }

    @Test
    public void btnEmployeeAvailableJobsDisplayed() {
        onView(withId(R.id.btnEmployeeAvailableJobs)).check(matches(isDisplayed()));
    }

    @Test
    public void btnEmployeeJobHistoryDisplayed() {
        onView(withId(R.id.employeeJobHistory)).check(matches(isDisplayed()));
    }

    @Test
    public void btnEmployeeAppliedJobsDisplayed() {
        onView(withId(R.id.btnEmployeeAppliedJobs)).check(matches(isDisplayed()));
    }

    @Test
    public void btnEmployeeViewAcceptedJobsDisplayed() {
        onView(withId(R.id.btnEmployeeAcceptedJobs)).check(matches(isDisplayed()));
    }

    @Test
    public void btnEmployeeReportDisplayed() {
        onView(withId(R.id.btnEmployeeReport)).check(matches(isDisplayed()));
    }

    @Test
    public void testToggleToEmployerLandingActivity() {
        Espresso.onView(ViewMatchers.withId(R.id.employeeToggleEmploymentView))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Switched to Employer"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
    @Test
    public void testEmployeeActivityFinishesAfterToggle() {
        Espresso.onView(ViewMatchers.withId(R.id.employeeToggleEmploymentView))
                .perform(ViewActions.click());
        scenario.moveToState(Lifecycle.State.DESTROYED);
    }
}