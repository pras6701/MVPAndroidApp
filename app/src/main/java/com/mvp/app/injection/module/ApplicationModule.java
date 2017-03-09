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

import android.app.Application;
import android.content.Context;

import com.mvp.app.data.DataManager;
import com.mvp.app.data.IDataManager;
import com.mvp.app.data.db.DbHelper;
import com.mvp.app.data.db.IDbHelper;
import com.mvp.app.data.network.ApiFactory;
import com.mvp.app.data.network.IApiHelper;
import com.mvp.app.data.prefs.IPreferencesHelper;
import com.mvp.app.data.prefs.PreferencesHelper;
import com.mvp.app.injection.ApiInfo;
import com.mvp.app.injection.ApplicationContext;
import com.mvp.app.injection.DatabaseInfo;
import com.mvp.app.injection.PreferenceInfo;
import com.mvp.app.util.AppConstants;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application mApplication;


    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    Bus provideEventBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    IApiHelper provideApiHelper() {
        return ApiFactory.makeApiService(mApplication);
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return "VHVH";
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }


    @Provides
    @Singleton
    IDataManager provideDataManager(DataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    IDbHelper provideDbHelper(DbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    IPreferencesHelper providePreferencesHelper(PreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }
}
