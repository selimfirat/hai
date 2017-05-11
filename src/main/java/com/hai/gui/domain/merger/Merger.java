package com.hai.gui.domain.merger;

import com.hai.gui.data.DB;
import com.hai.gui.data.candidate.CandidatesRepository;
import com.hai.gui.data.csp.Domain;
import com.hai.gui.data.logs.SuccessLogRepository;
import com.hai.gui.data.logs.TimerLogRepository;
import com.hai.gui.data.puzzle.Clue;
import com.hai.gui.data.puzzle.Puzzle;
import com.hai.gui.data.candidate.Candidate;
import com.hai.gui.domain.merger.rest_client.RestClient;
import com.hai.gui.domain.merger.rest_client.RestModule;
import com.hai.gui.domain.modules.Module;
import com.hai.gui.domain.modules.cwdb_nlength.NLengthCWDB;
import com.hai.gui.domain.modules.cwdb_similarity.Similarity;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;


/**
 * Created by mrsfy on 30-Apr-17.
 */
public class Merger {

    private Puzzle puzzle;
    private RestClient restClient;
    private MergerModuleTest mergerModuleTest;
    private String today;

    private Map<String, Double> moduleWeights = new HashMap<>();

    public Merger(Puzzle puzzle, LocalDate date) {
        this.puzzle = puzzle;
        restClient = new RestClient();
        mergerModuleTest = new MergerModuleTest(puzzle);
        this.today = date.format(DateTimeFormatter.ISO_LOCAL_DATE);

        moduleWeights.put(RestModule.BING_SEARCH.name(), 0.15);
        moduleWeights.put(RestModule.DATAMUSE_ANSWER_LIST.name(), 0.20);
        moduleWeights.put(RestModule.N_LENGTH.name(), .05);
        moduleWeights.put(RestModule.SYNONYMS_ANTONYMS.name(), 0.10);
        moduleWeights.put(RestModule.WIKI_TITLES_SEARCH.name(), 0.15);
        moduleWeights.put(Module.CWDB_N_LENGTH.name(), .10);
        moduleWeights.put(Module.CWDB_SIMILARITY.name(), 0.25);
    }

    public Puzzle getPuzzle(){
        return puzzle;
    }

    public Map<String, Domain> getDomains() {
        Map<String, Domain> domains = new HashMap<>();

        for (Clue clue : puzzle.getClues().getA()) {
            Map<String, Double> candidates = useModules(clue, true);
            domains.put("A" + clue.getClueNum(), new Domain(candidates));
        }

        for (Clue clue : puzzle.getClues().getD()){
            Map<String, Double> candidates = useModules(clue, false);
            domains.put("D" + clue.getClueNum(), new Domain(candidates));
        }

        mergerModuleTest.normalizeScores();
        SuccessLogRepository.getInstance().saveRecords(mergerModuleTest.getScores(), today);
        CandidatesRepository.getInstance().saveCombinedCandidates(today, domains);


        return domains;
    }

    private Map<String, Double> useModules(Clue clue, boolean isAcross) {
        String clueId = (isAcross ? "A" : "D") + clue.getClueNum();
        Map<String, Double> res = new HashMap<>();

        for (RestModule module : RestModule.values()) {
            long startTime = System.currentTimeMillis();

            List<Candidate> candidates = restClient.useModule(module, clue.getValue(), clue.getAnswerLength(isAcross));

            CandidatesRepository.getInstance().saveCandidates((isAcross ? "A" : "D") + clue.getClueNum(), today, module.name(), candidates);

            candidates.forEach(candidate -> {
                if (!res.containsKey(candidate.getWord()))
                    res.put(candidate.getWord(), candidate.getScore() / moduleWeights.get(module.name()));
                else
                    res.put(candidate.getWord(), res.get(clueId) + candidate.getScore()/moduleWeights.get(module.name()));
            });

            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            TimerLogRepository.getInstance().addRecord(clueId, module.name(), elapsedTime, today);

            mergerModuleTest.calculateScore(module.name(), (isAcross ? "A" : "D") + clue.getClueNum(), candidates);
        }


        for (Module module : Module.values()) {
            long startTime = System.currentTimeMillis();

            List<Candidate> candidates = new ArrayList<>();

            switch (module) {
                case CWDB_SIMILARITY:
                    Similarity similarity = new Similarity(clue.getValue(), clue.getAnswerLength(isAcross));
                    candidates = new ArrayList<>(similarity.getAnswers(DB.getConnection()));
                break;
                case CWDB_N_LENGTH:
                    candidates = new ArrayList<>(NLengthCWDB.getAnswers(DB.getConnection(), clue.getAnswerLength(isAcross)));
                break;
            }

            CandidatesRepository.getInstance().saveCandidates((isAcross ? "A" : "D") + clue.getClueNum(), today, module.name(), candidates);

            candidates.forEach(candidate -> {
                if (!res.containsKey(candidate.getWord()))
                    res.put(candidate.getWord(), candidate.getScore());
                else
                    res.put(candidate.getWord(), res.get(clueId) + candidate.getScore());
            });
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            TimerLogRepository.getInstance().addRecord(clueId, module.name(), elapsedTime, today);

            mergerModuleTest.calculateScore(module.name(), (isAcross ? "A" : "D") + clue.getClueNum(), candidates);
        }

        return res;
    }
}