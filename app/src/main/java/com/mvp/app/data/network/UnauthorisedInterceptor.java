package com.mvp.app.data.network;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.mvp.app.MVPApplication;
import com.mvp.app.data.BusEvent;
import com.squareup.otto.Bus;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Response;

public class UnauthorisedInterceptor implements Interceptor {

    @Inject Bus eventBus;

    public UnauthorisedInterceptor(Context context) {
        MVPApplication.get(context).getComponent().inject(this);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response=null;
        try {
            response = chain.proceed(chain.request());
            if (response.code() == 401) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        eventBus.post(new BusEvent.AuthenticationError());
                    }
                });
            }
        }catch(Exception e){
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    eventBus.post(new BusEvent.SyncFailure());
                }
            });
        }
        return response;
    }
}
