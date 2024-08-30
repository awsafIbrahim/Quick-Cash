package com.example.quickcash;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quickcash.activities.employer.EmployerAddJobPostingActivity;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EmployerAddJobPostingActivityInstrumentedTest {
    private ActivityScenario<EmployerAddJobPostingActivity> activityScenario;
    private String testUserEmail = "abc123@gmail.com";
    private String testUserPassword = "mypassword";

    @Before
    public void setUp() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        try {
            Tasks.await(auth.signInWithEmailAndPassword(testUserEmail, testUserPassword));
        } catch (Exception e) {
            e.printStackTrace();
        }
        activityScenario = ActivityScenario.launch(EmployerAddJobPostingActivity.class);
    }

    @After
    public void tearDown() {
        FirebaseAuth.getInstance().signOut();
        activityScenario.close();
    }

    @Test
    public void jobTitleTextDisplayed() {
        onView(withId(R.id.jobTitle)).check(matches(isDisplayed()));
    }

    @Test
    public void jobTitleInputFieldDisplayed() {
        onView(withId(R.id.writeJob)).check(matches(isDisplayed()));
    }

    @Test
    public void datePickerButtonDisplayed() {
        onView(withId(R.id.datepickerbtn)).check(matches(isDisplayed()));
    }

    @Test
    public void datePickerTextDisplayed() {
        onView(withId(R.id.datePicker)).check(matches(isDisplayed()));
    }

    @Test
    public void jobDurationInputFieldDisplayed() {
        onView(withId(R.id.durationNumber)).check(matches(isDisplayed()));
    }

    @Test
    public void jobDurationSpinnerDisplayed() {
        onView(withId(R.id.durationSpinner)).check(matches(isDisplayed()));
    }

    @Test
    public void jobUrgencyTextDisplayed() {
        onView(withId(R.id.urgencyTextView)).check(matches(isDisplayed()));
    }

    @Test
    public void jobUrgencySpinnerDisplayed() {
        onView(withId(R.id.urgencySpinner)).check(matches(isDisplayed()));
    }

    @Test
    public void jobSalaryTextDisplayed() {
        onView(withId(R.id.salaryTextView)).check(matches(isDisplayed()));
    }

    @Test
    public void jobSalaryInputFieldDisplayed() {
        onView(withId(R.id.salaryNumber)).check(matches(isDisplayed()));
    }

    @Test
    public void submitJobButtonDisplayed() {
        onView(withId(R.id.submitJobBtn)).check(matches(isDisplayed()));
    }

}
