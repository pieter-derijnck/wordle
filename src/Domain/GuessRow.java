package Domain;

import java.lang.reflect.Array;
import java.util.List;

public class GuessRow {
    private Tile[] tiles;

    public GuessRow(String guessedWord) {
        this.tiles = new Tile[5];
        // TODO: Loop door het 'guessedWord' heen en vul de array met 5 nieuwe Tile-objecten
    }

    public boolean isWinningGuess() {
        // TODO: Check of alle 5 de vakjes de status 'CORRECT' hebben
        return false;
    }

    public Tile[] getTiles() {
        return tiles;
    }
}

