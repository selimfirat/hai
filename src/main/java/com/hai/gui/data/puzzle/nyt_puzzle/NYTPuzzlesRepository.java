package com.hai.gui.data.puzzle.nyt_puzzle;

import com.hai.gui.data.puzzle.Puzzle;
import com.hai.gui.data.puzzle.PuzzleContainer;
import javafx.util.converter.LocalDateStringConverter;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by mrsfy on 08-Apr-17.
 */
public class NYTPuzzlesRepository {

    private static NYTPuzzlesRepository _instance;

    private NYTPuzzlesRepository() { }

    private MongoCollection puzzles;

    public static NYTPuzzlesRepository getInstance() {
        if (_instance == null)
            _instance = new NYTPuzzlesRepository();

        return _instance;
    }

    public void setJongo(Jongo jongo) {
        this.puzzles = jongo.getCollection("puzzles");
    }

    public Puzzle findPuzzle(LocalDate date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateStr = dateTimeFormatter.format(date);

        return puzzles.findOne("{ print_date: # }", dateStr).as(PuzzleContainer.class).getPuzzle_data();
    }


}
