package Cui;


import Domain.WordleGame;
import java.util.Scanner;

public class Start {
    private WordleGame domainController;
    private Scanner scanner;

    public Start() {
        // We maken de controller en de scanner aan
        this.domainController = new WordleGame();
        this.scanner = new Scanner(System.in);
    }

    public void speelSpel() {
        System.out.println("Welkom bij Wordle!");

        while (!domainController.isGameOver()) {
            System.out.print("Voer een woord in: ");
            String invoer = scanner.nextLine();

            // De UI geeft de input simpelweg door aan de Domain Controller
            domainController.submitGuess(invoer);

            // TODO: Print het bord uit (bijv. via domainController.getBoard())
        }

        if (domainController.isGameWon()) {
            System.out.println("Gefeliciteerd, je hebt gewonnen!");
        } else {
            System.out.println("Helaas, je beurten zijn op.");
        }
    }
}