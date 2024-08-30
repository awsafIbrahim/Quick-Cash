package com.example.quickcash;


import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quickcash.activities.employee.EmployeeViewJobPostingsActivity;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EmployeeViewJobPostingActivityInstrumentedTest {

    private ActivityScenario<EmployeeViewJobPostingsActivity> scenario;
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
        scenario = ActivityScenario.launch(EmployeeViewJobPostingsActivity.class);
    }

    @After
    public void tearDown() {
        FirebaseAuth.getInstance().signOut();
        scenario.close();
        Intents.release();
    }
    @Test
    public void testViewJobsBackButton() {
        Espresso.onView(ViewMatchers.withId(R.id.employeeViewJobsBackButton))
                .perform(ViewActions.click());
    }
    @Test
    public void testEmployeeViewJobsFinishesAfterback (){
        Espresso.onView(ViewMatchers.withId(R.id.employeeViewJobsBackButton))
                .perform(ViewActions.click());
        scenario.moveToState(Lifecycle.State.DESTROYED);
    }
    @Test
    public void testViewDisplayJobs() {
        Espresso.onView(ViewMatchers.withId(R.id.employeeViewJobsRecyclerView))
                .perform(ViewActions.click());
    }

}
