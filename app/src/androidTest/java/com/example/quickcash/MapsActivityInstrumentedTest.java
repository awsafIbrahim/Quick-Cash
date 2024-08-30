package com.example.quickcash;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import com.example.quickcash.activities.employer.EmployerLandingActivity;
import com.example.quickcash.activities.general.MapsActivity;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MapsActivityInstrumentedTest {

    private ActivityScenario<MapsActivity> scenario;
    private String testUserEmail = "abc123@gmail.com";
    private String testUserPassword = "mypassword";

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION);

    @Before
    public void setUp() {
        Intents.init();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        try {
            Tasks.await(auth.signInWithEmailAndPassword(testUserEmail, testUserPassword));
        } catch (Exception e) {
            e.printStackTrace();
        }
        scenario = ActivityScenario.launch(MapsActivity.class);
    }

    @After
    public void tearDown() {
        FirebaseAuth.getInstance().signOut();
        Intents.release();
        scenario.close();
    }

    @Test
    public void testOkButtonNavigatesToEmployerLandingActivity() {
        onView(withId(R.id.okButton)).perform(click());
        Intents.intended(hasComponent(EmployerLandingActivity.class.getName()));
    }

    @Test
    public void testMapInitialization() {
        allowTimeForResponse();
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }

    private void allowTimeForResponse() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

