package com.mvp.app.data.db;

import com.mvp.app.data.db.model.User;
import com.mvp.app.data.network.model.MasterData;

import rx.Observable;

public interface IDbHelper {

    Observable<Long> insertUser(final User user);
    Observable<Void> clearTables() ;
    Observable<Void> setUserDetails(final User user);
    Observable<User> getLoggedInUserDetails();
    Observable<Void> setMasterData(MasterData masterData);

}