package com.threef.lifenotes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by shenglinfan on 16/4/4.
 */
public class BootReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
//            setAlarmTime(this,);
            //重新计算闹铃时间，并调第一步的方法设置闹铃时间及闹铃间隔时间

        }
    }

    private void setAlarmTime(Context context,  long timeInMillis) {
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent("android.alarm.demo.action");
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        int interval = 24 * 60 * 60 * 1000;
        am.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, interval, sender);
    }
}