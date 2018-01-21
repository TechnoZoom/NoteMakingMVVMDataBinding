package com.kapil.ecomm;

import com.kapil.ecomm.constants.OrderLifeCycleConstants;
import com.kapil.ecomm.data.source.OrdersRepository;
import com.kapil.ecomm.data.source.mock.FakeOrderDataSource;
import com.kapil.ecomm.orderlifecycle.AllOrdersViewModel;

import org.junit.BeforeClass;
import org.junit.Test;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.plugins.RxJavaTestPlugins;
import rx.schedulers.Schedulers;

import static com.kapil.ecomm.Constants.EXCEPTION_ERROR_SNACKBAR_TEXT;
import static com.kapil.ecomm.Constants.SERVER_BUSY_MESSAGE;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;


/**
 * Created by kapilbakshi on 16/08/17.
 */

public class AllOrdersViewModelTest {

    @BeforeClass
    public static void setupRxSchedulers() {
        RxJavaTestPlugins.resetPlugins();
        RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook() {
            @Override
            public Scheduler getIOScheduler() {
                return Schedulers.immediate();
            }
        });
        RxAndroidPlugins.getInstance().reset();
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }

    @Test
    public void onExceptionError_checkIfSnacBarIsDisplayed() {
        FakeOrderDataSource.getInstance().create_Exception_Error_Observable("Internet Security Exception");
        AllOrdersViewModel allOrdersViewModel = constructAndGetAllOrdersViewModel(EXCEPTION_ERROR_SNACKBAR_TEXT);
        allOrdersViewModel.loadOrders();
        assertEquals(EXCEPTION_ERROR_SNACKBAR_TEXT, allOrdersViewModel.getSnackbarText());
    }

    @Test
    public void onServerError_checkIfSnacBarIsDisplayedWithCorrectMessage() {
        FakeOrderDataSource.getInstance().createAllOrderResponseWithServerErrorObservable(SERVER_BUSY_MESSAGE);
        AllOrdersViewModel allOrdersViewModel = constructAndGetAllOrdersViewModel(EXCEPTION_ERROR_SNACKBAR_TEXT);
        allOrdersViewModel.loadOrders();
        assertEquals(SERVER_BUSY_MESSAGE, allOrdersViewModel.getSnackbarText());
    }

    @Test
    public void onOrdersFetched_CheckIfOrdreCountIsCorrect(){
        FakeOrderDataSource.getInstance().createOrdersObservable(OrderLifeCycleConstants.STATUS_ORDER_OUT_FOR_DELIVERY,
                OrderLifeCycleConstants.STATUS_ORDER_RECEIVED,OrderLifeCycleConstants.STATUS_ORDER_CANCELLED);
        AllOrdersViewModel allOrdersViewModel = constructAndGetAllOrdersViewModel(EXCEPTION_ERROR_SNACKBAR_TEXT);
        allOrdersViewModel.loadOrders();
        assertEquals(3, allOrdersViewModel.getOrdersList().size());
    }

    @Test
    public void beforeOrdersLoading_CheckIfProgressBarIsNotDisplayed(){
        FakeOrderDataSource.getInstance().createOrdersObservable(OrderLifeCycleConstants.STATUS_ORDER_OUT_FOR_DELIVERY,
                OrderLifeCycleConstants.STATUS_ORDER_RECEIVED,OrderLifeCycleConstants.STATUS_ORDER_CANCELLED);
        AllOrdersViewModel allOrdersViewModel = constructAndGetAllOrdersViewModel(EXCEPTION_ERROR_SNACKBAR_TEXT);
        assertFalse(allOrdersViewModel.getDataLoading().get());
    }

    @Test
    public void afterSuccessFullOrdersLoading_CheckIfProgressBarIsNotDisplayed(){
        FakeOrderDataSource.getInstance().createOrdersObservable(OrderLifeCycleConstants.ORDER_STATUSES_ARRAY);
        AllOrdersViewModel allOrdersViewModel = constructAndGetAllOrdersViewModel(EXCEPTION_ERROR_SNACKBAR_TEXT);
        allOrdersViewModel.loadOrders();
        assertFalse(allOrdersViewModel.getDataLoading().get());
    }

    @Test
    public void afterExceptionOrdersLoading_CheckIfProgressBarIsNotDisplayed(){
        FakeOrderDataSource.getInstance().create_Exception_Error_Observable("Internet Security Exception");
        AllOrdersViewModel allOrdersViewModel = constructAndGetAllOrdersViewModel(EXCEPTION_ERROR_SNACKBAR_TEXT);
        allOrdersViewModel.loadOrders();
        assertFalse(allOrdersViewModel.getDataLoading().get());
    }

    private AllOrdersViewModel constructAndGetAllOrdersViewModel(String errorText) {
        return new AllOrdersViewModel(OrdersRepository.getInstance(FakeOrderDataSource.getInstance()),errorText);
    }

}


