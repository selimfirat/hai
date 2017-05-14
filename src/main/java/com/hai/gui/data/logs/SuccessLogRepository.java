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
public class SuccessLogRepository {

    private static SuccessLogRepository _instance;

    private SuccessLogRepository(){}

    public static SuccessLogRepository getInstance() {
        if (_instance == null)
            _instance = new SuccessLogRepository();

        return _instance;
    }

    public void saveRecords(Map<String, Double> scores, String date) {
        if (scores.size() == 0)
            return;

        try {

            String query = "INSERT INTO success_logs (title, date, success) VALUES (?, ?, ?)";

            PreparedStatement statement = DB.prepareStatement(query);

            for (String moduleName : scores.keySet()) {
                String delQ = "DELETE FROM success_logs WHERE date = ? AND title = ?";
                PreparedStatement pDelQ = DB.prepareStatement(delQ);
                pDelQ.setString(1, date);
                pDelQ.setString(2, moduleName);
                pDelQ.execute();

                statement.setString(1, moduleName);
                statement.setString(2, date);
                statement.setDouble(3, scores.get(moduleName));
                statement.addBatch();
            }

            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
