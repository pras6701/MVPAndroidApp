package com.mvp.app.ui.home;

import com.mvp.app.data.IDataManager;
import com.mvp.app.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class HomePresenter<V extends HomeMvpContract.IHomeMvpView> extends BasePresenter<V> implements HomeMvpContract.IHomeMvpPresenter<V> {

    @Inject
    public HomePresenter(IDataManager dataManager, CompositeSubscription compositeSubscription) {
        super(dataManager, compositeSubscription);
    }

    @Override
    public void signOut() {
        mCompositeSubscription.add(mDataManager.signOut()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        Timber.i("Sign out successful!");
                        getMvpView().openActivityOnTokenExpire();
                    }
                    @Override
                    public void onError(Throwable e) {
                        Timber.e("Error signing out: " + e);
                    }
                    @Override
                    public void onNext(Void aVoid) {
                        getMvpView().onSuccessfullSignOut();
                    }
                }));
    }
}
