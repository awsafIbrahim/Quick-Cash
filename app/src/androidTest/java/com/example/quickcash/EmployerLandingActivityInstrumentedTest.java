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

import com.example.quickcash.activities.employer.EmployerLandingActivity;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EmployerLandingActivityInstrumentedTest {

    private ActivityScenario<EmployerLandingActivity> scenario;
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
        scenario = ActivityScenario.launch(EmployerLandingActivity.class);
    }

    @After
    public void tearDown() {
        FirebaseAuth.getInstance().signOut();
        scenario.close();
        Intents.release();
    }

    @Test
    public void employerToolbarDisplayed() {
        onView(withId(R.id.employerToolbar)).check(matches(isDisplayed()));
    }

    @Test
    public void btnEmployerToggleEmploymentViewDisplayed() {
        onView(withId(R.id.employerToggleEmploymentView)).check(matches(isDisplayed()));
    }

    @Test
    public void btnEmployerAddJobPostingDisplayed() {
        onView(withId(R.id.btnEmployerAddJobPosting)).check(matches(isDisplayed()));
    }

    @Test
    public void btnEmployerJobHistoryDisplayed() {
        onView(withId(R.id.employerJobHistory)).check(matches(isDisplayed()));
    }

    @Test
    public void btnEmployerViewJobPostingDisplayed() {
        onView(withId(R.id.btnEmployerViewJobPostings)).check(matches(isDisplayed()));
    }

    @Test
    public void btnEmployerViewPreferredEmployeesDisplayed() {
        onView(withId(R.id.btnEmployerViewPreferredEmployees)).check(matches(isDisplayed()));
    }

    @Test
    public void btnEmployerAcceptedApplicationsDisplayed() {
        onView(withId(R.id.btnEmployerAcceptedApplications)).check(matches(isDisplayed()));
    }

    @Test
    public void btnEmployerReportDisplayed() {
        onView(withId(R.id.btnEmployerReport)).check(matches(isDisplayed()));
    }

    @Test
    public void testToggleToEmployeeLandingActivity() {
        Espresso.onView(ViewMatchers.withId(R.id.employerToggleEmploymentView))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Switched to Employee"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
    @Test
    public void testEmployerActivityFinishesAfterToggle() {
        Espresso.onView(ViewMatchers.withId(R.id.employerToggleEmploymentView))
                .perform(ViewActions.click());
        scenario.moveToState(Lifecycle.State.DESTROYED);
    }
}