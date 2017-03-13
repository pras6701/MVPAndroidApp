package com.mvp.app.data.prefs;

import android.content.Context;

import com.mvp.app.data.network.model.MasterData;
import com.mvp.app.data.prefs.base.BasePreferencesHelper;
import com.mvp.app.injection.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class PreferencesHelper extends BasePreferencesHelper {

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        super(context);
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return 0;
    }

    @Override
    public void setCurrentUserLoggedInMode(String mode) {

    }

    @Override
    public Long getCurrentUserId() {
        return null;
    }

    @Override
    public Observable<Void> setMasterData(MasterData masterData) {
        put("MASTERDATA",mGson.toJson(masterData));
        return Observable.empty();
    }

}
