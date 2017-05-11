package com.hai.gui.domain.modules.cwdb_nlength;

import com.hai.gui.data.candidate.Candidate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * Created by eliztekcan on 30.04.2017.
 */
public class NLengthCWDB {

    public static Set<Candidate> getAnswers(Connection c, int l)
    {
        Set<Candidate> set = new HashSet<Candidate>();
        String query = " SELECT answer FROM cwdb WHERE LEN(answer) = " + l;
        try{
            Statement stmt = c.createStatement();
            ResultSet answerSet = stmt.executeQuery(query);
            while (answerSet.next()) {
                set.add(new Candidate(answerSet.getString("answer"),1));
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return set;

    }
}
