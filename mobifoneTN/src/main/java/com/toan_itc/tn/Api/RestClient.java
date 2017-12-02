package com.toan_itc.tn.Api;

import android.content.Context;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.toan_itc.tn.Network.Constants;

import io.realm.RealmObject;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by toan.it on 1/14/16.
 */
public class RestClient {
    private static RestApi sRestApi;
    protected final Context mContext;
    public RestApi request(){
        if (sRestApi == null) {
            Gson gson = new GsonBuilder()
                    .setExclusionStrategies(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes f) {
                            return f.getDeclaringClass().equals(RealmObject.class);
                        }
                        @Override
                        public boolean shouldSkipClass(Class<?> clazz) {
                            return false;
                        }
                    })
                    .create();
            sRestApi = new Retrofit.Builder()
                    .baseUrl(Constants.TN_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient())
                    .build()
                    .create(RestApi.class);
        }
        return sRestApi;
    }

    public static RestClient with(Context context) {
        return new RestClient(context);
    }

    public RestClient(Context context) {
        this.mContext = context;
    }
    private OkHttpClient okHttpClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder().addInterceptor(logging).addNetworkInterceptor(headerInterceptor);

        return builder.build();
    }
    Interceptor headerInterceptor = chain -> {
        Request original = chain.request();

        Request request = original.newBuilder()
                .method(original.method(), original.body())
                .build();

        return chain.proceed(request);
    };
}
