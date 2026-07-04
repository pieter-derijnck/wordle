package Domain;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class WordDictionary {
    private List<String> validWords;

    // In de constructor geef je het pad (de locatie) naar je .txt bestand mee
    public WordDictionary(String filePath) {
        try {
            // Dit is een handige Java functie die alle regels uit een textbestand direct in een List zet!
            this.validWords = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            System.out.println("Fout bij het laden van het woordenbestand: " + e.getMessage());
        }
    }

    public String getRandomTargetWord() {
        // TODO: Kies een willekeurig woord uit de 'validWords' lijst
        return "";
    }

    public boolean isValidGuess(String guess) {
        // TODO: Check of de gok van de speler wel in de 'validWords' lijst staat
        return false;
    }
}
