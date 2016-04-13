package com.threef.lifenotes;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by shenglinfan on 16/4/4.
 */
public class AlarmReceiver extends BroadcastReceiver {

    private NotificationManager manager;

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.alarm.demo.action".equals(intent.getAction())) {

            manager = (NotificationManager)context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
            Intent playIntent = new Intent(context, EverydayActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle("每日小结").setSmallIcon(R.mipmap.ic_launcher).
                    setDefaults(Notification.DEFAULT_ALL).setContentIntent(pendingIntent).setAutoCancel(true);
            manager.notify(1, builder.build());
            Toast.makeText(context,"alarm",Toast.LENGTH_SHORT).show();

        }
    }
}
