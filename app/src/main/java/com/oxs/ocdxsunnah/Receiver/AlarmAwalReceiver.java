package com.oxs.ocdxsunnah.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.oxs.ocdxsunnah.Notification.NotifAlarmAwal;

public class AlarmAwalReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotifAlarmAwal notif = new NotifAlarmAwal(context);
        NotificationCompat.Builder nb = notif.getChannelNotification();
        notif.getManager().notify(0, nb.build());
    }
}
