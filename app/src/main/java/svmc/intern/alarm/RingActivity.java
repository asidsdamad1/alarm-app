package svmc.intern.alarm;

import static svmc.intern.alarm.models.Alarm.ALARM;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import svmc.intern.alarm.models.Alarm;
import svmc.intern.alarm.service.AlarmService;

public class RingActivity extends AppCompatActivity {

    ImageView imgRing;
    TextView txtTime, txtLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);
        Intent intent = getIntent();

        imgRing = findViewById(R.id.imgRing);
        txtTime = findViewById(R.id.timeRing);
        txtLabel = findViewById(R.id.labelRing);

        String[] alarm = intent.getStringArrayExtra(ALARM);
        imgRing.setImageResource(Integer.parseInt(alarm[4]));
        txtTime.setText(alarm[0]);
        txtLabel.setText(alarm[1]);

        findViewById(R.id.floatBtnClose).setOnClickListener(view -> {
            Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
            getApplicationContext().stopService(intentService);
            finish();
        });
        
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
        getApplicationContext().stopService(intentService);
    }


}