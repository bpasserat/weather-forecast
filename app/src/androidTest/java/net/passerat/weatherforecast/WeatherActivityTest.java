package net.passerat.weatherforecast;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import net.passerat.weatherforecast.data.CityElement;
import net.passerat.weatherforecast.data.WeatherElement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class WeatherActivityTest {

    @Rule
    public ActivityTestRule<WeatherActivity> mActivityRule = new ActivityTestRule<>(
            WeatherActivity.class);

     @Test
    public void checkRecyclerView() throws Exception {
        onView(withId(R.id.recyclerView))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkToolbar() throws Exception {
        onView(withId(R.id.toolbar))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkToolbarMenu_action() throws Exception {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.action_update))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkToolbarMenu_city() throws Exception {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.action_settings))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkClickSettings() throws Exception {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.action_settings)).perform(click());
    }

    @Test
    public void checkClickSettingsShowDialog() throws Exception {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.action_settings)).perform(click());
        onView(withText(R.string.configure_city)).check(matches(withText(R.string.configure_city)));
        onView(withText(R.string.ok)).perform(click());

    }

}


