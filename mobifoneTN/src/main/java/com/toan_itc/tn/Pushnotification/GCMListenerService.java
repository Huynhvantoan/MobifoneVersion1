package com.toan_itc.tn.Pushnotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.toan_itc.tn.Activity.MainActivity;
import com.toan_itc.tn.Network.ApiController;
import com.toan_itc.tn.R;

/**
 * Created by brunogabriel on 1/21/16.
 */
public class GCMListenerService  extends GcmListenerService {
    private static final String GCM_LISTENER_DEBUG = "GCM_LISTENER_DEBUG";
    /**
     * This function will be called when a new message is received
     * @param from SenderID reference
     * @param data bundle that contains the message
     */
    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        Log.d(GCM_LISTENER_DEBUG, "Data receive: " + data);
        sendNotification(data);
    }

    /**
     * This function will be called to show a single notification
     * @param data
     */
    private void sendNotification(Bundle data) {
        if(data!=null) {
            try {
                String mTitle = data.getString("title", "");
                String mMessage = data.getString("message", "");
                String mdata = data.getString("data", "");
                SharedPreferences sharedPreferences = this.getSharedPreferences(ApiController.APPTN, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString(ApiController.PUSH_TITLE, mTitle);
                editor.putString(ApiController.PUSH_MESSAGE, mMessage);
                editor.putString(ApiController.PUSH_DATA, mdata);
                editor.commit();
                // Clear activity top or open, as necessary, you need to implement your rule
                Intent mIntent = new Intent(this, MainActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mIntent.putExtra(ApiController.PUSH_MESSAGE,mMessage);
                PendingIntent mPendingIntent = PendingIntent.getActivity(this, 0, mIntent,
                        PendingIntent.FLAG_ONE_SHOT);

                // Notification sound alert (default from device)
                Uri mDefaultSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                processNotifyMessage(this, mTitle, mMessage,mdata);
                // This class will create local notification with image, message etc.
                NotificationCompat.Builder mNotificationBuilder =
                        new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(mTitle)
                        .setContentText(mMessage)
                        .setTicker(mMessage)
                        .setAutoCancel(true)
                        .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(mMessage))
                        .setAutoCancel(true)
                        .setSound(mDefaultSoundURI)
                        .setContentIntent(mPendingIntent);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, mNotificationBuilder.build());
            } catch (Exception e) {
                Log.d(GCM_LISTENER_DEBUG, "Exception on sendNotification: " + e.getMessage());
            }
        }
    }
    private void processNotifyMessage(Context context, String title, String message,String data) {
        Intent intent = new Intent(ApiController.PUSH_NOTIFICATION);
        intent.putExtra(ApiController.PUSH_TITLE, title);
        intent.putExtra(ApiController.PUSH_MESSAGE, message);
        intent.putExtra(ApiController.PUSH_DATA, data);
        context.sendBroadcast(intent);
    }
}
