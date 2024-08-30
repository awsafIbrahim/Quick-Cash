package com.example.quickcash;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static org.hamcrest.core.AllOf.allOf;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;

import com.example.quickcash.activities.employee.EmployeeLandingActivity;
import com.example.quickcash.activities.employee.EmployeeSearchJobsActivity;

import org.junit.Rule;
import org.junit.Test;

public class SearchJobInstrumentedTest {

    @Rule
    public IntentsTestRule<EmployeeSearchJobsActivity> intentsTestRule = new IntentsTestRule<>(EmployeeSearchJobsActivity.class);


    @Test
    public void testBackButtonNavigatesToEmployeeLandingActivity() {

        Espresso.onView(ViewMatchers.withId(R.id.backButoton)).perform(ViewActions.click());

        intended(
                allOf(
                        toPackage("com.example.quickcash"),
                        hasComponent(EmployeeLandingActivity.class.getName())
                )
        );
    }
}
