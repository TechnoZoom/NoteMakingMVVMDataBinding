package com.kapil.ecomm.di.modules;

import com.kapil.ecomm.BuildConfig;
import com.kapil.ecomm.MyApplication;


import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

import static com.kapil.ecomm.Config.BASE_URL;

/**
 * Created by kapilbakshi on 13/08/17.
 */

@Module
public class NetworkModule {

    private MyApplication application;

    public NetworkModule(MyApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Retrofit providesNotesRepository() {
        OkHttpClient.Builder httpClient;
        httpClient = new OkHttpClient.Builder()
                .connectTimeout(70, TimeUnit.SECONDS)
                .readTimeout(70, TimeUnit.SECONDS)
                .writeTimeout(70, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create().asLenient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }

}
