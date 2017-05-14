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
import com.hai.gui.presentation.MrsHai;
import org.bouncycastle.math.raw.Mod;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by mrsfy on 30-Apr-17.
 */
public class Merger {

    private Logger LOG = Logger.getLogger(Merger.class.getName());
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
        moduleWeights.put(RestModule.N_LENGTH.name(), 0.05);
        moduleWeights.put(RestModule.SYNONYMS_ANTONYMS.name(), 0.10);
        moduleWeights.put(RestModule.WIKI_TITLES_SEARCH.name(), 0.15);
        moduleWeights.put(Module.CWDB_N_LENGTH.name(), 0.10);
        moduleWeights.put(Module.CWDB_SIMILARITY.name(), 0.25);
    }

    public Puzzle getPuzzle(){
        return puzzle;
    }

    public Map<String, Domain> getDomains() {

        Map<String, Domain> domains = new HashMap<>();

        LOG.log(MrsHai.LEVEL, "Now, I have started to obtain candidates for each clue from modules.");


        List<Clue> allClues = puzzle.getClues().getA();
        allClues.addAll(puzzle.getClues().getD());

        puzzle.getClues().getA().parallelStream().forEach(clue -> {
            boolean isAcross = clue.getClueEnd() - clue.getClueStart() < 5;
            Map<String, Double> candidates = useModules(clue, isAcross);

            double sumScores = candidates.values().stream().reduce((a,b)->a+b).get();
            for (String cKey: candidates.keySet())
                candidates.put(cKey, candidates.get(cKey) / sumScores);

            domains.put((isAcross ? "A" : "D") + clue.getClueNum(), new Domain(candidates));
        });

        LOG.log(MrsHai.LEVEL, "I have obtained candidates from modules.");

        LOG.log(MrsHai.LEVEL, "I'm normalizing the scores for candidates.");
        mergerModuleTest.normalizeScores();

        LOG.log(MrsHai.LEVEL, "I'm saving test results for modules.");
        SuccessLogRepository.getInstance().saveRecords(mergerModuleTest.getScores(), today);

        LOG.log(MrsHai.LEVEL, "I'm saving normalized & combined candidates of this puzzle.");
        CandidatesRepository.getInstance().saveCombinedCandidates(today, domains);

        return domains;
    }

    private Map<String, Double> useModules(Clue clue, boolean isAcross) {
        String clueId = (isAcross ? "A" : "D") + clue.getClueNum();

        Map<String, Double> res = new HashMap<>();

        Arrays.stream(RestModule.values()).parallel().forEach(module -> {
            if (module == RestModule.BING_SEARCH)
                return;

            System.out.println("Using " + module.name() + " module for clue of " + clueId + ".");
            long startTime = System.currentTimeMillis();

            List<Candidate> candidates = new ArrayList<>(); //CandidatesRepository.getInstance().getFetchedCandidates(today, module.name(), clueId);
            if (candidates.size() == 0)
                candidates = restClient.useModule(module, clue.getValue(), clue.getAnswerLength(isAcross));

            CandidatesRepository.getInstance().saveCandidates(clueId, today, module.name(), candidates);

            double sumScores = candidates.stream().mapToDouble(Candidate::getScore).sum();
            candidates.forEach(candidate -> {
                if (!res.containsKey(candidate.getWord()))
                    res.put(candidate.getWord(), moduleWeights.get(module.name()) * candidate.getScore() / sumScores);
                else
                    res.put(candidate.getWord(), res.get(candidate.getWord()) + moduleWeights.get(module.name()) * candidate.getScore() / sumScores);
            });

            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            TimerLogRepository.getInstance().addRecord(clueId, module.name(), elapsedTime, today);

            mergerModuleTest.calculateScore(module.name(), (isAcross ? "A" : "D") + clue.getClueNum(), candidates);
        });


        Arrays.stream(Module.values()).parallel().forEach(module -> {
            if (module != Module.CWDB_SIMILARITY)
                return;

            System.out.println("Using " + module.name() + " module for clue of " + clueId + ".");
            long startTime = System.currentTimeMillis();

            List<Candidate> candidates = new ArrayList<>(); //CandidatesRepository.getInstance().getFetchedCandidates(today, module.name(), clueId);
            if (candidates.size() == 0) {
                switch (module) {
                    case CWDB_SIMILARITY:
                        Similarity similarity = new Similarity(clue.getValue(), clue.getAnswerLength(isAcross));
                        candidates = new ArrayList<>(similarity.getAnswers(DB.getConnection()));
                        break;
                    case CWDB_N_LENGTH:
                        candidates = new ArrayList<>(NLengthCWDB.getAnswers(DB.getConnection(), clue.getAnswerLength(isAcross)));
                        break;
                }
            }

            CandidatesRepository.getInstance().saveCandidates(clueId, today, module.name(), candidates);

            double sumScores = candidates.stream().mapToDouble(Candidate::getScore).sum();
            candidates.forEach(candidate -> {
                if (!res.containsKey(candidate.getWord()))
                    res.put(candidate.getWord(), moduleWeights.get(module.name()) * candidate.getScore() / sumScores);
                else
                    res.put(candidate.getWord(), res.get(candidate.getWord()) + (moduleWeights.get(module.name()) * (candidate.getScore() / sumScores)));
            });
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            TimerLogRepository.getInstance().addRecord(clueId, module.name(), elapsedTime, today);

            mergerModuleTest.calculateScore(module.name(), (isAcross ? "A" : "D") + clue.getClueNum(), candidates);

        });

        return res;
    }
}