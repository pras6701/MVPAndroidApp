package com.mvp.app.ui.base.intf;

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the IMvpView type that wants to be attached with.
 */
public interface IMvpPresenter<V extends IMvpView> {

    void attachView(V mvpView);

    void detachView();

    void handleApiError();

    void setUserAsLoggedOut();
}
