package com.mvp.app.ui.login;

import com.mvp.app.data.network.model.SignInResponse;
import com.mvp.app.injection.PerActivity;
import com.mvp.app.ui.base.intf.IMvpPresenter;
import com.mvp.app.ui.base.intf.IMvpView;

public class LoginMvpContract {

    @PerActivity
    public interface ILoginMvpPresenter<V extends ILoginMvpView> extends IMvpPresenter<V> {

        void signIn(final String userId, final String password);
    }

    public interface ILoginMvpView extends IMvpView {

        void onSignInSuccessful(SignInResponse vendorSignInResponse);

        void showProgress(boolean show);

        void setSignInButtonEnabled(boolean enabled);

        void showProfileNotFoundError(String accountName);

        void showGeneralSignInError();

        void switchFragment(LoginActivity.LoginFragmentType fragmentType);

        boolean validateSignIn();

        void clearErrorLayout();
    }

}
