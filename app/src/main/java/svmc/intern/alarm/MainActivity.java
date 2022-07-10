package svmc.intern.alarm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import svmc.intern.alarm.adapters.AlarmAdapter;
import svmc.intern.alarm.adapters.IAlarmClick;
import svmc.intern.alarm.models.Alarm;
import svmc.intern.alarm.models.AlarmDao;
import svmc.intern.alarm.models.AlarmDatabase;

public class MainActivity extends AppCompatActivity implements IAlarmClick {
    static String TAG ="MainActivity";
    private RecyclerView rcAlarm;
    private AlarmAdapter alarmAdapter;
    private List<Alarm> alarmList;
    private String typeState = "";
    AlarmDatabase db;
    AlarmDao alarmDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = Room.databaseBuilder(getApplicationContext(), AlarmDatabase.class, "database-alarm").allowMainThreadQueries().build();
        alarmDao = db.alarmDao();
        alarmList = alarmDao.getAlarms();
        alarmAdapter = new AlarmAdapter(alarmList,this);
        rcAlarm = findViewById(R.id.rc_alarm);
        rcAlarm.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        rcAlarm.setAdapter(alarmAdapter);
    }


    public void handleOpenAdd(View view) {
        Intent intent = new Intent(this,AlarmEditActivity.class);
        Alarm alarm = new Alarm();
        intent.putExtra("alarm", alarm);
        typeState="ADD";
        startActivityForResult(intent,1);
    }


    public void refreshAlarmList(){
        alarmList.clear();
        alarmList.addAll(alarmDao.getAlarms());
        Log.i(TAG,alarmList.toString());
        alarmAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int pos) {
        Intent intent = new Intent(this,AlarmEditActivity.class);
        Alarm alarm = alarmList.get(pos);
        intent.putExtra("alarm",alarm);
        typeState="EDIT";
        startActivityForResult(intent,1);
    }

    @Override
    public void onItemLongClick(int pos) {

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Do you want to remove?");
        b.setPositiveButton("Yes", (dialog, id) -> {
            alarmDao.delete(alarmList.get(pos));
            refreshAlarmList();
            Toast.makeText(MainActivity.this, "Delete Success", Toast.LENGTH_SHORT).show();
        });
        b.setNegativeButton("No", (dialog, id) -> dialog.cancel());
        AlertDialog al = b.create();
        al.show();

    }

    @Override
    public void onActiveClick(int pos,boolean active) {
        Alarm alarm = alarmList.get(pos);
        alarm.setActive(active);

        if (active)
            alarm.schedule(this);
        else
            alarm.cancelAlarm(this);

        alarmDao.update(alarm);
        refreshAlarmList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Alarm newAlarm = (Alarm)data.getSerializableExtra("newAlarm");
                if(typeState.equals("EDIT")){
                    alarmDao.update(newAlarm);
                    Toast.makeText(this, "Edit Success", Toast.LENGTH_SHORT).show();
                }else if(typeState.equals("ADD")){
                    alarmDao.insert(newAlarm);
                    Toast.makeText(this, "Create Success", Toast.LENGTH_SHORT).show();
                }
                if (newAlarm.isActive())
                    newAlarm.schedule(this);
                refreshAlarmList();
            }
        }
    }
}