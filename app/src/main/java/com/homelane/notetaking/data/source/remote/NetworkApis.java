package com.homelane.notetaking.data.source.remote;

import com.homelane.notetaking.networkresponses.AllOrdersResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by kapilbakshi on 13/08/17.
 */

public interface NetworkApis {

    @GET("/v2/59904fcc1200005300946362")
    Observable<AllOrdersResponse> getOrders();
}
