package svmc.intern.alarm.service;

import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import java.util.List;

import svmc.intern.alarm.models.Alarm;
import svmc.intern.alarm.models.AlarmDao;
import svmc.intern.alarm.models.AlarmDatabase;

public class RescheduleAlarmsService extends LifecycleService {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        AlarmDatabase db = Room.databaseBuilder(getApplicationContext(), AlarmDatabase.class, "database-alarm").allowMainThreadQueries().build();
        AlarmDao alarmDao = db.alarmDao();
        List<Alarm> alarmList = alarmDao.getAlarms();
        for (Alarm a : alarmList) {
            if (a.isActive()) {
                a.schedule(getApplicationContext());
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return null;
    }
}
