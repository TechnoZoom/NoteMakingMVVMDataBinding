package com.kapil.ecomm.orders;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.kapil.ecomm.R;
import com.kapil.ecomm.constants.OrderLifeCycleConstants;
import com.kapil.ecomm.custom.RecyclerViewItemCountAssertion;
import com.kapil.ecomm.data.source.mock.FakeOrderDataSource;
import com.kapil.ecomm.orderlifecycle.AllOrdersActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.kapil.ecomm.Constants.SERVER_BUSY_MESSAGE;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by kapilbakshi on 14/08/17.
 */

@RunWith(AndroidJUnit4.class)

public class AllOrdersTest {

    @Rule
    public ActivityTestRule<AllOrdersActivity> mActivityTestRule = new ActivityTestRule<AllOrdersActivity>(AllOrdersActivity.class, true, false);

    @Test
    public void onExceptionError_checkIfSnacBarIsDispalyed() {
        FakeOrderDataSource.getInstance().create_Exception_Error_Observable("Internet Security Exception");
        reloadOrdersActivity();
        String text = mActivityTestRule.getActivity().getString(R.string.some_error_ocurred);
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(text)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void onCancelledOrderClick_checkIfCancelledOrderPageIsOpened() {
        FakeOrderDataSource.getInstance().createOrdersObservable(OrderLifeCycleConstants.ORDER_STATUSES_ARRAY);
        reloadOrdersActivity();
        onView(withText(OrderLifeCycleConstants.STATUS_ORDER_CANCELLED)).perform(click());
        onView(withId(R.id.order_cancelled_text_view)).check(matches(isDisplayed()));
    }


    @Test
    public void onOrdersLoading_checkIfOrderCountIsCorrect() {
        FakeOrderDataSource.getInstance().createOrdersObservable(OrderLifeCycleConstants.ORDER_STATUSES_ARRAY);
        reloadOrdersActivity();
        onView(withId(R.id.orders_recycler_view)).check(new RecyclerViewItemCountAssertion(3));
    }

    @Test
    public void onReceivedOrderClick_checkIfReceivedOrderPageIsOpened() {
        FakeOrderDataSource.getInstance().createOrdersObservable(OrderLifeCycleConstants.STATUS_ORDER_OUT_FOR_DELIVERY,
                OrderLifeCycleConstants.STATUS_ORDER_RECEIVED,OrderLifeCycleConstants.STATUS_ORDER_CANCELLED);
        reloadOrdersActivity();
        onView(withText(OrderLifeCycleConstants.STATUS_ORDER_RECEIVED)).perform(click());
        onView(withId(R.id.order_received_text_view)).check(matches(isDisplayed()));
    }

    @Test
    public void onDeliveryOrderClick_checkIfDeliveryOrderPageIsOpened() {
        FakeOrderDataSource.getInstance().createOrdersObservable(OrderLifeCycleConstants.ORDER_STATUSES_ARRAY);
        reloadOrdersActivity();
        onView(withText(OrderLifeCycleConstants.STATUS_ORDER_OUT_FOR_DELIVERY)).perform(click());
        onView(withId(R.id.order_out_for_delivery_text_view)).check(matches(isDisplayed()));
    }

    @Test
    public void onServerError_checkIfSnackBarIsDisplayedWithCorrectMessage() {
        FakeOrderDataSource.getInstance().createAllOrderResponseWithServerErrorObservable(SERVER_BUSY_MESSAGE);
        reloadOrdersActivity();
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(SERVER_BUSY_MESSAGE)))
                .check(matches(isDisplayed()));
    }


    private void reloadOrdersActivity() {
        mActivityTestRule.launchActivity(new Intent());
    }
}
