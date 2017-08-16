package com.homelane.notetaking.support;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.homelane.notetaking.orderlifecycle.CancelledOrderActivity;

import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.shadows.ShadowProgressDialog;

import static com.homelane.notetaking.support.ResourceLocator.getString;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNotNull;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by Mohsen on 21/10/2016.
 */

public class Assert {

    public static void assertViewIsVisible(View view) {
        assertNotNull(view);
        assertThat(view.getVisibility(), equalTo(View.VISIBLE));
    }

    public static void assertViewIsNotVisible(View view) {
        assertNotNull(view);
        assertThat(view.getVisibility(), not(equalTo(View.VISIBLE)));
    }

    public static void assertAlertDialogIsShown(@StringRes int title, @StringRes int message) {
        AlertDialog alert = ShadowAlertDialog.getLatestAlertDialog();
        ShadowAlertDialog shadowAlertDialog = shadowOf(alert);
        assertThat(shadowAlertDialog.getTitle().toString(), equalTo(getString(title)));
        assertThat(shadowAlertDialog.getMessage().toString(), equalTo(getString(message)));
    }

    public static void assertProgressDialogIsShown(@StringRes int title) {
        ProgressDialog alert = (ProgressDialog) ShadowProgressDialog.getLatestAlertDialog();
        ShadowProgressDialog shadowProgressDialog = shadowOf(alert);
        assertThat(shadowProgressDialog.getTitle().toString(), equalTo(getString(title)));
    }

    public static void assertSnackbarIsShown(@StringRes int message) {
        Snackbar snackbar = ShadowSnackbar.getLatestSnackbar();
        ShadowSnackbar shadowSnackbar = ShadowSnackbar.shadowOf(snackbar);
        assertThat(shadowSnackbar.text, equalTo(getString(message)));

    }

    public static void assertNextActivity(Activity currentActivity, Class nextActivityClass) {
        Intent startedIntent = shadowOf(currentActivity).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(nextActivityClass, shadowIntent.getIntentClass());

    }

}