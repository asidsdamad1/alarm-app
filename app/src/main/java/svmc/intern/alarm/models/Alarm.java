package svmc.intern.alarm.models;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Calendar;

import svmc.intern.alarm.R;
import svmc.intern.alarm.broadcastreceiver.AlarmReceiver;


@Entity(tableName = "alarm_table")
public class Alarm implements Serializable {
    public static final String ALARM = "ALARM";
    public static final String REPEAT = "REPEAT";
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int alarmId;

    private int hour, minute, sound;
    private String title;
    private int volume;
    private boolean isVibration, isActive, Mon, Tue, Wed, Thu, Fri, Sat, Sun;


    public Alarm(int alarmId, int hour, int minute, int sound, String title, int volume, boolean isVibration, boolean isActive, boolean mon, boolean tue, boolean wed, boolean thu, boolean fri, boolean sat, boolean sun) {
        this.alarmId = alarmId;
        this.hour = hour;
        this.minute = minute;
        this.sound = sound;
        this.title = title;
        this.volume = volume;
        this.isVibration = isVibration;
        this.isActive = isActive;
        Mon = mon;
        Tue = tue;
        Wed = wed;
        Thu = thu;
        Fri = fri;
        Sat = sat;
        Sun = sun;
    }

    public Alarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int today = calendar.get(Calendar.DAY_OF_WEEK);
        switch (today) {
            case Calendar.MONDAY:
                setMon(true);
                break;
            case Calendar.TUESDAY:
                setTue(true);
                break;
            case Calendar.WEDNESDAY:
                setWed(true);
                break;
            case Calendar.THURSDAY:
                setThu(true);
                break;
            case Calendar.FRIDAY:
                setFri(true);
                break;
            case Calendar.SATURDAY:
                setSat(true);
                break;
            case Calendar.SUNDAY:
                setSun(true);
                break;
        }
        this.isActive = true;
    }

    public Alarm(int hour, int minute, String title, int volume, boolean isVibration) {
        this.hour = hour;
        this.minute = minute;
        this.title = title;
        this.volume = volume;
        this.isVibration = isVibration;
    }

    public Alarm(int hour, int minute, int sound, String title, int volume, boolean isVibration, boolean isActive, boolean mon, boolean tue, boolean web, boolean thu, boolean fri, boolean sat, boolean sun) {
        this.hour = hour;
        this.minute = minute;
        this.sound = sound;
        this.title = title;
        this.volume = volume;
        this.isVibration = isVibration;
        this.isActive = isActive;
        Mon = mon;
        Tue = tue;
        Wed = web;
        Thu = thu;
        Fri = fri;
        Sat = sat;
        Sun = sun;
    }

    public boolean isMon() {
        return Mon;
    }

    public void setMon(boolean mon) {
        Mon = mon;
    }

    public boolean isTue() {
        return Tue;
    }

    public void setTue(boolean tue) {
        Tue = tue;
    }

    public boolean isWed() {
        return Wed;
    }

    public void setWed(boolean wed) {
        Wed = wed;
    }

    public boolean isThu() {
        return Thu;
    }

    public void setThu(boolean thu) {
        Thu = thu;
    }

    public boolean isFri() {
        return Fri;
    }

    public void setFri(boolean fri) {
        Fri = fri;
    }

    public boolean isSat() {
        return Sat;
    }

    public void setSat(boolean sat) {
        Sat = sat;
    }

    public boolean isSun() {
        return Sun;
    }

    public void setSun(boolean sun) {
        Sun = sun;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }


    public int getIdIcon() {
        if (5 < hour && hour < 9)
            return R.drawable.morning;
        if (9 < hour && hour < 19)
            return R.drawable.day;
        return R.drawable.night;
    }

    public int getHour() {
        return hour;
    }

    public String getStringHour() {
        if (hour > 12) {
            if (hour - 12 < 10)
                return "0" + (hour - 12);

            return String.valueOf(hour - 12);
        }

        if (hour < 10)
            return "0" + hour;
        return String.valueOf(hour);
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getStringMinute() {
        if (minute < 10)
            return "0" + minute;
        return String.valueOf(minute);
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSound() {
        return sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }

    public String getTitle() {
        if (title == null)
            return "Title";
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public boolean isVibration() {
        return isVibration;
    }

    public void setVibration(boolean vibration) {
        isVibration = vibration;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getAMToString() {
        if (hour < 12)
            return "AM";
        else
            return "PM";
    }

    public String getRepeatToString() {
        String s = "";
        int count = 0;
        if (Mon) {
            if (count > 0) {
                s += ",Mon";
            } else {
                s += "Mon";
            }
            count++;
        }

        if (Tue) {
            if (count > 0) {
                s += ",Tue";
            } else {
                s += "Tue";
            }
            count++;
        }

        if (Wed) {
            if (count > 0) {
                s += ",Wed";
            } else {
                s += "Wed";
            }
            count++;
        }

        if (Thu) {
            if (count > 0) {
                s += ",Thu";
            } else {
                s += "Thu";
            }
            count++;
        }

        if (Fri) {
            if (count > 0) {
                s += ",Fri";
            } else {
                s += "Fri";
            }
            count++;
        }

        if (Sat) {
            if (count > 0) {
                s += ",Sat";
            } else {
                s += "Sat";
            }
            count++;

        }

        if (Sun) {
            if (count > 0) {
                s += ",Sun";
            } else {
                s += "Sun";
            }
            count++;
        }


        if (count == 7) {
            s = "Weekdays";
        }

        return s;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "alarmId=" + alarmId +
                ", hour=" + hour +
                ", minute=" + minute +
                ", sound=" + sound +
                ", title='" + title + '\'' +
                ", volume=" + volume +
                ", isVibration=" + isVibration +
                ", isActive=" + isActive +
                ", Mon=" + Mon +
                ", Tue=" + Tue +
                ", Wed=" + Wed +
                ", Thu=" + Thu +
                ", Fri=" + Fri +
                ", Sat=" + Sat +
                ", Sun=" + Sun +
                '}';
    }

    public void schedule(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        String[] alarm = new String[]{getStringHour() + ":" + getStringMinute(), title, String.valueOf(volume), String.valueOf(isVibration), String.valueOf(getIdIcon()), String.valueOf(sound),String.valueOf(alarmId)};
        boolean[] repeat = new boolean[]{isMon(), isTue(), isWed(), isThu(), isFri(), isSat(), isSun()};
        intent.putExtra(ALARM, alarm);
        intent.putExtra(REPEAT, repeat);

        int pendingFlags;
        if (Build.VERSION.SDK_INT >= 23) {
            pendingFlags = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE;
        } else {
            pendingFlags = PendingIntent.FLAG_UPDATE_CURRENT;
        }


        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, alarmId, intent, pendingFlags
        );

        String toastText = String.format("Alarm active for %02d:%02d", hour, minute);
        Log.i("time", toastText);
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (calendar.getTimeInMillis() > System.currentTimeMillis())
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent
            );
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        int pendingFlags;
        if (Build.VERSION.SDK_INT >= 23) {
            pendingFlags = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE;
        } else {
            pendingFlags = PendingIntent.FLAG_UPDATE_CURRENT;
        }
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, pendingFlags);
        alarmManager.cancel(alarmPendingIntent);
        this.setActive(false);
        String toastText = String.format("Alarm cancelled for %02d:%02d", hour, minute);
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
        Log.i("cancel", toastText);

    }


}
