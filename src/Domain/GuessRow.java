package Domain;

import java.lang.reflect.Array;
import java.util.List;

public class GuessRow {
    private Tile[] tiles;

    public GuessRow(String guessedWord) {
        int length = 5;
        this.tiles = new Tile[length];
        // TODO: Loop door het 'guessedWord' heen en vul de array met 5 nieuwe Tile-objecten
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new Tile(guessedWord.charAt(i));
        }

    }

    public boolean isWinningGuess() {
        // TODO: Check of alle 5 de vakjes de status 'CORRECT' hebben
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].getStatus() != StatusLetter.CORRECT) {
                return false;
            }

        }
        return true;

    }

    public Tile[] getTiles() {
        return tiles;
    }
}

