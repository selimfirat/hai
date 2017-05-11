package com.hai.gui.domain.merger;

import com.hai.gui.data.DB;
import com.hai.gui.data.candidate.CandidatesRepository;
import com.hai.gui.data.csp.Domain;
import com.hai.gui.data.logs.SuccessLogRepository;
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


/**
 * Created by mrsfy on 30-Apr-17.
 */
public class Merger {

    private Puzzle puzzle;
    private LocalDate date;
    private RestClient restClient;
    private MergerModuleTest mergerModuleTest;
    private String today;

    public Merger(Puzzle puzzle, LocalDate date) {
        this.puzzle = puzzle;
        this.date = date;
        restClient = new RestClient();
        mergerModuleTest = new MergerModuleTest(puzzle);
        this.today = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public Puzzle getPuzzle(){
        return puzzle;
    }

    public Map<String, Domain> getDomains() {
        Map<String, Domain> domains = new HashMap<>();

        for (Clue clue : puzzle.getClues().getA())
                useModules(clue, domains, true);

        // comment
        for (Clue clue : puzzle.getClues().getD())
            useModules(clue, domains, false);


        mergerModuleTest.normalizeScores();
        SuccessLogRepository.getInstance().saveRecords(mergerModuleTest.getScores(), today);

        return domains;
    }

    private void useModules(Clue clue, Map<String, Domain> domains, boolean isAcross) {
        for (RestModule module : RestModule.values()) {
            System.out.println(clue.getValue());
            System.out.println(clue.getClueStart() + ", " + clue.getClueEnd());
            List<Candidate> candidates = restClient.useModule(module, clue.getValue(), clue.getAnswerLength(isAcross));

            CandidatesRepository.getInstance().saveCandidates((isAcross ? "A" : "D") + clue.getClueNum(), today, module.name(), candidates);
            domains.put((isAcross ? "A" : "D") + clue.getClueNum(), new Domain(candidates));
            mergerModuleTest.calculateScore(module.name(), (isAcross ? "A" : "D") + clue.getClueNum(), candidates);
        }


        for (Module module : Module.values()) {

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
            domains.put((isAcross ? "A" : "D") + clue.getClueNum(), new Domain(candidates));
            mergerModuleTest.calculateScore(module.name(), (isAcross ? "A" : "D") + clue.getClueNum(), candidates);
        }

    }
}