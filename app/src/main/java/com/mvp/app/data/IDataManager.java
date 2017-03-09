package com.mvp.app.data;

import com.mvp.app.data.db.IDbHelper;
import com.mvp.app.data.network.IApiHelper;
import com.mvp.app.data.network.model.SignInRequest;
import com.mvp.app.data.network.model.SignInResponse;
import com.mvp.app.data.prefs.IPreferencesHelper;

import rx.Observable;

public interface IDataManager {

    IPreferencesHelper getPreferencesHelper();
    IApiHelper getApiHelper();
    IDbHelper getDbHelper();

    Observable<SignInResponse> signIn(SignInRequest vendorSignInRequest);
    Observable<Void> signOut();
    Observable<Void> syncMasterData();

}
