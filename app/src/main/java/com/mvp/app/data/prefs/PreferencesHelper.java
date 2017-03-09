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
  /*  public void putSignedInRibot(Ribot ribot) {
        mSharedPreferences.edit().putString(PREF_KEY_SIGNED_IN_RIBOT, mGson.toJson(ribot)).apply();
    }

    @Nullable
    public Ribot getSignedInRibot() {
        String ribotJson = mSharedPreferences.getString(PREF_KEY_SIGNED_IN_RIBOT, null);
        if (ribotJson == null) return null;
        return mGson.fromJson(ribotJson, Ribot.class);
    }

    public void putVenues(List<Venue> venues) {
        mSharedPreferences.edit().putString(PREF_KEY_VENUES, mGson.toJson(venues)).apply();
    }

    @Nullable
    public List<Venue> getVenues() {
        String venuesJson = mSharedPreferences.getString(PREF_KEY_VENUES, null);
        if (venuesJson != null) {
            return mGson.fromJson(venuesJson, new TypeToken<List<Venue>>() {
            }.getType());
        }
        return null;
    }

    public Observable<List<Venue>> getVenuesAsObservable() {
        return Observable.create(new Observable.OnSubscribe<List<Venue>>() {
            @Override
            public void call(Subscriber<? super List<Venue>> subscriber) {
                List<Venue> venues = getVenues();
                if (venues != null) {
                    subscriber.onNext(venues);
                }
                subscriber.onCompleted();
            }
        });
    }

    public void putLatestCheckIn(CheckIn checkIn) {
        mSharedPreferences.edit().putString(PREF_KEY_LATEST_CHECK_IN, mGson.toJson(checkIn)).apply();
    }

    @Nullable
    public CheckIn getLatestCheckIn() {
        String checkInJson = mSharedPreferences.getString(PREF_KEY_LATEST_CHECK_IN, null);
        if (checkInJson != null) {
            return mGson.fromJson(checkInJson, CheckIn.class);
        }
        return null;
    }

    public Observable<CheckIn> getLatestCheckInAsObservable() {
        return Observable.create(new Observable.OnSubscribe<CheckIn>() {
            @Override
            public void call(Subscriber<? super CheckIn> subscriber) {
                CheckIn checkIn = getLatestCheckIn();
                if (checkIn != null) {
                    subscriber.onNext(checkIn);
                }
                subscriber.onCompleted();
            }
        });
    }

    *//**
     * Return the RegisteredBeacon object related to the latest successful encounter
     * or null if no encounter has been performed yet on this device.
     *//*
    @Nullable
    public RegisteredBeacon getLatestEncounterBeacon() {
        String beaconJson = mSharedPreferences.getString(PREF_KEY_LATEST_ENCOUNTER_BEACON, null);
        if (beaconJson != null) {
            return mGson.fromJson(beaconJson, RegisteredBeacon.class);
        }
        return null;
    }

    *//**
     * Return the date of the latest successful encounter
     * or null if no encounter has been performed yet on this device.
     *//*
    @Nullable
    public Date getLatestEncounterDate() {
        long time = mSharedPreferences.getLong(PREF_KEY_LATEST_ENCOUNTER_DATE, -1);
        if (time > 0) {
            return new Date(time);
        }
        return null;
    }

    *//**
     * Return the check-in ID of the latest encounter.
     *//*
    @Nullable
    public String getLatestEncounterCheckInId() {
        return mSharedPreferences.getString(PREF_KEY_LATEST_ENCOUNTER_CHECK_IN_ID, null);
    }*/

  /*  *//**
     * Save an Encounter object
     * This overrides the encounter beacon, encounter date and latest check-in.
     *//*
    public void putLatestEncounter(Encounter encounter) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(PREF_KEY_LATEST_CHECK_IN, mGson.toJson(encounter.checkIn));
        editor.putString(PREF_KEY_LATEST_ENCOUNTER_CHECK_IN_ID, encounter.checkIn.id);
        editor.putString(PREF_KEY_LATEST_ENCOUNTER_BEACON, mGson.toJson(encounter.beacon));
        editor.putLong(PREF_KEY_LATEST_ENCOUNTER_DATE, encounter.encounterDate.getTime());
        editor.apply();
    }*/

  /*  public void clearLatestEncounter() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(PREF_KEY_LATEST_ENCOUNTER_CHECK_IN_ID);
        editor.remove(PREF_KEY_LATEST_ENCOUNTER_BEACON);
        editor.remove(PREF_KEY_LATEST_ENCOUNTER_DATE);
        editor.apply();
    }*/

}
