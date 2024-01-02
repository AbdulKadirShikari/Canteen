package com.canteenautomation.canteeen.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.canteenautomation.canteeen.ItemHistory;
import com.canteenautomation.canteeen.R;
import com.canteenautomation.canteeen.sohel.S;
import com.canteenautomation.canteeen.sohel.SavedData;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private String action;
    private String notificationTitle;
    private String msg;
    private NotificationUtils notificationUtils;
    private boolean message;
    String strjson;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From:" + remoteMessage.getFrom());

        if (remoteMessage == null) return;

        S.E("getData" + remoteMessage.getData());
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body:----" + remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                strjson= String.valueOf(json);
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleDataMessage(JSONObject json) {
        S.E("json" + json);
        try {
            JSONObject data = json.getJSONObject("data");
            action = data.getString("action");
            notificationTitle = data.getString("title");
            JSONObject message = data.getJSONObject("message");
            msg = message.getString("msg");
            S.E("msg::" + msg);
            S.E("action::" + action);
            S.E("notificationTitle::" + notificationTitle);
        } catch (JSONException e) {
            S.E("JSONException" + e);
            e.printStackTrace();
        }
        showNotification();
    }


    private void sendNotification() {
        Intent intent = new Intent(this, ItemHistory.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.canteen_logo001)
                .setContentTitle(notificationTitle)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setContentText(msg)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void showNotification() {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            Log.e("check if", "......");
            // app is not in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", String.valueOf(strjson));
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            Log.e("Message  ", "" + strjson);
            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        } else {
            S.E("newdistributor");
            sendNotification();

        }
    }

    private void showNotificationMessageWithBigImage(Context context, String title, String action, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, action, message, timeStamp, intent, imageUrl);
    }


}