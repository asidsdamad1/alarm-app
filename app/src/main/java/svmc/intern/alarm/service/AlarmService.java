package svmc.intern.alarm.service;

import static svmc.intern.alarm.application.App.CHANNEL_ID;
import static svmc.intern.alarm.models.Alarm.ALARM;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;


import svmc.intern.alarm.R;
import svmc.intern.alarm.RingActivity;


public class AlarmService extends Service {
    private static final String TAG = "AlarmService";
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
        mediaPlayer.setLooping(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        Intent notificationIntent = new Intent(this, RingActivity.class);
        String[] alarm = intent.getStringArrayExtra(ALARM);
        notificationIntent.putExtra(ALARM, alarm);
        int pendingFlags;
        if (Build.VERSION.SDK_INT >= 23) {
            pendingFlags = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE;
        } else {
            pendingFlags = PendingIntent.FLAG_UPDATE_CURRENT;
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, pendingFlags);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(alarm[0])
                .setContentText(alarm[1])
                .setSmallIcon(Integer.parseInt(alarm[4]))
                .setContentIntent(pendingIntent).build();

//        mediaPlayer = MediaPlayer.create(this, Integer.parseInt(alarm[5]));

        mediaPlayer.setVolume(Float.parseFloat(alarm[2]), Float.parseFloat(alarm[2]));
        mediaPlayer.start();

        long[] pattern = {0, 100, 1000};
        if (Boolean.parseBoolean(alarm[3]))
            vibrator.vibrate(pattern, 1);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(notificationIntent);

        startForeground(Integer.parseInt(alarm[6]), notification);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mediaPlayer.stop();
        vibrator.cancel();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
