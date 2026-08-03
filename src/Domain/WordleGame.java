package Domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordleGame {
    private Board board;
    private WordDictionary dictionary;
    private String targetWord;
    private boolean isGameOver;
    private boolean isGameWon;

    private List<String> huidigeMogelijkeWoorden;

    public WordleGame(String dictionaryFilePath) {
        this.dictionary = new WordDictionary(dictionaryFilePath);
        startNewGame();
    }

    public void startNewGame() {
        this.board = new Board();
        this.targetWord = dictionary.getRandomTargetWord();
        this.isGameOver = false;
        this.isGameWon = false;
        this.huidigeMogelijkeWoorden = new ArrayList<>(dictionary.getValidWords());
    }

    public void submitGuess(String guess) {
        guess = guess.toLowerCase();
        valideerInput(guess);

        GuessRow row = new GuessRow(guess);
        evalueerLetters(row);
        board.addGuess(row);

        filterMogelijkeWoorden(row);

        System.out.println("--- DEBUG CALCULATOR ---");
        System.out.println("Aantal overgebleven mogelijke woorden: " + huidigeMogelijkeWoorden.size());
        if (!huidigeMogelijkeWoorden.isEmpty()) {
            if (huidigeMogelijkeWoorden.size() <= 10) {
                System.out.println("Mogelijke woorden: " + huidigeMogelijkeWoorden);
            } else {
                System.out.println("Eerste 5 suggesties: " + huidigeMogelijkeWoorden.subList(0, 5));
            }
        }

        updateGameStatus(row);
    }

    private void filterMogelijkeWoorden(GuessRow row) {
        Tile[] tiles = row.getTiles();

        for (int i = 0; i < 5; i++) {
            char letter = Character.toLowerCase(tiles[i].getLetter());
            StatusLetter status = tiles[i].getStatus();
            final int pos = i;

            huidigeMogelijkeWoorden.removeIf(woord -> {
                char wChar = Character.toLowerCase(woord.charAt(pos));
                if (status == StatusLetter.CORRECT) {
                    return wChar != letter;
                } else if (status == StatusLetter.PRESENT) {
                    return !woord.contains(String.valueOf(letter)) || wChar == letter;
                } else if (status == StatusLetter.ABSENT) {
                    boolean isEldersGroenOfGeel = false;
                    for (int j = 0; j < 5; j++) {
                        if (Character.toLowerCase(tiles[j].getLetter()) == letter && tiles[j].getStatus() != StatusLetter.ABSENT) {
                            isEldersGroenOfGeel = true;
                            break;
                        }
                    }
                    if (isEldersGroenOfGeel) {
                        return wChar == letter;
                    } else {
                        return woord.contains(String.valueOf(letter));
                    }
                }
                return false;
            });
        }
    }

    // SLIMME CALCULATOR: Berekent het woord met de meeste unieke letters uit de resterende lijst!
    public String getBesteGok() {
        if (huidigeMogelijkeWoorden.isEmpty()) {
            return dictionary.getRandomTargetWord().toUpperCase();
        }

        String besteWoord = huidigeMogelijkeWoorden.get(0);
        int hoogsteScore = -1;

        for (String woord : huidigeMogelijkeWoorden) {
            int score = berekenWoordScore(woord);
            if (score > hoogsteScore) {
                hoogsteScore = score;
                besteWoord = woord;
            }
        }

        return besteWoord.toUpperCase();
    }

    // Hoe meer unieke letters in een woord, hoe hoger de score (want dat helpt om meer te elimineren)
    private int berekenWoordScore(String woord) {
        Set<Character> uniekeLetters = new HashSet<>();
        for (char c : woord.toCharArray()) {
            uniekeLetters.add(c);
        }
        return uniekeLetters.size();
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

    public Board getBoard() { return board; }
    public boolean isGameOver() { return isGameOver; }
    public boolean isGameWon() { return isGameWon; }
    public String getTargetWord() { return targetWord; }
}