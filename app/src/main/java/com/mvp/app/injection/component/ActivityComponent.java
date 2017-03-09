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


import com.mvp.app.injection.PerActivity;
import com.mvp.app.injection.module.ActivityModule;
import com.mvp.app.ui.fragment.home.DummyFragment;
import com.mvp.app.ui.fragment.login.SignUpFragment;
import com.mvp.app.ui.home.HomeActivity;
import com.mvp.app.ui.login.LoginActivity;
import com.mvp.app.ui.splash.SplashActivity;

import dagger.Component;


@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(LoginActivity loginActivity);
    void inject(SplashActivity splashActivity);
    void inject(DummyFragment bookingFragment);
    void inject(SignUpFragment signUpFragment);
    void inject(HomeActivity homeActivity);
}
