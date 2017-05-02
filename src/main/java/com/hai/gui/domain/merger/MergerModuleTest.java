package com.hai.gui.domain.merger;

/**
 * Created by Sena on 2.05.2017.
 * this is to use individual modules and evaluate their accuracy and put them into a map<module name, score>
 *
 */

import com.hai.gui.data.csp.Domain;
import com.hai.gui.data.puzzle.Clue;
import com.hai.gui.data.puzzle.Puzzle;
import com.hai.gui.domain.merger.rest_client.Candidate;
import com.hai.gui.domain.merger.rest_client.RestClient;
import com.hai.gui.domain.merger.rest_client.RestModule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MergerModuleTest {

    private Puzzle puzzle;
    private RestClient restClient;
    private Map<String,Double> scoreList;

    public MergerModuleTest(Puzzle puzzle) {
        this.puzzle = puzzle;
        restClient = new RestClient();
        scoreList = new HashMap<>();
    }
    public Puzzle getPuzzle(){
        return puzzle;
    }
    private double calculateScore(List<Candidate> candidates){
        double score = 0;
        for(Candidate candidate: candidates){
            boolean found = false;
            for(String answer :puzzle.getAnswers())
            {
                if(answer.equals(candidate)){
                    found = true;
                    break;
                }
            }
            if(found){
                score += candidate.getScore();
            }
            else{
                score -= candidate.getScore();
            }
        }
        return score;
    }

    public Map<String, Domain> getDomains() {
        Map<String, Domain> domains = new HashMap<>();

        for (Clue clue : puzzle.getClues().getA())
            useModules(clue, domains, true);

        for (Clue clue : puzzle.getClues().getD())
            useModules(clue, domains, false);

        return domains;
    }

    void useModules(Clue clue, Map<String, Domain> domains, boolean isAcross) {
        for (RestModule module : RestModule.values()) {
            System.out.println(clue.getValue());
            System.out.println(clue.getClueStart() + ", " + clue.getClueEnd());
            List<Candidate> candidates = restClient.useModule(module, clue.getValue(), clue.getAnswerLength(isAcross));
            scoreList.put(module.toString(),scoreList.get(module.toString())+calculateScore(candidates));
            domains.put((isAcross ? "A" : "D") + clue.getClueNum(), new Domain(candidates));
        }
    }
}
