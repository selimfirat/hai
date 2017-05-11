package com.hai.gui.data.candidate;

import com.hai.gui.data.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by mrsfy on 09-May-17.
 */
public class CandidatesRepository {

    private static CandidatesRepository _instance;

    private CandidatesRepository(){}

    public static CandidatesRepository getInstance() {
        if (_instance == null)
            _instance = new CandidatesRepository();

        return _instance;
    }

    public void saveCandidates(String clueNum, String puzzleDate, String moduleName, List<Candidate> candidates) {
        if (candidates.size() == 0)
            return;

        try {
            String query = "INSERT INTO candidates_modules (clue_id, word, score, module, date) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement statement = DB.prepareStatement(query);

            for (Candidate candidate : candidates) {
                statement.setString(1, clueNum);
                statement.setString(2, candidate.getWord());
                statement.setDouble(3, candidate.getScore());
                statement.setString(4, moduleName);
                statement.setString(5, puzzleDate);
            }

            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
