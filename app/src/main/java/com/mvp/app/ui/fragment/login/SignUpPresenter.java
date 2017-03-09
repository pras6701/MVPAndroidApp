package com.mvp.app.ui.fragment.login;

import com.mvp.app.data.IDataManager;
import com.mvp.app.data.network.model.SignUpRequest;
import com.mvp.app.data.network.model.SignUpResponse;
import com.mvp.app.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class SignUpPresenter<V extends SignUpMvpContract.ISignUpMvpView> extends BasePresenter<V> implements SignUpMvpContract.ISignUpMvpPresenter<V> {

    @Inject
    public SignUpPresenter(IDataManager dataManager, CompositeSubscription compositeSubscription) {
        super(dataManager, compositeSubscription);
    }

    @Override
    public void signUp() {
        SignUpRequest signUpRequest= populateSignUpRequest();
        mCompositeSubscription.add(mDataManager.getApiHelper().signUp(signUpRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<SignUpResponse>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e)
                    {
                        getMvpView().onSignUpFailed();
                        Timber.w("New Booking call onError" + e);
                    }

                    @Override
                    public void onNext(SignUpResponse vendorSignUpResponse) {
                        getMvpView().onSignUpSuccess();
                    }
                }));
    }

    private SignUpRequest populateSignUpRequest() {
        return null;
    }
}
