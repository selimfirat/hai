package com.hai.gui.domain.merger;

/**
 * Created by Sena on 2.05.2017.
 * this is to use individual modules and evaluate their accuracy and put them into a map<module name, score>
 *
 */

import com.hai.gui.data.csp.Domain;
import com.hai.gui.data.puzzle.Clue;
import com.hai.gui.data.puzzle.Puzzle;
import com.hai.gui.data.candidate.Candidate;
import com.hai.gui.domain.merger.rest_client.RestClient;
import com.hai.gui.domain.merger.rest_client.RestModule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


public class MergerModuleTest {

    private Puzzle puzzle;
    private Map<String,Double> scores;
    private Map<String, String> answers;

    public MergerModuleTest(Puzzle puzzle) {
        this.puzzle = puzzle;
        scores = new HashMap<>();
        answers = new HashMap<>();

        String[] answersLayout = puzzle.getAnswers();

        puzzle.getClues().getA().forEach(clue -> {

            int start = clue.getClueStart();
            int end = clue.getClueEnd();

            StringBuilder answer = new StringBuilder();

            for (int i = start; i <= end; i++)
                    answer.append(answersLayout[i]);

            answers.put("A" + clue.getClueNum(), answer.toString());
        });

        puzzle.getClues().getD().forEach(clue -> {

            int start = clue.getClueStart();
            int end = clue.getClueEnd();

            StringBuilder answer = new StringBuilder();

            for (int i = start; i <= end; i+= 5)
                    answer.append(answersLayout[i]);

            answers.put("D" + clue.getClueNum(), answer.toString());
        });

        System.out.println("Merger Module Test answers " + answers);

    }

    public Puzzle getPuzzle(){
        return puzzle;
    }

    public void calculateScore(String module, String clueId, List<Candidate> candidates){
        System.out.println("The module " + module + " has suggested " + candidates.size() + " candidates for " + clueId);
        scores.putIfAbsent(module, 0.0);

        double score = scores.get(module);

        for(Candidate candidate : candidates)
            if (answers.get(clueId).equals(candidate.getWord())) {
                score += 1.0;
                System.out.println("The candidates that module " + module + " suggested contains the correct fill for " + clueId + ".");
                break;
            }

        scores.put(module, score);
    }

    public void normalizeScores() {

        for (String module : scores.keySet())
            scores.put(module, 100.0 * scores.get(module) / answers.size());

    }

    public Map<String, Double> getScores() {

        return scores;
    }


}
