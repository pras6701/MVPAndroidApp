package com.mvp.app;

import android.app.Application;
import android.content.Context;

import com.mvp.app.data.BusEvent;
import com.mvp.app.data.IDataManager;
import com.mvp.app.injection.component.ApplicationComponent;
import com.mvp.app.injection.component.DaggerApplicationComponent;
import com.mvp.app.injection.module.ApplicationModule;
import com.mvp.app.ui.login.LoginActivity;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MVPApplication extends Application {

    @Inject
    Bus mEventBus;

    @Inject
    IDataManager mDataManager;

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (com.mvp.app.BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            LeakCanary.install(this);
        }

        if (com.mvp.app.BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mApplicationComponent.inject(this);
        mEventBus.register(this);
    }

    public static MVPApplication get(Context context) {
        return (MVPApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

    @Subscribe
    public void onAuthenticationError(BusEvent.AuthenticationError event) {
        mDataManager.signOut()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        startSignInActivity();
                    }
                });
    }

    private void startSignInActivity() {
        startActivity(LoginActivity.getStartIntent(
                this, true, getString(com.mvp.app.R.string.authentication_message)));
    }

}

