package Domain;

public class DomeinController {

    private WordleGame currentGame;

    public DomeinController() {
        // Nog geen database nodig voor we het spel werkend hebben!
    }

    public void startNewGame() {
        this.currentGame = new WordleGame("src/Utils/words");
    }

    public void submitGuess(String guess) {
        if (currentGame != null) {
            currentGame.submitGuess(guess);
        }
    }

    public boolean isGameOver() {
        return currentGame != null && currentGame.isGameOver();
    }

    public boolean isGameWon() {
        return currentGame != null && currentGame.isGameWon();
    }

    public String getTargetWord() {
        return currentGame != null ? currentGame.getTargetWord() : "";
    }

    public String[] getBoardAsText() {
        if (currentGame == null) return new String[0];

        String[] output = new String[6];
        GuessRow[] rows = currentGame.getBoard().getRows();

        // Loop door de 6 rijen van het bord
        for (int i = 0; i < 6; i++) {
            if (rows[i] == null) {
                // Deze rij is nog leeg
                output[i] = "[ ] [ ] [ ] [ ] [ ]";
            } else {
                // Deze rij is ingevuld, bouw de tekst op
                String rowText = "";
                Tile[] tiles = rows[i].getTiles();
                for (int j = 0; j < 5; j++) {
                    // Zet de letter om naar hoofdletters voor de mooiste weergave
                    char letter = Character.toUpperCase(tiles[j].getLetter());
                    rowText += "[" + letter + ":" + tiles[j].getStatus() + "] ";
                }
                output[i] = rowText;
            }
        }
        return output;
    }
    public char[][] getBoardLetters() {
        char[][] letters = new char[6][5];
        GuessRow[] rows = currentGame.getBoard().getRows();
        for (int i = 0; i < 6; i++) {
            if (rows[i] != null) {
                for (int j = 0; j < 5; j++) {
                    letters[i][j] = Character.toUpperCase(rows[i].getTiles()[j].getLetter());
                }
            } else {
                for (int j = 0; j < 5; j++) letters[i][j] = ' '; // Leeg vakje
            }
        }
        return letters;
    }

    public String[][] getBoardStatuses() {
        String[][] statuses = new String[6][5];
        GuessRow[] rows = currentGame.getBoard().getRows();
        for (int i = 0; i < 6; i++) {
            if (rows[i] != null) {
                for (int j = 0; j < 5; j++) {
                    statuses[i][j] = rows[i].getTiles()[j].getStatus().name(); // Dit geeft "CORRECT", "PRESENT", etc.
                }
            } else {
                for (int j = 0; j < 5; j++) statuses[i][j] = "EMPTY";
            }
        }
        return statuses;
    }

}