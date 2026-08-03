package Domain;

public class DomeinController {

    private WordleGame currentGame;

    public DomeinController() {}

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

    public char[][] getBoardLetters() {
        char[][] letters = new char[6][5];
        GuessRow[] rows = currentGame.getBoard().getRows();
        for (int i = 0; i < 6; i++) {
            if (rows[i] != null) {
                for (int j = 0; j < 5; j++) {
                    letters[i][j] = Character.toUpperCase(rows[i].getTiles()[j].getLetter());
                }
            } else {
                for (int j = 0; j < 5; j++) letters[i][j] = ' ';
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
                    statuses[i][j] = rows[i].getTiles()[j].getStatus().name();
                }
            } else {
                for (int j = 0; j < 5; j++) statuses[i][j] = "EMPTY";
            }
        }
        return statuses;
    }

    // Roept de stabiele calculator gok op
    public String getBesteCalculatorGok() {
        if (currentGame == null) return "SLATE";
        return currentGame.getBesteGok();
    }


}