package Cui;

import Domain.DomeinController;
import java.util.Scanner;

public class Start {
    private DomeinController dc;
    private Scanner scanner;

    public Start() {
        // We maken de echte controller en de scanner aan
        this.dc = new DomeinController();
        this.scanner = new Scanner(System.in);
    }

    public void speelSpel() {
        dc.startNewGame();
        System.out.println("Welkom bij Wordle!");
        printBord();

        // Blijf vragen om invoer zolang het spel niet voorbij is
        while (!dc.isGameOver()) {
            System.out.print("Voer een 5-letter woord in: ");
            String invoer = scanner.nextLine();

            try {
                // De UI geeft de input simpelweg door aan de Domain Controller[cite: 9]
                dc.submitGuess(invoer);

                // Als het woord geldig was, printen we het vernieuwde bord
                printBord();

            } catch (IllegalArgumentException e) {
                // We vangen de foutmelding (bijv. "Ongeldig woord!") netjes op
                System.out.println("\nFout: " + e.getMessage());
                System.out.println("Probeer het opnieuw...\n");
            }
        }

        // Bepaal de eindtekst afhankelijk van winst of verlies[cite: 9]
        if (dc.isGameWon()) {
            System.out.println("Gefeliciteerd, je hebt gewonnen!");
        } else {
            System.out.println("Helaas, je beurten zijn op. Het woord was: " + dc.getTargetWord());
        }

    }

    private void printBord() {
        System.out.println("\n--- JOUW BORD ---");
        // We halen de kant-en-klare tekst op uit de DomeinController
        String[] bordRegels = dc.getBoardAsText();
        for (int i = 0; i < bordRegels.length; i++) {
            System.out.println(bordRegels[i]);
        }
        System.out.println("-----------------\n");
    }
}