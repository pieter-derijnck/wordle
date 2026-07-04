package Domain;

public class Tile {
    private char letter;
    private StatusLetter status;


    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public StatusLetter getStatus() {
        return status;
    }

    public void setStatus(StatusLetter status) {
        this.status = status;
    }
}
