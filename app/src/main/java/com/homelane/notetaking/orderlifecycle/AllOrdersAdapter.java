package com.homelane.notetaking.orderlifecycle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.homelane.notetaking.R;
import com.homelane.notetaking.data.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kapilbakshi on 13/08/17.
 */

public class AllOrdersAdapter extends RecyclerView.Adapter<AllOrdersAdapter.OrdersViewHolder> {

    private List<Order> ordersList = new ArrayList<>();
    private AllOrdersViewModel viewModel;
    private Context context;

    public AllOrdersAdapter(Context context, AllOrdersViewModel viewModel, List<Order> ordersList) {
        this.context = context;
        this.viewModel = viewModel;
        this.ordersList = ordersList;
    }

    @Override
    public AllOrdersAdapter.OrdersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new AllOrdersAdapter.OrdersViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(AllOrdersAdapter.OrdersViewHolder holder, final int position) {

        final Order order = ordersList.get(position);
        holder.getRowView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.orderClicked(order.getStatus());
            }
        });

        holder.getOrderStatusTextView().setText(order.getStatus());
        holder.getProductNameTextView().setText(order.getProduct().getName());
        holder.getProductPriceTextView().setText(String.valueOf(order.getProduct().getPrice()));
    }

    class OrdersViewHolder extends RecyclerView.ViewHolder {
        private TextView orderStatusTextView;
        private TextView productPriceTextView;
        private TextView productNameTextView;
        private View rowView;

        OrdersViewHolder(View rowView) {
            super(rowView);
            orderStatusTextView = (TextView) rowView.findViewById(R.id.order_status_text_view);
            productPriceTextView = (TextView) rowView.findViewById(R.id.product_price_text_view);
            productNameTextView = (TextView) rowView.findViewById(R.id.product_name_text_view);
            this.rowView = rowView;
        }

        public View getRowView() {
            return rowView;
        }

        public TextView getOrderStatusTextView() {
            return orderStatusTextView;
        }

        public TextView getProductPriceTextView() {
            return productPriceTextView;
        }

        public TextView getProductNameTextView() {
            return productNameTextView;
        }

    }

}

