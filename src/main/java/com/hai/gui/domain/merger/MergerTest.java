
/**
 * Created by Sena on 2.05.2017.
 * this is to use useModules function for all clues and
 * sort the domains with higher score
 */
package com.hai.gui.domain.merger;

import java.util.*;
import com.hai.gui.domain.merger.Merger;
import com.hai.gui.data.csp.Domain;
import com.hai.gui.data.puzzle.Clue;
import com.hai.gui.data.puzzle.CluesContainer;
import com.hai.gui.data.puzzle.Puzzle;
import com.hai.gui.domain.merger.rest_client.Candidate;
import com.hai.gui.domain.merger.rest_client.RestClient;
import com.hai.gui.domain.merger.rest_client.RestModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class MergerTest {

    private Merger merger;
    public MergerTest(Puzzle puzzle) {
        merger = new Merger(puzzle);
    }
    private static Map<String, Double> sortByValue(Map<String, Double> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<String, Double>> list =
                new LinkedList<Map.Entry<String, Double>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        /*
        //classic iterator example
        for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext(); ) {
            Map.Entry<String, Integer> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }*/

        return sortedMap;
    }

    private void testModules(){
        for(Domain d : merger.getDomains().values()){
            d.setCandidates(sortByValue(d.getCandidates()));
        }
    }
}