package com.example.carr_o.sync;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

//import java.sql.Driver;
import java.util.concurrent.TimeUnit;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

public class UpdateUtilities {
    private static final int UPDATE_INTERVAL_MINUTES = 60;
    private static final int UPDATE_INTERVAL_SECONDS = (int)
            (TimeUnit.MINUTES.toSeconds(UPDATE_INTERVAL_MINUTES));
    private static final int SYNC_FLEXTIME_SECONDS = UPDATE_INTERVAL_SECONDS;

    private static final String UPDATE_JOB_TAG = "news_update_tag";

    private static boolean sInitialized;

    synchronized public static void scheduleUpdate(@NonNull final Context context){
        if(sInitialized){return;}

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job constraintReminderJob = dispatcher.newJobBuilder()
                .setService(UpdateFirebaseJobService.class)
                .setTag(UPDATE_JOB_TAG)
                .setConstraints(Constraint.ON_UNMETERED_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(UPDATE_INTERVAL_SECONDS,
                        UPDATE_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();
        dispatcher.schedule(constraintReminderJob);
        sInitialized = true;
        Log.d("UPDATE UTILS", "scheduleUpdate: ");
    }
}
