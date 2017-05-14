package com.hai.gui.data.logs;

import com.hai.gui.data.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by mrsfy on 09-May-17.
 */
public class TimerLogRepository {

    private static TimerLogRepository _instance;

    private TimerLogRepository(){}

    public static TimerLogRepository getInstance() {
        if (_instance == null)
            _instance = new TimerLogRepository();

        return _instance;
    }

    public void addRecord(String clueId, String title, long time, String date) {

        try {
            String delQ = "DELETE FROM timer_logs WHERE date = ? AND clue_id = ? AND title = ?";

            PreparedStatement pDelQ = DB.prepareStatement(delQ);
            pDelQ.setString(1, date);
            pDelQ.setString(2, clueId);
            pDelQ.setString(3, title);
            pDelQ.execute();


            String query = "INSERT INTO timer_logs (clue_id, title, time, date) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = DB.prepareStatement(query);

            statement.setString(1, clueId);
            statement.setString(2, title);
            statement.setLong(3, time);
            statement.setString(4, date);

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
