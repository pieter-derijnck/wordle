package Domain;

// import Connections.DatabaseManager; // Voor later!

public class DomeinController {

    private WordleGame currentGame;
    // private DatabaseManager dbManager; // Hier komt straks je repo/database!

    public DomeinController() {
        // this.dbManager = new DatabaseManager();
    }

    // De CUI roept dit aan om een potje te starten
    public void startNewGame() {
        this.currentGame = new WordleGame();
    }

    // De CUI geeft de ingetypte letters door
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

    // --- HIER KOMT DE MAGIE VOOR DE CUI ---
    // In plaats van dat de CUI 'Board' en 'Tile' objecten moet importeren en begrijpen,
    // geeft de DC gewoon kant-en-klare tekst (of arrays) terug die de CUI domweg kan printen.

    public String[] getBoardAsText() {
        // TODO: Haal de rijen op uit currentGame.getBoard()
        // en bouw ze om naar 6 Strings (bijvoorbeeld: "[A:CORRECT] [P:PRESENT] ...")
        // Dan hoeft de CUI alleen maar System.out.println te doen op deze Strings!
        return new String[6];
    }
}