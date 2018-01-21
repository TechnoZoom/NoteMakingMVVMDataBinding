package com.kapil.ecomm;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kapil.ecomm.constants.OrderLifeCycleConstants;
import com.kapil.ecomm.data.source.mock.FakeOrderDataSource;
import com.kapil.ecomm.orderlifecycle.AllOrdersActivity;
import com.kapil.ecomm.orderlifecycle.AllOrdersViewModel;
import com.kapil.ecomm.orderlifecycle.CancelledOrderActivity;
import com.kapil.ecomm.orderlifecycle.DeliveryActivity;
import com.kapil.ecomm.orderlifecycle.OrderReceivedActivity;
import com.kapil.ecomm.support.ShadowSnackbar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static com.kapil.ecomm.support.Assert.assertNextActivity;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by kapilbakshi on 16/08/17.
 */

@RunWith(RobolectricTestRunner.class)
@org.robolectric.annotation.Config(constants = BuildConfig.class, sdk = 21,
        shadows = {ShadowSnackbar.class},application = UnitTestingApplication.class)

public class AllOrdersActivityTest {

    private static final String SERVER_BUSY_MESSAGE = "Server is busy please try after sometime" ;
    private AllOrdersActivity activity;
    private AllOrdersViewModel allOrdersViewModel;
    RecyclerView ordersRecyclerView;

    @Test
    public void onExceptionError_checkIfSnacBarIsDisplayed() {
        FakeOrderDataSource.getInstance().create_Exception_Error_Observable("Internet Security Exception");
        reloadOrdersActivity();
        assertThat(activity.getString(R.string.some_error_ocurred), equalTo(ShadowSnackbar.getTextOfLatestSnackbar()));
    }

    @Test
    public void onServerError_checkIfSnacBarIsDisplayedWithCorrectMessage() {
        FakeOrderDataSource.getInstance().createAllOrderResponseWithServerErrorObservable(SERVER_BUSY_MESSAGE);
        reloadOrdersActivity();
        assertThat(SERVER_BUSY_MESSAGE, equalTo(ShadowSnackbar.getTextOfLatestSnackbar()));
    }

    @Test
    public void onOrdersLoading_checkIfOrderCountIsCorrect() {
        FakeOrderDataSource.getInstance().createOrdersObservable(OrderLifeCycleConstants.ORDER_STATUSES_ARRAY);
        reloadOrdersActivity();
        assertTrue(ordersRecyclerView.getAdapter().getItemCount()==3);
    }

    @Test
    public void onOrdersLoaded_checkIfStatusLabellingOfOrderItemsIsCorrect() {
        FakeOrderDataSource.getInstance().createOrdersObservable(OrderLifeCycleConstants.ORDER_STATUSES_ARRAY);
        reloadOrdersActivity();
        for(int i = 0; i < OrderLifeCycleConstants.ORDER_STATUSES_ARRAY.length; i++) {
            View itemView = ordersRecyclerView.getChildAt(i);
            TextView statusTextView = (TextView) itemView.findViewById(R.id.order_status_text_view);
            assertTrue(statusTextView.getText().toString().equals(OrderLifeCycleConstants.ORDER_STATUSES_ARRAY[i]));
        }
    }

    @Test
    public void onDeliveryOrderClick_checkIfDeliveryOrderPageIsOpened() {
        FakeOrderDataSource.getInstance().createOrdersObservable(OrderLifeCycleConstants.ORDER_STATUSES_ARRAY);
        reloadOrdersActivity();
        ordersRecyclerView.getChildAt(0).performClick();
        assertNextActivity(activity,DeliveryActivity.class);
    }

    @Test
    public void onCancelledOrderClick_checkIfCancelledOrderPageIsOpened() {
        FakeOrderDataSource.getInstance().createOrdersObservable(OrderLifeCycleConstants.ORDER_STATUSES_ARRAY);
        reloadOrdersActivity();
        ordersRecyclerView.getChildAt(1).performClick();
        assertNextActivity(activity,CancelledOrderActivity.class);
    }

    @Test
    public void onReceivedOrderClick_checkIfReceivedOrderPageIsOpened() {
        FakeOrderDataSource.getInstance().createOrdersObservable(OrderLifeCycleConstants.ORDER_STATUSES_ARRAY);
        reloadOrdersActivity();
        ordersRecyclerView.getChildAt(2).performClick();
        assertNextActivity(activity,OrderReceivedActivity.class);
    }


    private void reloadOrdersActivity() {
        activity = Robolectric.setupActivity(AllOrdersActivity.class);
        ordersRecyclerView = (RecyclerView) activity.findViewById(R.id.orders_recycler_view);
        allOrdersViewModel = activity.getAllOrdersViewModel();
    }
}