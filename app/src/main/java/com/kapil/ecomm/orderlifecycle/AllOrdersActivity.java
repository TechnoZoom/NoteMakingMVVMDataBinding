package com.kapil.ecomm.orderlifecycle;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.kapil.ecomm.MyApplication;
import com.kapil.ecomm.R;
import com.kapil.ecomm.ViewModelHolder;
import com.kapil.ecomm.data.Order;
import com.kapil.ecomm.data.source.OrdersRepository;
import com.kapil.ecomm.databinding.ActivityAllOrdersBinding;
import com.kapil.ecomm.util.ActivityUtils;
import com.kapil.ecomm.util.SnackbarUtils;

import java.util.List;

import javax.inject.Inject;

import static com.kapil.ecomm.constants.OrderLifeCycleConstants.STATUS_ORDER_CANCELLED;
import static com.kapil.ecomm.constants.OrderLifeCycleConstants.STATUS_ORDER_OUT_FOR_DELIVERY;
import static com.kapil.ecomm.constants.OrderLifeCycleConstants.STATUS_ORDER_RECEIVED;

public class AllOrdersActivity extends AppCompatActivity {
    
    @Inject
    OrdersRepository ordersRepository;

    private static final String ORDERS_VIEWMODEL_TAG = "orders_view_model_tag";

    public AllOrdersViewModel getAllOrdersViewModel() {
        return allOrdersViewModel;
    }

    private AllOrdersViewModel allOrdersViewModel;

    public ActivityAllOrdersBinding getActivityAllOrdersBinding() {
        return activityAllOrdersBinding;
    }

    private ActivityAllOrdersBinding activityAllOrdersBinding;
    private Observable.OnPropertyChangedCallback snackbarCallback;
    private Observable.OnPropertyChangedCallback orderClickedStatusCallBack;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApplication.getComponent().inject(this);
        activityAllOrdersBinding = DataBindingUtil.setContentView(this, R.layout.activity_all_orders);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(this.getString(R.string.all_orders));
        setViewModel();
        setupSnackBar();
        setOrderClickedCallback();
        setOrdersListCallBack();
    }

    private void setViewModel() {
        allOrdersViewModel = findOrCreateViewModel();
        activityAllOrdersBinding.setAllOrdersViewModel(allOrdersViewModel);
    }

    @Override
    public void onResume() {
        super.onResume();
        allOrdersViewModel.loadOrders();
    }


    private AllOrdersViewModel findOrCreateViewModel() {
        // In a configuration change we might have a ViewModel present. It's retained using the
        // Fragment Manager.
        @SuppressWarnings("unchecked")
        ViewModelHolder<AllOrdersViewModel> retainedViewModel =
                (ViewModelHolder<AllOrdersViewModel>) getSupportFragmentManager()
                        .findFragmentByTag(ORDERS_VIEWMODEL_TAG);

        if (retainedViewModel != null && retainedViewModel.getViewmodel() != null) {
            // If the model was retained, return it.
            return retainedViewModel.getViewmodel();
        } else {
            // There is no ViewModel yet, create it.
            AllOrdersViewModel viewModel = new AllOrdersViewModel(
                    ordersRepository,
                    getApplicationContext().getString(R.string.some_error_ocurred));
            // and bind it to this Activity's lifecycle using the Fragment Manager.
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel),
                    ORDERS_VIEWMODEL_TAG);
            return viewModel;
        }
    }

    private void setupSnackBar() {
        snackbarCallback = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                SnackbarUtils.showSnackbar(activityAllOrdersBinding.mainCord, allOrdersViewModel.getSnackbarText());
            }
        };
        allOrdersViewModel.snackbarText.addOnPropertyChangedCallback(snackbarCallback);
    }

    private void setOrderClickedCallback() {
        orderClickedStatusCallBack = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                String status = allOrdersViewModel.getOrderClickedStatus();
                Class activityClassToStart = null;
                switch (status) {
                    case STATUS_ORDER_CANCELLED :
                        activityClassToStart = CancelledOrderActivity.class;
                        break;
                    case STATUS_ORDER_RECEIVED :
                        activityClassToStart = OrderReceivedActivity.class;
                        break;
                    case STATUS_ORDER_OUT_FOR_DELIVERY :
                        activityClassToStart = DeliveryActivity.class;
                        break;
                }

                if(activityClassToStart != null) {
                    startActivity(new Intent(AllOrdersActivity.this,activityClassToStart));
                }
            }
        };
        allOrdersViewModel.orderClickedStatus.addOnPropertyChangedCallback(orderClickedStatusCallBack);
    }

    private void setOrdersListCallBack() {

        allOrdersViewModel.ordersList.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Order>>() {
            @Override
            public void onChanged(ObservableList<Order> orders) {
                setNotesRecyclerView(orders.subList(0,orders.size()));
            }

            @Override
            public void onItemRangeChanged(ObservableList<Order> orders, int i, int i1) {
                setNotesRecyclerView(orders.subList(0,orders.size()));
            }

            @Override
            public void onItemRangeInserted(ObservableList<Order> orders, int i, int i1) {
                setNotesRecyclerView(orders.subList(0,orders.size()));

            }

            @Override
            public void onItemRangeMoved(ObservableList<Order> orders, int i, int i1, int i2) {
                setNotesRecyclerView(orders.subList(0,orders.size()));

            }

            @Override
            public void onItemRangeRemoved(ObservableList<Order> orders, int i, int i1) {
                setNotesRecyclerView(orders.subList(0,orders.size()));
            }
        });
    }

    private void setNotesRecyclerView(List<Order> orderList) {
        activityAllOrdersBinding.contAllOrders.ordersRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        activityAllOrdersBinding.contAllOrders.ordersRecyclerView.setAdapter(
                new AllOrdersAdapter(this,allOrdersViewModel,orderList));
    }
    
}





