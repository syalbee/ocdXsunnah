package com.oxs.ocdxsunnah.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.oxs.ocdxsunnah.Notification.NotifAwal;

public class NotifAwalReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotifAwal notifAwal = new NotifAwal(context);
        NotificationCompat.Builder nb = notifAwal.getChannelNotification();
        notifAwal.getManager().notify(0, nb.build());

    }
}