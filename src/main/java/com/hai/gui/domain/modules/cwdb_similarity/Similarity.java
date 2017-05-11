package com.hai.gui.domain.modules.cwdb_similarity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import com.hai.gui.data.candidate.Candidate;
import com.hai.gui.domain.modules.cwdb_similarity.LevenshteinComparator;
import info.debatty.java.stringsimilarity.Levenshtein;

/**
 * Created by eliztekcan on 30.04.2017.
 */
public class Similarity {
    static final String FILENAME_CONNECTION = "/Users/eliztekcan/IdeaProjects/cwdb-importer/res/connectionString.txt";
    static final String FILENAME_CLUES = "/Users/eliztekcan/IdeaProjects/cwdb-importer/res/new_txt";
    private PriorityQueue<String> pq;
    String baseClue;
    int l;

    public Similarity(String baseClue, int l){
        this.l = l;
        this.baseClue = baseClue;
        pq = new PriorityQueue<String>(new LevenshteinComparator(baseClue));
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        String arr[] = new String[4];
        //Read from file
        BufferedReader br = null;
        FileReader fr = null;
        BufferedReader br1 = null;
        FileReader fr1 = null;
        try {
            //READ: connection string
            fr = new FileReader(FILENAME_CONNECTION);
            br = new BufferedReader(fr);
            String sCurrentLine;

            br = new BufferedReader(new FileReader(FILENAME_CONNECTION));
            int i = 0;
            while ((sCurrentLine = br.readLine()) != null) {
                arr[i] = sCurrentLine;
                i++;
            }
            //CONNECTION
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(arr[0]);
            Similarity sim = new Similarity("A snit is a bad one", 5);
            Similarity sim1 = new Similarity("End-of-semester event", 4);
            Similarity sim2 = new Similarity("Vessels for whitewater rapids", 5);
            Similarity sim3 = new Similarity("Senator Orrin Hatch's state", 4);
            Similarity sim4 = new Similarity("Y.M.C.A facilities", 4);
            Similarity sim5 = new Similarity("Full of nuance, as an acting role", 5);
            Similarity sim6 = new Similarity("Global poverty org.", 5);
            Similarity sim7 = new Similarity("Solemn vows", 5);
            Similarity sim8 = new Similarity("Full of nuance, as an acting role", 3);


            Set<Candidate> s8 = sim8.getAnswers(conn);
            System.out.println();
            //String[] k = sim8.similarityTop20(conn);
            //for(int j=0; j<20;j++)
            //  System.out.println(s8);
            System.out.println(s8);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(2);
        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }
    }

    public String[] similarityTop20(Connection c){
        List<String> clues = getDistinctClues(c);
        int i = 0;
        while(i < clues.size()) {
            pq.add(clues.get(i));
            i++;
        }
        String[] similar = new String[20];
        for(int j = 0; j < 20; j++){
            similar[j] = pq.poll();
        }
        return similar;
    }

    public List<String> getDistinctClues(Connection c)
    {
        List<String> list = new ArrayList<>();
        String query = "SELECT DISTINCT clue FROM cwdb WHERE LEN(answer) =" + l;
        try{
            Statement stmt = c.createStatement();
            ResultSet clueSet = stmt.executeQuery(query);
            while (clueSet.next()) {
                list.add(clueSet.getString("clue"));
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        return list;

    }


    public Set<Candidate> getAnswers(Connection c)
    {
        Levenshtein k = new Levenshtein();
        Set<Candidate> set = new HashSet<Candidate>();
        String[] queries = new String[20];
        String[] similarClues = similarityTop20(c);
        for(int i = 0; i < 20; i++){
            similarClues[i] = similarClues[i].replace("\'","\'\'");
            queries[i] = " SELECT answer FROM cwdb WHERE clue = '" + similarClues[i] + "'";
        }
        try{
            Statement stmt = c.createStatement();
            for(int i = 0; i < 20; i++){
                ResultSet answerSet = stmt.executeQuery(queries[i]);
                while (answerSet.next()) {
                    set.add(new Candidate(answerSet.getString("answer"),Math.pow(1/(1 +k.distance(baseClue, similarClues[i])),2)));
                }
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return set;

    }
}