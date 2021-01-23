package com.oxs.ocdxsunnah.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.oxs.ocdxsunnah.Notification.NotifAlarmAkhir;

public class AlarmAkhirReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotifAlarmAkhir notif = new NotifAlarmAkhir(context);
        NotificationCompat.Builder nb = notif.getChannelNotification();
        notif.getManager().notify(0, nb.build());

    }
}