package com.example.quickcash;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quickcash.activities.general.ManualSearchActivity;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ManualSearchActivityInstrumentedTest {
    private ActivityScenario<ManualSearchActivity> scenario;
    private String testUserEmail = "abc123@gmail.com";
    private String testUserPassword = "mypassword";

    @Before
    public void setUp() {
        Intents.init();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        try {
            Tasks.await(auth.signInWithEmailAndPassword(testUserEmail, testUserPassword));
        } catch (Exception e) {
            e.printStackTrace();
        }
        scenario = ActivityScenario.launch(ManualSearchActivity.class);
    }

    @After
    public void tearDown() {
        FirebaseAuth.getInstance().signOut();
        Intents.release();
        scenario.close();
    }

    @Test
    public void checkSearchBarDisplayed() {
        onView(withId(R.id.idSearchBar)).check(matches(isDisplayed()));
    }

    @Test
    public void checkMapIsDisplayed() {
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }

    @Test
    public void checkOkButtonIsDisplayed() {
        onView(withId(R.id.okButton)).check(matches(isDisplayed()));
    }

}
