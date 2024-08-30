package com.example.quickcash;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quickcash.activities.employee.EmployeeAppliedJobsActivity;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EmployeeAppliedJobsaActivityInstrumentedTest {

    private ActivityScenario<EmployeeAppliedJobsActivity> scenario;
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
        scenario = ActivityScenario.launch(EmployeeAppliedJobsActivity.class);
    }

    @After
    public void tearDown() {
        FirebaseAuth.getInstance().signOut();
        scenario.close();
        Intents.release();
    }

    @Test
    public void testAppliedBackButton() {

        Espresso.onView(ViewMatchers.withId(R.id.employeeAppliedJobsBackButton))
                .perform(ViewActions.click());
        scenario.moveToState(Lifecycle.State.DESTROYED);
    }
    @Test
    public void testEmployeeAppliedJobsFinishesAfterback (){
        Espresso.onView(ViewMatchers.withId(R.id.employeeAppliedJobsBackButton))
                .perform(ViewActions.click());
        scenario.moveToState(Lifecycle.State.DESTROYED);
    }
    @Test
    public void testAppliedJobs() {

        Espresso.onView(ViewMatchers.withId(R.id.employeeAppliedJobsRecyclerView))
                .perform(ViewActions.click());
    }

}