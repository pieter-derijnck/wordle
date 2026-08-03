package Domain;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class WordDictionary {
    private List<String> validWords;
    private Random random;

    // In de constructor geef je het pad (de locatie) naar je .txt bestand mee
    public WordDictionary(String filePath) {
        this.random = new Random();
        try {
            // Dit laadt alle regels uit het txt-bestand in een lijst[cite: 6]
            this.validWords = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            System.out.println("Fout bij het laden van het woordenbestand: " + e.getMessage());
        }
    }

    public String getRandomTargetWord() {
        // TODO: Kies een willekeurig woord uit de 'validWords' lijst
        if(validWords.isEmpty()) {return null;}
        int randomIndex = random.nextInt(validWords.size());
        return validWords.get(randomIndex);
    }

    public boolean isValidGuess(String guess) {
        // TODO: Check of de gok van de speler wel in de 'validWords' lijst staat
        if(validWords.isEmpty()) return false;
        for(int i = 0; i < validWords.size(); i++) {
            if(validWords.get(i).equals(guess)) {
                return true;
            }
        }return  false;
    }
}
