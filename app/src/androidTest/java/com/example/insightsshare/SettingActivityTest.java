package com.example.insightsshare;

import static org.junit.Assert.*;

import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SettingActivityTest {

    @Rule
    public ActivityScenarioRule<SettingActivity> activityActivityScenarioRule = new ActivityScenarioRule<SettingActivity>(SettingActivity.class);

    private View decorView;

    @Before
    public void setUp() throws Exception {
        activityActivityScenarioRule.getScenario().onActivity(new ActivityScenario.ActivityAction<SettingActivity>() {
            @Override
            public void perform(SettingActivity activity) {
                decorView = activity.getWindow().getDecorView();
            }
        });
    }

    @After
    public void tearDown() throws Exception {
        activityActivityScenarioRule.getScenario().close();
    }

    @Test
    public void logout() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    }
}