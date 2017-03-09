package com.mvp.app.ui.fragment.login;

import com.mvp.app.injection.PerActivity;
import com.mvp.app.ui.base.intf.IMvpPresenter;
import com.mvp.app.ui.base.intf.IMvpView;

public class SignUpMvpContract {

    @PerActivity
    public interface ISignUpMvpPresenter<V extends ISignUpMvpView> extends IMvpPresenter<V> {
        void signUp();
    }

    public interface ISignUpMvpView extends IMvpView {
        void onSignUpSuccess();
        void onSignUpFailed();
    }

}
