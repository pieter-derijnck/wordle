package Domain;

public class WordleGame {
    private static final String WORDS_FILE_PATH = "src/Utils/words.txt";
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
        // TODO: 1. Valideer de gok via dictionary.isValidGuess()
        // TODO: 2. Maak een nieuwe GuessRow aan
        // TODO: 3. Vergelijk de letters met 'targetWord' en pas de LetterStatus van de Tiles aan
        // TODO: 4. Voeg de rij toe aan het bord
        // TODO: 5. Check of het spel gewonnen is, of dat de beurten op zijn (isGameOver)
    }
}
