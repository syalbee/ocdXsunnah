package com.oxs.ocdxsunnah.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.oxs.ocdxsunnah.Database.DatabaseInit;
import com.oxs.ocdxsunnah.R;
import com.oxs.ocdxsunnah.Receiver.AlarmAkhirReceiver;
import com.oxs.ocdxsunnah.Receiver.AlarmAwalReceiver;
import com.oxs.ocdxsunnah.Receiver.AlarmSahurReceiver;
import com.oxs.ocdxsunnah.Receiver.NotifAkhirReceiver;
import com.oxs.ocdxsunnah.Receiver.NotifAwalReceiver;
import com.oxs.ocdxsunnah.Retrofit.ApiService;
import com.oxs.ocdxsunnah.Service.AlarmAkhirService;
import com.oxs.ocdxsunnah.Service.AlarmAwalService;
import com.oxs.ocdxsunnah.Service.AlarmSahurService;
import com.oxs.ocdxsunnah.Models.ImsakModels;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity {

    Switch nf, am, as;
    TextView waktuText;
    Button btLogout;
    ImageButton btBack;

    private int jamSahur, menitSahur;
    private final int endhour = 16;
    private final int hour = 12;
    private int lama;
    private String mode;

    DatabaseInit db = new DatabaseInit();


    private void getDataFromApi() {
        ApiService.endpoint().getData()
                .enqueue(new Callback<ImsakModels>() {
                    @Override
                    public void onResponse(Call<ImsakModels> call, Response<ImsakModels> response) {
                        if (response.isSuccessful()) {
                            ArrayList<ImsakModels.Times> time = response.body().getResults().getDatetime();
                            String hour = time.get(0).getTimes().getImsak().substring(0, 2);
                            String minute = time.get(0).getTimes().getImsak().substring(3);

                            jamSahur = Integer.parseInt(hour) - 1;
                            menitSahur = Integer.parseInt(minute);
                        }
                    }

                    @Override
                    public void onFailure(Call<ImsakModels> call, Throwable t) {
                        Log.d("SettingActivity", t.toString());
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        nf = (Switch) findViewById(R.id.notif);
        am = (Switch) findViewById(R.id.alarmMakan);
        as = (Switch) findViewById(R.id.alarmSahur);
        waktuText = (TextView) findViewById(R.id.textSahur);
        btLogout = (Button) findViewById(R.id.btLogout);
        btBack = (ImageButton) findViewById(R.id.btBack);

        saveState();
        getDataFromApi();
        stopService();

        db.user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseUser firebaseUser = db.firebaseAuth.getCurrentUser();
                

                String metode = snapshot.child(firebaseUser.getUid()).child("metode").getValue().toString();
                String endHours = snapshot.child(firebaseUser.getUid()).child("lama").getValue().toString();
                konversi(metode, endHours);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        nf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nf.isChecked()) {
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", true);
                    editor.apply();
                    SharedPreferences.Editor editor1 = getSharedPreferences("save3", MODE_PRIVATE).edit();
                    editor1.putBoolean("value", true);
                    editor1.apply();
                    SharedPreferences.Editor editor2 = getSharedPreferences("save4", MODE_PRIVATE).edit();
                    editor2.putBoolean("value", true);
                    editor2.apply();
                    nf.setChecked(true);
                    notifOn();
                    startNotif(hour);
                    startNotifAkhir(endhour);
                } else {
                    notifOff();
                    cancelAlarm(1);
                    cancelAlarm(2);
                    cancelAlarm(3);
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);
                    SharedPreferences.Editor editor1 = getSharedPreferences("save1", MODE_PRIVATE).edit();
                    editor1.putBoolean("value", false);
                    SharedPreferences.Editor editor2 = getSharedPreferences("save2", MODE_PRIVATE).edit();
                    editor2.putBoolean("value", false);
                    SharedPreferences.Editor editor3 = getSharedPreferences("save3", MODE_PRIVATE).edit();
                    editor3.putBoolean("value", false);
                    SharedPreferences.Editor editor4 = getSharedPreferences("save4", MODE_PRIVATE).edit();
                    editor4.putBoolean("value", false);
                    editor.apply();
                    editor1.apply();
                    editor2.apply();
                    editor3.apply();
                    editor4.apply();
                    nf.setChecked(false);
                }
            }
        });

        am.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (am.isChecked()) {
                    cancelAlarm(hour);
                    startAlarm(hour);
                    startNotifAlarm(hour);
                    startAlarmAkhir(endhour);
                    startNotifAlarmAkhir(endhour);

                    SharedPreferences.Editor editor = getSharedPreferences("save1", MODE_PRIVATE).edit();
                    editor.putBoolean("value", true);
                    editor.apply();
                    am.setChecked(true);
                } else {
                    cancelAlarm(2);
                    SharedPreferences.Editor editor = getSharedPreferences("save1", MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);
                    editor.apply();
                    am.setChecked(false);
                    startNotif(hour);
                    startNotifAkhir(endhour);
                }
            }
        });

        as.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (as.isChecked()) {
                    startSahur(jamSahur, menitSahur);
                    startNotifSahur(jamSahur, menitSahur);

                    SharedPreferences.Editor editor = getSharedPreferences("save2", MODE_PRIVATE).edit();
                    editor.putBoolean("value", true);
                    editor.apply();
                    as.setChecked(true);
                } else {
                    cancelAlarm(3);
                    SharedPreferences.Editor editor = getSharedPreferences("save2", MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);
                    editor.apply();
                    as.setChecked(false);
                }
            }
        });

        db.googleSignInClient = GoogleSignIn.getClient(SettingActivity.this, GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            db.firebaseAuth.signOut();
                            finish();
                            Toast.makeText(SettingActivity.this, "Berhasil logout", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(SettingActivity.this, MenuActivity.class);
                startActivity(back);
            }
        });
        Toast.makeText(this, String.valueOf(lama), Toast.LENGTH_SHORT).show();
    }

    private void konversi(String a, String b){
        mode = a;
        if (mode.equalsIgnoreCase("ocd")) {
            as.setVisibility(View.INVISIBLE);
            waktuText.setVisibility(View.INVISIBLE);
        }
    }


    private void startNotif(int time) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, time);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotifAwalReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }

    private void startNotifAkhir(int time) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, time);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotifAkhirReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 2, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }

    private void startAlarm(int hour) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmAwalService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 1, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }

    private void startNotifAlarm(int hour) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmAwalReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 2, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }

    private void startAlarmAkhir(int hour) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmAkhirService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 3, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }

    private void startNotifAlarmAkhir(int hour) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmAkhirReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 4, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }

    private void startSahur(int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour - 1);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmSahurService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 5, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }

    private void startNotifSahur(int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour - 1);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmSahurReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 6, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }

    private void cancelAlarm(int no) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (no == 1) {
            Intent intent = new Intent(this, NotifAwalReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
            alarmManager.cancel(pendingIntent);

            Intent intent1 = new Intent(this, NotifAkhirReceiver.class);
            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, 2, intent1, 0);
            alarmManager.cancel(pendingIntent1);
        } else if (no == 2) {
            Intent intent = new Intent(this, AlarmAwalService.class);
            PendingIntent pendingIntent = PendingIntent.getService(this, 1, intent, 0);
            alarmManager.cancel(pendingIntent);

            Intent intent1 = new Intent(this, AlarmAwalReceiver.class);
            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, 2, intent1, 0);
            alarmManager.cancel(pendingIntent1);

            Intent intent2 = new Intent(this, AlarmAkhirService.class);
            PendingIntent pendingIntent2 = PendingIntent.getService(this, 3, intent2, 0);
            alarmManager.cancel(pendingIntent2);

            Intent intent3 = new Intent(this, AlarmAkhirReceiver.class);
            PendingIntent pendingIntent3 = PendingIntent.getBroadcast(this, 4, intent3, 0);
            alarmManager.cancel(pendingIntent3);
        } else if (no == 3) {
            Intent intent = new Intent(this, AlarmSahurService.class);
            PendingIntent pendingIntent = PendingIntent.getService(this, 5, intent, 0);
            alarmManager.cancel(pendingIntent);

            Intent intent1 = new Intent(this, AlarmSahurReceiver.class);
            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, 6, intent1, 0);
            alarmManager.cancel(pendingIntent1);
        }
    }

    private void notifOn() {
        am.setEnabled(true);
        as.setEnabled(true);
    }

    private void notifOff() {
        am.setEnabled(false);
        as.setEnabled(false);
        am.setChecked(false);
        as.setChecked(false);
    }

    private void stopService() {
        stopService(new Intent(this, AlarmAwalService.class));
        stopService(new Intent(this, AlarmSahurService.class));
        stopService(new Intent(this, AlarmAkhirService.class));
    }

    private void saveState() {
        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        nf.setChecked(sharedPreferences.getBoolean("value", false));
        SharedPreferences sharedPreferences1 = getSharedPreferences("save1", MODE_PRIVATE);
        am.setChecked(sharedPreferences1.getBoolean("value", false));
        SharedPreferences sharedPreferences2 = getSharedPreferences("save2", MODE_PRIVATE);
        as.setChecked(sharedPreferences2.getBoolean("value", false));

        SharedPreferences sharedPreferences3 = getSharedPreferences("save3", MODE_PRIVATE);
        am.setEnabled(sharedPreferences3.getBoolean("value", false));
        SharedPreferences sharedPreferences4 = getSharedPreferences("save4", MODE_PRIVATE);
        as.setEnabled(sharedPreferences4.getBoolean("value", false));
    }


}