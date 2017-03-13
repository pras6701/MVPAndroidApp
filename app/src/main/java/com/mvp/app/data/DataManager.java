package com.mvp.app.data;

import com.mvp.app.data.db.IDbHelper;
import com.mvp.app.data.network.IApiHelper;
import com.mvp.app.data.network.model.MasterData;
import com.mvp.app.data.network.model.SignInRequest;
import com.mvp.app.data.network.model.SignInResponse;
import com.mvp.app.data.prefs.IPreferencesHelper;
import com.mvp.app.util.EventPosterHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Func1;

@Singleton
public class DataManager implements IDataManager{

    private final IApiHelper mApiHelper;
    private final IDbHelper mDbHelper;
    private final IPreferencesHelper mPreferencesHelper;
    private final EventPosterHelper mEventPoster;

    @Inject
    public DataManager(IApiHelper gmcVendorApiService,
                       IDbHelper databaseHelper,
                       IPreferencesHelper preferencesHelper,
                       EventPosterHelper eventPosterHelper) {
        mApiHelper = gmcVendorApiService;
        mDbHelper = databaseHelper;
        mPreferencesHelper = preferencesHelper;
        mEventPoster = eventPosterHelper;
    }

    public IPreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    @Override
    public IApiHelper getApiHelper() {
        return mApiHelper;
    }

    @Override
    public IDbHelper getDbHelper() {
        return mDbHelper;
    }

    public Observable<SignInResponse> signIn(SignInRequest vendorSignInRequest) {
        return mApiHelper.signIn(vendorSignInRequest);
    }

    public Observable<Void> signOut() {
        return mDbHelper.clearTables()
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        mPreferencesHelper.clear();
                        mEventPoster.postEventSafely(new BusEvent.UserSignedOut());
                    }
                });
    }

   public Observable<Void> syncMasterData() {
       return mApiHelper.fetchMasterData()
                .concatMap(new Func1<MasterData, Observable<Void>>() {
                    @Override
                    public Observable<Void> call(MasterData masterData) {
                        return mPreferencesHelper.setMasterData(masterData);
                    }
                })
               .doOnError(x->postEventSafelyAction(new BusEvent.SyncFailure()))
               .doOnCompleted(postEventSafelyAction(new BusEvent.SyncCompleted()));
    }

    //  Helper method to post events from doOnCompleted.
    private Action0 postEventSafelyAction(final Object event) {
        return new Action0() {
            @Override
            public void call() {
                mEventPoster.postEventSafely(event);
            }
        };
    }

}
