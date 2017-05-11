package com.hai.gui.domain.modules.cwdb_similarity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eliztekcan on 30.04.2017.
 */
public class DatabaseQuery {
    static final String FILENAME_CONNECTION = "/Users/eliztekcan/IdeaProjects/cwdb-importer/res/connectionString.txt";
    static final String FILENAME_CLUES = "/Users/eliztekcan/IdeaProjects/cwdb-importer/res/new_txt";

    public static List<String> getDistinctAnswers(Connection c)
    {
        List<String> list = new ArrayList<String>();
        String query = "SELECT DISTINCT answer FROM cwdb";
        try{
            Statement stmt = c.createStatement();
            ResultSet clueSet = stmt.executeQuery(query);
            System.out.println(clueSet.getMetaData().getColumnCount());
            while (clueSet.next()) {
                list.add(clueSet.getString("answer"));
                System.out.println(clueSet.getString("answer"));
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        return list;

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

            List<String> list = getDistinctAnswers(conn);
            System.out.println(list);
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

}
