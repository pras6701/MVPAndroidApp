package com.mvp.app.ui.base;

import com.mvp.app.data.IDataManager;
import com.mvp.app.ui.base.intf.IMvpPresenter;
import com.mvp.app.ui.base.intf.IMvpView;

import rx.subscriptions.CompositeSubscription;

/**
 * Base class that implements the IMvpPresenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 */
public abstract class BasePresenter<T extends IMvpView> implements IMvpPresenter<T> {

    protected T mMvpView;

    protected final IDataManager mDataManager;

    protected final CompositeSubscription mCompositeSubscription;

    public BasePresenter(IDataManager dataManager, CompositeSubscription compositeSubscription) {
        this.mDataManager = dataManager;
        this.mCompositeSubscription = compositeSubscription;
    }
    @Override
    public void attachView(T mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        if(mCompositeSubscription !=null && mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription.unsubscribe();
        }
        mMvpView = null;
    }

    @Override
    public void handleApiError() {
    //TODO:Implement
       /* switch (error.getErrorCode()) {
            case HttpsURLConnection.HTTP_UNAUTHORIZED:
            case HttpsURLConnection.HTTP_FORBIDDEN:
                setUserAsLoggedOut();
                getMvpView().openActivityOnTokenExpire();
            case HttpsURLConnection.HTTP_INTERNAL_ERROR:
            case HttpsURLConnection.HTTP_NOT_FOUND:
            default:
                getMvpView().onError(apiError.getMessage());
        }*/
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public T getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    @Override
    public void setUserAsLoggedOut() {
        mDataManager.getPreferencesHelper().putAccessToken(null);
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call IMvpPresenter.attachView(IMvpView) before" +
                    " requesting data to the IMvpPresenter");
        }
    }
}

