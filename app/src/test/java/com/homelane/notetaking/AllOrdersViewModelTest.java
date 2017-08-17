package com.homelane.notetaking;

import com.homelane.notetaking.data.source.OrdersRepository;
import com.homelane.notetaking.data.source.mock.FakeOrderDataSource;
import com.homelane.notetaking.orderlifecycle.AllOrdersViewModel;

import org.junit.BeforeClass;
import org.junit.Test;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.plugins.RxJavaTestPlugins;
import rx.schedulers.Schedulers;

import static com.homelane.notetaking.Constants.EXCEPTION_ERROR_SNACKBAR_TEXT;
import static com.homelane.notetaking.Constants.SERVER_BUSY_MESSAGE;
import static junit.framework.Assert.assertEquals;


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

    private AllOrdersViewModel constructAndGetAllOrdersViewModel(String errorText) {
        return new AllOrdersViewModel(OrdersRepository.getInstance(FakeOrderDataSource.getInstance()),errorText);
    }

}


