package com.hai.gui.domain.modules.cwdb_similarity;

import info.debatty.java.stringsimilarity.Levenshtein;
import java.util.Comparator;
/**
 * Created by eliztekcan on 30.04.2017.
 */
public class LevenshteinComparator implements Comparator<String>
{
    static Levenshtein l;
    String s;
    public LevenshteinComparator(String s){
        l = new Levenshtein();
        this.s = s;
    }

    public int compare(String s1, String s2){
        if(l.distance(s2,s) - l.distance(s1,s) > 0)
            return -1;
        else if (l.distance(s2,s) - l.distance(s1,s) == 0)
            return 0;
        else
            return 1;
    }
}
