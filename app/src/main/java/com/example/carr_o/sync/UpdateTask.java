package com.example.carr_o.sync;

import android.content.Context;
import android.util.Log;

import com.example.carr_o.utilities.NotificationUtils;

public class UpdateTask {
    public static final String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";
    public static final String UPDATE_NEWS = "update_news";
//    public static final String ACTION_OPEN_APP = "open-app";

    public static void executeTask(Context context, String action){
        if(ACTION_DISMISS_NOTIFICATION.equals(action)){
            NotificationUtils.clearAllNotifications(context);
        } else if(UPDATE_NEWS.equals(action)){
            Log.d("Updatetask", "executeTask: ");
//            UpdateTask instance = new UpdateTask();
//            instance.update();
            NotificationUtils.notifyNews(context);
        }

    }
}
