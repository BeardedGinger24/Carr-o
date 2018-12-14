package com.example.carr_o.sync;

import android.app.IntentService;
import android.content.Intent;

public class UpdateIntentService extends IntentService {
    public UpdateIntentService() {super("UpdateIntentService");}

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        UpdateTask.executeTask(this, action);
    }
}
