package svmc.intern.alarm.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AlarmDao {
    @Insert
    void insert(Alarm alarm);

    @Query("DELETE FROM alarm_table")
    void deleteAll();

    @Query("SELECT * FROM alarm_table")
    List<Alarm> getAlarms();

    @Update
    void update(Alarm alarm);

    @Delete
    void delete(Alarm alarm);
}
