package com.homelane.notetaking.di;

import com.homelane.notetaking.data.source.OrdersRepository;
import com.homelane.notetaking.data.source.mock.FakeOrderDataSource;
import com.homelane.notetaking.di.modules.OrdersModule;

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
