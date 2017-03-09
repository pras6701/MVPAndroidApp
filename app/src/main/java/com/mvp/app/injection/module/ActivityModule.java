/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.mvp.app.injection.module;

import android.app.Activity;
import android.content.Context;

import com.mvp.app.injection.ActivityContext;
import com.mvp.app.injection.PerActivity;
import com.mvp.app.ui.fragment.home.DummyMvpContract;
import com.mvp.app.ui.fragment.home.DummyMvpContract.IDummyMvpView;
import com.mvp.app.ui.fragment.home.DummyPresenter;
import com.mvp.app.ui.fragment.login.SignUpMvpContract;
import com.mvp.app.ui.fragment.login.SignUpPresenter;
import com.mvp.app.ui.home.HomeMvpContract;
import com.mvp.app.ui.home.HomePresenter;
import com.mvp.app.ui.login.LoginMvpContract;
import com.mvp.app.ui.login.LoginPresenter;
import com.mvp.app.ui.splash.SplashMvpContract;
import com.mvp.app.ui.splash.SplashPresenter;

import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return activity;
    }

    @Provides
    Activity provideActivity() {
        return activity;
    }

    @Provides
    CompositeSubscription provideCompositeSubscription() {
        return new CompositeSubscription();
    }

    @Provides
    @PerActivity
    SplashMvpContract.ISplashMvpPresenter<SplashMvpContract.ISplashMvpView> provideSplashPresenter(SplashPresenter<SplashMvpContract.ISplashMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    HomeMvpContract.IHomeMvpPresenter<HomeMvpContract.IHomeMvpView> provideHomePresenter(HomePresenter<HomeMvpContract.IHomeMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    DummyMvpContract.IDummyMvpPresenter<IDummyMvpView> provideNewBookingPresenter(DummyPresenter<IDummyMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LoginMvpContract.ILoginMvpPresenter<LoginMvpContract.ILoginMvpView> provideLoginPresenter(LoginPresenter<LoginMvpContract.ILoginMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SignUpMvpContract.ISignUpMvpPresenter<SignUpMvpContract.ISignUpMvpView> provideSignUpPresenter(SignUpPresenter<SignUpMvpContract.ISignUpMvpView> presenter) {
        return presenter;
    }

}
