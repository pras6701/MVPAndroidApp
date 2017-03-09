package com.mvp.app.data.db;

import android.database.Cursor;

import com.mvp.app.data.db.model.User;
import com.mvp.app.data.db.table.UserTable;
import com.mvp.app.data.network.model.MasterData;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

@Singleton
public class DbHelper implements IDbHelper {

    private final BriteDatabase mDb;

    @Inject
    public DbHelper(DbOpenHelper dbOpenHelper) {
        mDb = SqlBrite.create().wrapDatabaseHelper(dbOpenHelper);
    }

    public BriteDatabase getDatabase() {
        return mDb;
    }

    /**
     * Remove all the data from all the tables in the database.
     */
    public Observable<Void> clearTables() {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    Cursor cursor = mDb.query("SELECT name FROM sqlite_master WHERE type='table'");
                    while (cursor.moveToNext()) {
                        mDb.delete(cursor.getString(cursor.getColumnIndex("name")), null);
                    }
                    cursor.close();
                    transaction.markSuccessful();
                    subscriber.onCompleted();
                } finally {
                    transaction.end();
                }
            }
        });
    }

    // Delete all beacons in table and add the new ones.
    public Observable<Void> setUserDetails(final User user) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    mDb.insert(UserTable.TABLE_NAME,
                                UserTable.toContentValues(user));
                    transaction.markSuccessful();
                    subscriber.onCompleted();
                } finally {
                    transaction.end();
                }
            }
        });
    }

    public Observable<User> getLoggedInUserDetails() {
        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                Cursor cursor = mDb.query(
                        "SELECT * FROM " + UserTable.TABLE_NAME +" LIMIT 1");
                while (cursor.moveToNext()) {
                    subscriber.onNext(UserTable.parseCursor(cursor));
                }
                cursor.close();
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Void> setMasterData(MasterData masterData) {
        return Observable.empty();
    }

    @Override
    public Observable<Long> insertUser(User user) {
        return null;
    }
}
