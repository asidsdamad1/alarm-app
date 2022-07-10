package svmc.intern.alarm.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import svmc.intern.alarm.R;
import svmc.intern.alarm.models.Alarm;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmHolder> {

    private final List<Alarm> alarmList;
    private final IAlarmClick iAlarmClick;

    public AlarmAdapter(List<Alarm> alarmList, IAlarmClick iAlarmClick) {
        this.alarmList = alarmList;
        this.iAlarmClick = iAlarmClick;
    }

    @NonNull
    @Override
    public AlarmHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, parent, false);
        return new AlarmHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AlarmHolder holder, @SuppressLint("RecyclerView") int position) {
        Alarm alarm = alarmList.get(position);
        holder.tvAlarmItemTime.setText(alarm.getStringHour() + ":" + alarm.getStringMinute());
        holder.tvAlarmItemAM.setText(alarm.getAMToString());
        holder.tvAlarmItemRepeat.setText(alarm.getRepeatToString());
        holder.tvAlarmItemTitle.setText(alarm.getTitle());
        holder.swAlarmItemActive.setChecked(alarm.isActive());
        holder.imAlarmItemIcon.setImageResource(alarm.getIdIcon());

        holder.itemView.setOnClickListener(view -> {
            iAlarmClick.onItemClick(position);
        });
        holder.itemView.setOnLongClickListener(view -> {
            iAlarmClick.onItemLongClick(position);
            return false;
        });

        holder.swAlarmItemActive.setOnClickListener(view -> {
            iAlarmClick.onActiveClick(position, holder.swAlarmItemActive.isChecked());
        });

    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    static class AlarmHolder extends RecyclerView.ViewHolder {
        TextView tvAlarmItemRepeat;
        TextView tvAlarmItemTime;
        TextView tvAlarmItemTitle;
        TextView tvAlarmItemAM;
        ImageView imAlarmItemIcon;
        Switch swAlarmItemActive;

        public AlarmHolder(@NonNull View itemView) {
            super(itemView);
            tvAlarmItemAM = itemView.findViewById(R.id.tv_alarm_item_am);
            tvAlarmItemTitle = itemView.findViewById(R.id.tv_alarm_item_title);
            tvAlarmItemRepeat = itemView.findViewById(R.id.tv_alarm_item_repeat);
            tvAlarmItemTime = itemView.findViewById(R.id.tv_alarm_item_time);
            imAlarmItemIcon = itemView.findViewById(R.id.im_alarm_item_icon);
            swAlarmItemActive = itemView.findViewById(R.id.sw_alarm_item_active);
        }
    }
}
