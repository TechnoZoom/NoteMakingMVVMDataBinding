package com.homelane.notetaking.orders;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.homelane.notetaking.R;
import com.homelane.notetaking.TestMyApplication;
import com.homelane.notetaking.data.Note;
import com.homelane.notetaking.data.source.mock.FakeNotesSource;
import com.homelane.notetaking.notes.AllNotesActivity;
import com.homelane.notetaking.orderlifecycle.AllOrdersActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by kapilbakshi on 14/08/17.
 */

@RunWith(AndroidJUnit4.class)

public class AllOrdersTest {

    @Rule
    public ActivityTestRule<AllOrdersActivity> mActivityTestRule = new ActivityTestRule<AllOrdersActivity>(AllOrdersActivity.class, true, false);

    @Before
    public void setUp() {
        mActivityTestRule.launchActivity(null);
    }

    @Test
    public void onExceptionError_checkIfSnacBarIsDispalyed() {
        String text = mActivityTestRule.getActivity().getString(R.string.some_error_ocurred);
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(text)))
                .check(matches(isDisplayed()));
    }
}
