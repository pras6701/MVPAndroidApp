package com.mvp.app.ui.base.intf;


import android.support.annotation.StringRes;

/**
 * Base interface that any class that wants to act as a View in the MVP (Model View IMvpPresenter)
 * pattern must implement. Generally this interface will be extended by a more specific interface
 * that then usually will be implemented by an Activity or Fragment.
 */
public interface IMvpView {

    void showLoading();

    void hideLoading();

    void openActivityOnTokenExpire();

    void onError(@StringRes int resId);

    void onError(String message);

    boolean isNetworkConnected();

    void hideKeyboard();
}
