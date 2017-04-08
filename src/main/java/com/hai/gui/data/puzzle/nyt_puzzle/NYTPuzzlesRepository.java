package com.hai.gui.data.puzzle.nyt_puzzle;

import com.hai.gui.data.puzzle.Puzzle;
import com.hai.gui.data.puzzle.PuzzleContainer;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by mrsfy on 08-Apr-17.
 */
public class NYTPuzzlesRepository {

    private static NYTPuzzlesRepository _instance;

    private NYTPuzzlesRepository() { }

    MongoCollection puzzles;

    public static NYTPuzzlesRepository getInstance() {
        if (_instance == null)
            _instance = new NYTPuzzlesRepository();

        return _instance;
    }

    public void setJongo(Jongo jongo) {
        this.puzzles = jongo.getCollection("puzzles");
    }

    public Puzzle findPuzzle(LocalDate date) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format1.format(date);

        return puzzles.findOne("{ print_date: # }", dateStr).as(PuzzleContainer.class).getPuzzle_data();
    }


}
