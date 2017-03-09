package com.mvp.app.data.prefs;

import com.mvp.app.data.network.model.MasterData;

import rx.Observable;

public interface IPreferencesHelper {
    int getCurrentUserLoggedInMode();
    void setCurrentUserLoggedInMode(String mode);
    Long getCurrentUserId();
    void clear();
    String getAccessToken();
    void putAccessToken(String token);

    Observable<Void> setMasterData(MasterData masterData);
}
