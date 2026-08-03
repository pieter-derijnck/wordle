package Domain;

public class WordleGame {
    private static final String WORDS_FILE_PATH = "src/Utils/words";
    private Board board;
    private WordDictionary dictionary;
    private String targetWord;
    private boolean isGameOver;
    private boolean isGameWon;

    public WordleGame(String dictionaryFilePath) {
        this.dictionary = new WordDictionary(dictionaryFilePath);
        startNewGame();
    }

    public void startNewGame() {
        this.board = new Board();
        this.targetWord = dictionary.getRandomTargetWord();
        this.isGameOver = false;
        this.isGameWon = false;
    }

    public void submitGuess(String guess) {
        guess = guess.toLowerCase();

        valideerInput(guess);

        GuessRow row = new GuessRow(guess);

        evalueerLetters(row);


        board.addGuess(row);

        updateGameStatus(row);
    }

    private void valideerInput(String guess) {
        if (guess.length() != 5 || !dictionary.isValidGuess(guess)) {
            throw new IllegalArgumentException("Ongeldig woord! Probeer een bestaand woord van 5 letters.");
        }
    }

    private void updateGameStatus(GuessRow row) {
        if (row.isWinningGuess()) {
            isGameWon = true;
            isGameOver = true;
        } else if (board.isFull()) {
            isGameOver = true;
        }
    }
    private void evalueerLetters(GuessRow row) {
        Tile[] tiles = row.getTiles();
        boolean[] targetLetterGebruikt = new boolean[5];


        for (int i = 0; i < 5; i++) {
            if (tiles[i].getLetter() == targetWord.charAt(i)) {
                tiles[i].setStatus(StatusLetter.CORRECT);
                targetLetterGebruikt[i] = true;
            }
        }

        for (int i = 0; i < 5; i++) {
            if (tiles[i].getStatus() != StatusLetter.CORRECT) {
                boolean letterGevonden = false;

                for (int j = 0; j < 5; j++) {
                    if (!targetLetterGebruikt[j] && tiles[i].getLetter() == targetWord.charAt(j)) {
                        tiles[i].setStatus(StatusLetter.PRESENT);
                        targetLetterGebruikt[j] = true;
                        letterGevonden = true;
                        break;
                    }
                }

                if (!letterGevonden) {
                    tiles[i].setStatus(StatusLetter.ABSENT);
                }
            }
        }
    }
    public Board getBoard() {
        return board;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isGameWon() {
        return isGameWon;
    }

    public String getTargetWord() {
        return targetWord;
    }



}
