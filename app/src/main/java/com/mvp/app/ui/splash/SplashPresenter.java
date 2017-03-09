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

package com.mvp.app.ui.splash;


import com.mvp.app.data.IDataManager;
import com.mvp.app.ui.base.BasePresenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;


public class SplashPresenter<V extends SplashMvpContract.ISplashMvpView> extends BasePresenter<V> implements SplashMvpContract.ISplashMvpPresenter<V> {

    @Inject
    public SplashPresenter(IDataManager dataManager, CompositeSubscription compositeSubscription) {
        super(dataManager, compositeSubscription);
    }

    @Override
    public void decideNextActivity() {
        if(getMvpView() == null){
            Timber.e("Splash Mvp View is null");
            return;
        }
        if (mDataManager.getPreferencesHelper().getAccessToken() == null) {
            getTimedObservable().subscribe(aLong -> getMvpView().openLoginActivity());
        } else {
            getTimedObservable().subscribe(aLong -> getMvpView().openHomeActivity());
        }
    }

    private Observable<Long> getTimedObservable() {
        return Observable.timer(1200, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
