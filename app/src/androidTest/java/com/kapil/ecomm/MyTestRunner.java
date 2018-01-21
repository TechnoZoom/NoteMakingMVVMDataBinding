package com.kapil.ecomm;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

/**
 * Created by kapilbakshi on 10/08/17.
 */

public class MyTestRunner extends AndroidJUnitRunner {

    @Override
    public Application newApplication(ClassLoader classLoader, String className, Context context)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        // replace Application class with mock one
        return super.newApplication(classLoader, TestMyApplication.class.getName(), context);
    }
}
