package com.kapil.ecomm.orderlifecycle;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.kapil.ecomm.data.Order;
import com.kapil.ecomm.data.source.OrdersDataSource;
import com.kapil.ecomm.data.source.OrdersRepository;
import com.kapil.ecomm.networkresponses.AllOrdersResponse;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kapilbakshi on 13/08/17.
 */

public class AllOrdersViewModel {

    // These observable fields will update Views automatically
    public final ObservableList<Order> ordersList = new ObservableArrayList<>();

    public final ObservableBoolean dataLoading = new ObservableBoolean(false);

    final ObservableField<String> snackbarText = new ObservableField<>();

    final ObservableField<String> orderClickedStatus = new ObservableField<>();

    private final OrdersRepository ordersRepository;

    private String exceptionErrorText;

    public AllOrdersViewModel(
            OrdersRepository repository,
            String exceptionErrorText) {
        this.exceptionErrorText = exceptionErrorText;
        ordersRepository = repository;
    }

    public String getOrderClickedStatus() {
        return orderClickedStatus.get();
    }


    public void loadOrders() {
        loadOrders(true);
    }

    public void orderClicked(String orderStatus) {
        orderClickedStatus.set(orderStatus);
    }

    public String getSnackbarText() {
        return snackbarText.get();
    }

    public ObservableList<Order> getOrdersList() {
        return ordersList;
    }

    public ObservableBoolean getDataLoading() {
        return dataLoading;
    }

    /**
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadOrders(final boolean showLoadingUI) {
        if (showLoadingUI) {
            dataLoading.set(true);
        }
        ordersRepository.getOrdersResponse(new OrdersDataSource.LoadOrdersCallback() {

            @Override
            public void onGetOrdersResponse(Observable<AllOrdersResponse> ordersResponseObservable) {

                ordersResponseObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<AllOrdersResponse>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                dataLoading.set(false);
                                snackbarText.set(exceptionErrorText);
                                e.printStackTrace();
                            }

                            @Override
                            public void onNext(AllOrdersResponse allOrdersResponse) {
                                dataLoading.set(false);
                                if (allOrdersResponse.isSuccess()) {
                                    ordersList.clear();
                                    ordersList.addAll(allOrdersResponse.getOrders());
                                }
                                else {
                                    snackbarText.set(allOrdersResponse.getError_message());
                                }
                            }
                        });
            }

        });
    }
}