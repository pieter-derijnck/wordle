package Domain;

public class Tile {
    private char letter;
    private StatusLetter status;

    public Tile(char letter) {
        this.letter = letter;
        this.status = StatusLetter.EMPTY; // Standaard is een vakje nog leeg/grijs
    }

    public char getLetter() {
        return letter;
    }

    public StatusLetter getStatus() {
        return status;
    }

    public void setStatus(StatusLetter status) {
        this.status = status;
    }
}
