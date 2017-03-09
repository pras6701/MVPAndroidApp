package com.mvp.app.ui.home;

import com.mvp.app.injection.PerActivity;
import com.mvp.app.ui.base.intf.IMvpPresenter;
import com.mvp.app.ui.base.intf.IMvpView;

public class HomeMvpContract {

    @PerActivity
    public interface IHomeMvpPresenter<V extends IHomeMvpView> extends IMvpPresenter<V> {
        void signOut();
    }

    public interface IHomeMvpView extends IMvpView {

        void onSuccessfullSignOut();
    }

}
