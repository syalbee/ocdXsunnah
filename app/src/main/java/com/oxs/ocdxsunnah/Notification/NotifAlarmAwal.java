package com.oxs.ocdxsunnah.Notification;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.oxs.ocdxsunnah.MainActivity;
import com.oxs.ocdxsunnah.R;
import com.oxs.ocdxsunnah.Receiver.Helper.AlarmAwalHelper;

public class NotifAlarmAwal extends ContextWrapper {

    public static final String channelID1 = "channel1";
    public static final String channelName = "Channel Name";
    private NotificationManager mManager;

    public NotifAlarmAwal(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel1 = new NotificationChannel(channelID1, channelName, NotificationManager.IMPORTANCE_LOW);
        getManager().createNotificationChannel(channel1);

    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification() {

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intent1 = new Intent(this, AlarmAwalHelper.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(getApplicationContext(), channelID1)
                .setContentTitle("Pengingat")
                .setContentText("Waktu Makan Akan Segera Dimulai!")
                .setSmallIcon(R.mipmap.logo)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setColor(Color.GREEN)
                .addAction(R.mipmap.logo, "Berhenti", pendingIntent1);
    }
}