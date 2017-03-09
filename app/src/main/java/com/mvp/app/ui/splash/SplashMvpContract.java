package com.mvp.app.ui.splash;

import com.mvp.app.injection.PerActivity;
import com.mvp.app.ui.base.intf.IMvpPresenter;
import com.mvp.app.ui.base.intf.IMvpView;

public class SplashMvpContract {

    @PerActivity
    public interface ISplashMvpPresenter<V extends ISplashMvpView> extends IMvpPresenter<V> {

        void decideNextActivity();
    }

    public interface ISplashMvpView extends IMvpView {

        void openLoginActivity();

        void openHomeActivity();

        void startSyncService();
    }

}
