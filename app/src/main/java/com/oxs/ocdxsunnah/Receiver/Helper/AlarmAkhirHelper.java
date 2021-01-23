package com.oxs.ocdxsunnah.Receiver.Helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.oxs.ocdxsunnah.Service.AlarmAkhirService;

public class AlarmAkhirHelper extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.stopService(new Intent(context, AlarmAkhirService.class));

    }
}
