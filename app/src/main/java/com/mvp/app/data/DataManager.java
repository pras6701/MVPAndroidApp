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
    /**
     * Sign in with a Google account.
     * 1. Retrieve an google auth code for the given account
     * 2. Sends code and account to API
     * 3. If success, saves ribot profile and API access token in preferences
     */
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

    /*public Observable<List<Ribot>> getRibots() {
        String auth = IApiHelper.Util.buildAuthorization(mPreferencesHelper.getAccessToken());
        return mApiHelper.fetchMasterData(auth, "latestCheckIn");
    }*/

    /**
     * Retrieve list of venues. Behaviour:
     * 1. Return cached venues (empty list if none is cached)
     * 2. Return API venues (if different to cached ones)
     * 3. Save new venues from API in cache
     * 5. If an error happens and cache is not empty, returns venues from cache.
     */
   /* public Observable<List<Venue>> getVenues() {
        String auth = IApiHelper.Util.buildAuthorization(mPreferencesHelper.getAccessToken());
        return mApiHelper.getVenues(auth)
                .doOnNext(new Action1<List<Venue>>() {
                    @Override
                    public void call(List<Venue> venues) {
                        mPreferencesHelper.putVenues(venues);
                    }
                })
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends List<Venue>>>() {
                    @Override
                    public Observable<? extends List<Venue>> call(Throwable throwable) {
                        return getVenuesRecoveryObservable(throwable);
                    }
                })
                .startWith(mPreferencesHelper.getVenuesAsObservable())
                .distinct();
    }*/

    // Returns venues from cache. If cache is empty, it forwards the error.
    /*private Observable<List<Venue>> getVenuesRecoveryObservable(Throwable error) {
        return mPreferencesHelper.getVenuesAsObservable()
                .switchIfEmpty(Observable.<List<Venue>>error(error));
    }*/

    /**
     * Performs a manual check in, either at a venue or a location.
     * Use CheckInRequest.fromVenue() or CheckInRequest.fromLabel() to create the request.
     * If the the check-in is successful, it's saved as the latest check-in.
     */
  /*  public Observable<CheckIn> checkIn(CheckInRequest checkInRequest) {
        String auth = IApiHelper.Util.buildAuthorization(mPreferencesHelper.getAccessToken());
        return mApiHelper.checkIn(auth, checkInRequest)
                .doOnNext(new Action1<CheckIn>() {
                    @Override
                    public void call(CheckIn checkIn) {
                        mPreferencesHelper.putLatestCheckIn(checkIn);
                    }
                });
    }*/

    /**
     * Marks a previous check-in as "checkedOut" and updates the value in preferences
     * if the check-in matches the latest check-in.
     */
  /*  public Observable<CheckIn> checkOut(final String checkInId) {
        String auth = IApiHelper.Util.buildAuthorization(mPreferencesHelper.getAccessToken());
        return mApiHelper.updateCheckIn(auth, checkInId,
                new IApiHelper.UpdateCheckInRequest(true))
                .doOnNext(new Action1<CheckIn>() {
                    @Override
                    public void call(CheckIn checkInUpdated) {
                        CheckIn latestCheckIn = mPreferencesHelper.getLatestCheckIn();
                        if (latestCheckIn != null && latestCheckIn.id.equals(checkInUpdated.id)) {
                            mPreferencesHelper.putLatestCheckIn(checkInUpdated);
                        }
                        String encounterCheckInId =
                                mPreferencesHelper.getLatestEncounterCheckInId();
                        if (encounterCheckInId != null &&
                                encounterCheckInId.equals(checkInUpdated.id)) {
                            mPreferencesHelper.clearLatestEncounter();
                        }
                    }
                });
    }*/

    /**
     * Returns today's latest manual check in, if there is one.
     */
/*    public Observable<CheckIn> getTodayLatestCheckIn() {
        return mPreferencesHelper.getLatestCheckInAsObservable()
                .filter(new Func1<CheckIn, Boolean>() {
                    @Override
                    public Boolean call(CheckIn checkIn) {
                        return DateUtil.isToday(checkIn.checkedInDate.getTime());
                    }
                });
    }*/

   /* public Observable<Encounter> performBeaconEncounter(String beaconId) {
        String auth = IApiHelper.Util.buildAuthorization(mPreferencesHelper.getAccessToken());
        return mApiHelper.performBeaconEncounter(auth, beaconId)
                .doOnNext(new Action1<Encounter>() {
                    @Override
                    public void call(Encounter encounter) {
                        mPreferencesHelper.putLatestEncounter(encounter);
                    }
                });
    }
*/
   /* public Observable<Encounter> performBeaconEncounter(String uuid, int major, int minor) {
        Observable<RegisteredBeacon> errorObservable = Observable.error(
                new BeaconNotRegisteredException(uuid, major, minor));
        return mDbHelper.findRegisteredBeacon(uuid, major, minor)
                .switchIfEmpty(errorObservable)
                .concatMap(new Func1<RegisteredBeacon, Observable<Encounter>>() {
                    @Override
                    public Observable<Encounter> call(RegisteredBeacon registeredBeacon) {
                        return performBeaconEncounter(registeredBeacon.id);
                    }
                });
    }*/

   /* public Observable<String> findRegisteredBeaconsUuids() {
        return mDbHelper.findRegisteredBeaconsUuids();
    }*/

   public Observable<Void> syncMasterData() {
       // String auth = IApiHelper.Util.buildAuthorization(mPreferencesHelper.getAccessToken());
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
