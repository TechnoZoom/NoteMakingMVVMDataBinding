package com.kapil.ecomm.di;

import com.kapil.ecomm.data.source.OrdersRepository;
import com.kapil.ecomm.data.source.mock.FakeOrderDataSource;
import com.kapil.ecomm.di.modules.OrdersModule;

/**
 * Created by kapilbakshi on 14/08/17.
 */

public class OrdersTestModule extends OrdersModule {

    @Override
    public OrdersRepository providesNotesRepository() {
        return OrdersRepository.getInstance(
                FakeOrderDataSource.getInstance());
    }
}
