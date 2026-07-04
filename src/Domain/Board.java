package Domain;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<GuessRow> rows;

    public Board() {
        this.rows = new ArrayList<>();
    }

    public void addGuess(GuessRow newRow) {
        this.rows.add(newRow);
    }
}
