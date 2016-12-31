package com.adorgolap.assunnahtrustqa;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.adorgolap.as_sunnahtrustqa.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by ifta on 12/31/16.
 */

public class MyFirebaseMessagingService  extends FirebaseMessagingService {
    private static final String KEY_UPDATE = "update";
    private static final String TAG = "iftaLog";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional
        Log.i(TAG, "From: " + remoteMessage.getFrom());
        Log.i(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        //Calling method to generate notification
        sendNotification(remoteMessage);
    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();
        String msg= remoteMessage.getNotification().getBody();
        if(title == null || title.equals(""))
        {
            title = getApplicationName(getApplicationContext());
        }
        Intent intent = new Intent(this, MainActivity.class);;
        if(title.toLowerCase().contains("update") || msg.toLowerCase().contains("update"))
        {
            Log.i(TAG,"sync trigg");
            intent.putExtra(C.KEY_UPDATE,true);
        }else {
            Log.i(TAG,"sync not trigg");
            intent.putExtra(C.KEY_UPDATE,false);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ast_logo_notification)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
    public static String getApplicationName(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
    }
}