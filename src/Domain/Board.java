package Domain;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private GuessRow[] rows;
    private int currentRowIndex;

    public Board() {
        this.rows = new GuessRow[6]; // Maximaal 6 pogingen
        this.currentRowIndex = 0;
    }

    public void addGuess(GuessRow row) {
        // TODO: Zet de nieuwe rij op de juiste plek in de array en verhoog de index
    }

    public boolean isFull() {
        // TODO: Geef true terug als alle 6 de rijen vol zijn
        return false;
    }

    public GuessRow[] getRows() {
        return rows;
    }
}
