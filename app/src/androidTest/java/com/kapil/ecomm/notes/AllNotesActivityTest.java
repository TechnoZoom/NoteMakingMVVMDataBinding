package com.kapil.ecomm.notes;


import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.kapil.ecomm.R;
import com.kapil.ecomm.TestMyApplication;
import com.kapil.ecomm.data.source.NotesRepository;
import com.kapil.ecomm.data.source.local.entities.Note;
import com.kapil.ecomm.data.source.mock.FakeNotesSource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AllNotesActivityTest {

    @Inject
    NotesRepository notesRepository;

    @Rule
    public ActivityTestRule<AllNotesActivity> mActivityTestRule = new ActivityTestRule<AllNotesActivity>(AllNotesActivity.class, true, false);

    @Before
    public void setUp() {
        TestMyApplication testMyApplication = (TestMyApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        testMyApplication.getTestAppComponent().inject(this);
        Note note = new Note("Mock Waaala Note", "Mocking Successfull",1502363598964L);
        FakeNotesSource.getInstance().addNotes(note);
        mActivityTestRule.launchActivity(null);
    }

    @Test
    public void allNotesActivityTest() {

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab),
                        withParent(allOf(withId(R.id.main_cord),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.note_title_edit_text), isDisplayed()));
        appCompatEditText.perform(replaceText("Mock Note 1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.note_desc_edit_text), isDisplayed()));
        appCompatEditText2.perform(replaceText("gdgsg"), closeSoftKeyboard());


        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.save_button), withText("Save"),
                        withParent(allOf(withId(R.id.cont_ad_ed_note),
                                withParent(withId(R.id.coord)))),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.fab),
                        withParent(allOf(withId(R.id.main_cord),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        pressBack();

    }

}
