package svmc.intern.alarm.adapters;

public interface IAlarmClick {
    void onItemClick(int pos);
    void onItemLongClick(int pos);
    void onActiveClick(int pos,boolean active);
}
