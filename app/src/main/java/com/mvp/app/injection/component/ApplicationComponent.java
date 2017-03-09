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

package com.mvp.app.injection.component;

import android.content.Context;


import com.mvp.app.MVPApplication;
import com.mvp.app.data.IDataManager;
import com.mvp.app.data.network.UnauthorisedInterceptor;
import com.mvp.app.injection.ApplicationContext;
import com.mvp.app.injection.module.ApplicationModule;
import com.mvp.app.receiver.BootCompletedReceiver;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MVPApplication app);
    void inject(UnauthorisedInterceptor unauthorisedInterceptor);
    void inject(BootCompletedReceiver bootCompletedReceiver);
    @ApplicationContext
    Context context();
   /* Application application();
    IApiHelper apiHelper();
    PreferencesHelper preferencesHelper();
    DbHelper dbHelper();
    DataManager dataManager();
    Bus eventBus();*/
    Bus eventBus();
    IDataManager dataManager();
}