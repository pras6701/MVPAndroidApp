package com.mvp.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mvp.app.MVPApplication;
import com.mvp.app.data.IDataManager;
import com.mvp.app.service.SyncService;

import javax.inject.Inject;


/**
 * Starts sync service on boot completed if user is signed in.
 */
public class BootCompletedReceiver extends BroadcastReceiver {

    @Inject
    IDataManager dataManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        MVPApplication.get(context).getComponent().inject(this);
        if (dataManager.getPreferencesHelper().getAccessToken() != null) {
           context.startService(SyncService.getStartIntent(context));
        }
    }

}
