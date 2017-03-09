package com.mvp.app.ui.login;

import com.mvp.app.data.IDataManager;
import com.mvp.app.data.network.model.SignInRequest;
import com.mvp.app.data.network.model.SignInResponse;
import com.mvp.app.ui.base.BasePresenter;
import com.mvp.app.util.NetworkUtil;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class LoginPresenter<V extends LoginMvpContract.ILoginMvpView> extends BasePresenter<V> implements LoginMvpContract.ILoginMvpPresenter<V> {

    @Inject
    public LoginPresenter(IDataManager dataManager, CompositeSubscription compositeSubscription) {
        super(dataManager, compositeSubscription);
    }

    @Override
    public void signIn(final String userId, final String password) {
        Timber.i("Starting sign in with account userId:" + userId);
        getMvpView().showProgress(true);
        getMvpView().setSignInButtonEnabled(false);
        getMvpView().clearErrorLayout();
        SignInRequest vendorSignInRequest=new SignInRequest();
        vendorSignInRequest.setId(userId);
        vendorSignInRequest.setPassword(password);

        Subscription subscription = mDataManager.signIn(vendorSignInRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<SignInResponse>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showProgress(false);
                        Timber.w("Sign in has called onError" + e);
                        getMvpView().setSignInButtonEnabled(true);
                        if (NetworkUtil.isHttpStatusCode(e, 403)) {
                            getMvpView().showProfileNotFoundError(userId);
                        } else {
                            getMvpView().showGeneralSignInError();
                        }
                    }

                    @Override
                    public void onNext(SignInResponse vendorSignInResponse) {
                        Timber.i("Sign in successful. Profile userId: " + userId);
                        getMvpView().onSignInSuccessful(vendorSignInResponse);
                    }
                });
        mCompositeSubscription.add(subscription);
    }

}
