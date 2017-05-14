package com.hai.gui.data.candidate;

import com.hai.gui.data.DB;
import com.hai.gui.data.csp.Domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
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

    public Map<String, Map<String, Double>> getFetchedDomains(String date) {
        Map<String, Map<String, Double>> domains = new HashMap<>();

        return null;
    }

    public void saveCandidates(String clueNum, String puzzleDate, String moduleName, List<Candidate> candidates) {
        if (candidates.size() == 0)
            return;

        try {
            String delQ = "DELETE FROM candidates_modules WHERE clue_id = ? AND date = ?";

            PreparedStatement pDelQ = DB.prepareStatement(delQ);
            pDelQ.setString(1, clueNum);
            pDelQ.setString(2, puzzleDate);
            pDelQ.execute();

            String query = "INSERT INTO candidates_modules (clue_id, word, score, module, date) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement statement = DB.prepareStatement(query);

            for (Candidate candidate : candidates) {
                statement.setString(1, clueNum);
                statement.setString(2, candidate.getWord());
                statement.setDouble(3, candidate.getScore());
                statement.setString(4, moduleName);
                statement.setString(5, puzzleDate);
                statement.addBatch();
            }

            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void saveCombinedCandidates(String puzzleDate, Map<String, Domain> domains) {

        String delQ = "DELETE FROM candidates_puzzles WHERE clue_id = ? AND date = ?";

        try {
            PreparedStatement pDelQ = DB.prepareStatement(delQ);
            pDelQ.setString(1, puzzleDate);
            pDelQ.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (String clueId : domains.keySet()) {
            TreeSet<Candidate> candidates = domains.get(clueId).getCandidates();
            try {
                String query = "INSERT INTO candidates_puzzles (clue_id, word, normalized_score, date) VALUES (?, ?, ?, ?)";

                PreparedStatement statement = DB.prepareStatement(query);

                for (Candidate candidate : candidates) {
                    statement.setString(1, clueId);
                    statement.setString(2, candidate.getWord());
                    statement.setDouble(3, candidate.getScore());
                    statement.setString(4, puzzleDate);
                    statement.addBatch();
                }

                statement.executeBatch();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
