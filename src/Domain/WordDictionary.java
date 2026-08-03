package Domain;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordDictionary {
    private List<String> validWords;
    private Random random;

    public WordDictionary(String filePath) {
        this.random = new Random();
        try {
            this.validWords = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            System.out.println("Fout bij het laden van het woordenbestand: " + e.getMessage());
        }
    }

    public String getRandomTargetWord() {
        if(validWords.isEmpty()) {return null;}
        int randomIndex = random.nextInt(validWords.size());
        return validWords.get(randomIndex);
    }

    public boolean isValidGuess(String guess) {
        if(validWords.isEmpty()) return false;
        for(int i = 0; i < validWords.size(); i++) {
            if(validWords.get(i).equalsIgnoreCase(guess)) {
                return true;
            }
        }
        return false;
    }

    public List<String> getValidWords() {
        return validWords;
    }
}