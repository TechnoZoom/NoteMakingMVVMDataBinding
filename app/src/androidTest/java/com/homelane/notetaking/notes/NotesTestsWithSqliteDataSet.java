package com.homelane.notetaking.notes;


import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.homelane.notetaking.R;
import com.homelane.notetaking.TestMyApplication;
import com.homelane.notetaking.data.source.NotesRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.homelane.notetaking.TestUtils.withItemText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class NotesTestsWithSqliteDataSet {

    @Rule
    public ActivityTestRule<AllNotesActivity> mActivityTestRule = new ActivityTestRule<>(AllNotesActivity.class,true,false);

    private final static String TITLE1 = "TITLE1";

    private final static String DESCRIPTION = "DESCR";

    private final static String TITLE2 = "TITLE2";

    @Inject
    NotesRepository notesRepository;

    @Before
    public void setUp() {
        TestMyApplication testMyApplication = (TestMyApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        testMyApplication.getTestAppComponent().inject(this);
        notesRepository.deleteAllNotes();
        mActivityTestRule.launchActivity(null);
    }

    @Test
    public void editNote() throws Exception {
        // First add a task
        createTask(TITLE1, DESCRIPTION);

        // Click on the task on the list
        onView(withText(TITLE1)).perform(click());

        String editTaskTitle = TITLE2;
        String editTaskDescription = "New Description";

        // Edit task title and description
        onView(withId(R.id.note_title_edit_text))
                .perform(replaceText(editTaskTitle), closeSoftKeyboard()); // Type new task title
        onView(withId(R.id.note_desc_edit_text)).perform(replaceText(editTaskDescription),
                closeSoftKeyboard()); // Type new task description and close the keyboard

        // Save the task
        onView(withId(R.id.save_button)).perform(click());

        // Verify task is displayed on screen in the task list.
        onView(withItemText(editTaskTitle)).check(matches(isDisplayed()));

        // Verify previous task is not displayed
        onView(withItemText(TITLE1)).check(doesNotExist());
    }


    @Test
    public void clickAddTaskButton_opensAddNoteUi() {
        // Click on the add task button
        onView(withId(R.id.fab)).perform(click());

        // Check if the add task screen is displayed
        onView(withId(R.id.note_title_edit_text)).check(matches(isDisplayed()));
        onView(withId(R.id.note_desc_edit_text)).check(matches(isDisplayed()));
        onView(withId(R.id.save_button)).check(matches(isDisplayed()));
    }

    @Test
    public void checkPrefillingOfNotes() {
        // Click on the add task button
        String title = "Title to test pre-filling";
        String description = "Description to test pre-filling";
        createTask(title,description);
        onView(withItemText(title)).perform(click());
        onView(withId(R.id.note_title_edit_text)).check(matches(withText(title)));
        onView(withId(R.id.note_desc_edit_text)).check(matches(withText(description)));
    }


    private void createTask(String title, String description) {
        // Click on the add task button
        onView(withId(R.id.fab)).perform(click());

        // Add task title and description
        onView(withId(R.id.note_title_edit_text)).perform(typeText(title),
                closeSoftKeyboard()); // Type new task title
        onView(withId(R.id.note_desc_edit_text)).perform(typeText(description),
                closeSoftKeyboard()); // Type new task description and close the keyboard

        // Save the task
        onView(withId(R.id.save_button)).perform(click());
    }

}
