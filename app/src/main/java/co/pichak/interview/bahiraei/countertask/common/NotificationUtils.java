package co.pichak.interview.bahiraei.countertask.common;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;

import co.pichak.interview.bahiraei.countertask.R;

/**
 * Created by bigoloo on 4/6/15.
 */
public class NotificationUtils {

    public  void showNotification(Context context,Class clazz,int current) {
        Intent intent = new Intent(context, clazz);

        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);

        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(String.format(context.getString(R.string.current_string), current)).
                        setContentIntent(pIntent).setAutoCancel(false).build();
        NotificationManager notificationManager =
                (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        notification.flags |= NotificationCompat.FLAG_NO_CLEAR;
        notificationManager.notify(0, notification);
    }

}
