package com.example.quickcash;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quickcash.activities.employee.EmployeeHistoryActivity;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EmployeeJobHistoryActivityInstrumentedTest {
    private ActivityScenario<EmployeeHistoryActivity> scenario;
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
        scenario = ActivityScenario.launch(EmployeeHistoryActivity.class);
    }
    @After
    public void tearDown() {
        FirebaseAuth.getInstance().signOut();
        scenario.close();
        Intents.release();
    }

    @Test
    public void employeeJobHistoryToolbarDisplayed() {
        onView(withId(R.id.employeeToolbar)).check(matches(isDisplayed()));
    }

    @Test
    public void btnEmployeeHistoryAppliedJobsDisplayed() {
        onView(withId(R.id.appliedJobsButton)).check(matches(isDisplayed()));
    }

    @Test
    public void btnEmployeeJobHistoryToggleEmploymentViewDisplayed() {
        onView(withId(R.id.employeeAppliedJobsBackButton)).check(matches(isDisplayed()));
    }

    @Test
    public void btnEmployeeHistoryAcceptedJobsDisplayed() {
        onView(withId(R.id.acceptedJobsButton)).check(matches(isDisplayed()));
    }
}
