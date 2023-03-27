package com.example.insightsshare;

import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MainActivityUiTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityActivityScenarioRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @After
    public void tearDown() throws Exception {
        activityActivityScenarioRule.getScenario().close();
    }

    @Test
    public void signIn_fail_EmailRequired(){
        String password = "123456";

        Espresso.onView(ViewMatchers.withId(R.id.inputPassword2))
                .perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.anmeldeButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.inputEmail2))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Email is required")));
    }
}
