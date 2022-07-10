package svmc.intern.alarm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.List;

import svmc.intern.alarm.models.Alarm;
import svmc.intern.alarm.models.AlarmDao;
import svmc.intern.alarm.models.AlarmDatabase;

public class AlarmEditActivity extends AppCompatActivity {
    static String TAG = "AlarmEditActivity";
    Alarm newAlarm;
    String type;
    TimePicker timePicker;
    ToggleButton btnMon, btnTue, btnWed, btnThu, btnFri, btnSat, btnSun;
    EditText edtTitle;
    SeekBar sbVolume;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch swVibration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_edit);
        mapping();
        Intent intent = getIntent();
        newAlarm = (Alarm) intent.getSerializableExtra("alarm");
        Log.i(TAG, newAlarm.toString());
        type = intent.getStringExtra("type");
        setDataToView();
    }


    public void mapping() {
        timePicker = findViewById(R.id.timePicker);

        btnMon = findViewById(R.id.btn_Mon);
        btnTue = findViewById(R.id.btn_Tue);
        btnWed = findViewById(R.id.btn_Web);
        btnThu = findViewById(R.id.btn_Thu);
        btnFri = findViewById(R.id.btn_Fri);
        btnSat = findViewById(R.id.btn_Sat);
        btnSun = findViewById(R.id.btn_Sun);

        edtTitle = findViewById(R.id.edt_alarm_title);
        sbVolume = findViewById(R.id.sb_volume);
        swVibration = findViewById(R.id.sw_alarm_vibration);
    }

    @SuppressLint("ResourceAsColor")
    public void setDataToView() {
        Log.i(TAG, newAlarm.toString());
        timePicker.setCurrentHour(newAlarm.getHour());
        timePicker.setCurrentMinute(newAlarm.getMinute());
        edtTitle.setText(newAlarm.getTitle());
        sbVolume.setProgress(newAlarm.getVolume());
        swVibration.setChecked(newAlarm.isVibration());

        btnMon.setChecked(newAlarm.isMon());
        btnTue.setChecked(newAlarm.isTue());
        btnWed.setChecked(newAlarm.isWed());
        btnThu.setChecked(newAlarm.isThu());
        btnFri.setChecked(newAlarm.isFri());
        btnSat.setChecked(newAlarm.isSat());
        btnSun.setChecked(newAlarm.isSun());

    }


    public void handleExit(View view) {
        AlarmEditActivity.super.onBackPressed();
    }

    public void handleSave(View view) {
        Log.i(TAG, "handle save");
        int volume = sbVolume.getProgress();
        newAlarm.setVolume(volume);

        newAlarm.setMon(btnMon.isChecked());
        newAlarm.setTue(btnTue.isChecked());
        newAlarm.setWed(btnWed.isChecked());
        newAlarm.setThu(btnThu.isChecked());
        newAlarm.setFri(btnFri.isChecked());
        newAlarm.setSat(btnSat.isChecked());
        newAlarm.setSun(btnSun.isChecked());


        newAlarm.setTitle(edtTitle.getText().toString());
        newAlarm.setHour(timePicker.getCurrentHour());
        newAlarm.setMinute(timePicker.getCurrentMinute());
        newAlarm.setVibration(swVibration.isChecked());

        if (checkAlarmOk(newAlarm)) {
            Intent intent = new Intent(AlarmEditActivity.this, MainActivity.class);
            intent.putExtra("newAlarm", newAlarm);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setTitle("You have not selected the date or time the alarm has been set?");
            b.setPositiveButton("Ok", (dialog, id) -> {
                dialog.cancel();
            });
            AlertDialog al = b.create();
            al.show();
        }
    }

    public boolean checkAlarmOk(Alarm alarm) {
        boolean dayOk = false;
        boolean timeOK = false;
        if (alarm.isMon() || alarm.isTue() || alarm.isWed() || alarm.isThu() || alarm.isFri() || alarm.isSat() || alarm.isSun()) {
            dayOk = true;
        }
        AlarmDatabase db = Room.databaseBuilder(getApplicationContext(), AlarmDatabase.class, "database-alarm").allowMainThreadQueries().build();
        AlarmDao alarmDao = db.alarmDao();
        List<Alarm> alarmList = alarmDao.getAlarms();
        if (alarmList.size() > 0){
            for (Alarm a : alarmList) {
                if (a.getAlarmId() == alarm.getAlarmId()){
                    timeOK = true;
                    break;
                }
                if ((a.getHour() != alarm.getHour() || a.getMinute() != alarm.getMinute())) {
                    timeOK = true;
                    break;
                } else {
                    timeOK = false;
                }
            }
        }else{
            timeOK = true;
        }


        return (dayOk && timeOK);
    }


}