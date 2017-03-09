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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import com.mvp.app.R;
import com.mvp.app.data.BusEvent;
import com.mvp.app.service.SyncService;
import com.mvp.app.ui.base.BaseActivity;
import com.mvp.app.ui.home.HomeActivity;
import com.mvp.app.ui.login.LoginActivity;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import timber.log.Timber;


public class SplashActivity extends BaseActivity implements SplashMvpContract.ISplashMvpView {

    @Inject
    SplashMvpContract.ISplashMvpPresenter<SplashMvpContract.ISplashMvpView> mSplashPresenter;
    @Inject
    Bus mBus;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp();
        setContentView(R.layout.activity_splash);
        activityComponent().inject(this);
        mBus.register(this);
        setUpButterKnife();
        mSplashPresenter.attachView(SplashActivity.this);
        startSyncService();
    }

    /**
     * Making the screen wait so that the  branding can be shown
     */
    @Override
    public void openLoginActivity() {
        Intent intent = LoginActivity.getStartIntent(SplashActivity.this,true);
        startActivity(intent);
        finish();
    }

    @Override
    public void openHomeActivity() {
        Intent intent = HomeActivity.getStartIntent(SplashActivity.this,true);
        startActivity(intent);
        finish();
    }

    @Override
    public void startSyncService() {
        SyncService.start(this);
    }

    @Override
    protected void onDestroy() {
        mSplashPresenter.detachView();
        mBus.unregister(this);
        super.onDestroy();
        finish();
    }

    @Subscribe
    public void onMasterDataSyncCompleted(BusEvent.SyncCompleted event) {
        Timber.i("Master Data sync completed, opening next activity");
        if(SyncService.isRunning(this)){
            SyncService.stop(this);
        }

        mSplashPresenter.decideNextActivity();
    }

    @Subscribe
    public void onMasterDataSyncFailure(BusEvent.SyncFailure event) {
        Timber.i("Master Data sync failure");
        showSnackBar("We are facing some issue.Please try again later",null);
    }

    @Override
    protected void setUp() {
        setUpFullScreen();
    }

    @Override
    protected boolean isNetworkBroadcastEnabled() {
        return true;
    }

    @Override
    public void onNetworkAvailable() {
        if(!SyncService.isRunning(this)){
            SyncService.start(this);
        }
        //showSnackBar("Network Availaible",null);
    }

    @Override
    public void onNetworkUnavailable() {
        if(SyncService.isRunning(this)){
            SyncService.stop(this);
        }
        showSnackBar("No Network.","Turn on internet");
    }

}
