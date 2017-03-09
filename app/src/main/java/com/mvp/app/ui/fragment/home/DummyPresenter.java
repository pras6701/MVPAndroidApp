package com.mvp.app.ui.fragment.home;

import com.mvp.app.data.IDataManager;
import com.mvp.app.data.network.model.DummyData;
import com.mvp.app.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class DummyPresenter<V extends DummyMvpContract.IDummyMvpView> extends BasePresenter<V> implements DummyMvpContract.IDummyMvpPresenter<V> {

    @Inject
    public DummyPresenter(IDataManager dataManager, CompositeSubscription compositeSubscription) {
        super(dataManager, compositeSubscription);
    }

    @Override
    public void fetchNewDummyData() {
        mCompositeSubscription.add(mDataManager.getApiHelper().fetchDummyDatas()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DummyData>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e)
                    {
                        getMvpView().showProgress(false);
                        Timber.w("New Booking call onError" + e);
                    }

                    @Override
                    public void onNext(List<DummyData> dummyDatas) {
                        getMvpView().onFetchNewDummyDataSuccessful(dummyDatas);
                    }
                }));
    }
}
