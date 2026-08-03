package Gui;

import Domain.DomeinController;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WordleGui extends Application {
    private DomeinController dc;
    private Label[][] tileLabels = new Label[6][5];
    private Label messageLabel;
    private VBox root;
    private String huidigeInvoer = "";

    @Override
    public void start(Stage primaryStage) {
        dc = new DomeinController();
        dc.startNewGame();

        root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #121213;"); // Donkere Wordle achtergrond

        Label title = new Label("WORDLE");
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: white; -fx-font-family: Arial;");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 5; col++) {
                Label tile = new Label();
                styleTile(tile); // Geef het vakje meteen een stijl zodat het zichtbaar is
                tileLabels[row][col] = tile;
                grid.add(tile, col, row);
            }
        }

        messageLabel = new Label("");
        messageLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #ff6b6b; -fx-font-family: Arial; -fx-font-weight: bold;");

        root.getChildren().addAll(title, grid, messageLabel);

        Scene scene = new Scene(root, 450, 650);

        // Luister naar toetsenbord invoer op de scene
        scene.setOnKeyPressed(event -> handleKeyPress(event));

        primaryStage.setTitle("Wordle JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();

        updateBoard();
        root.requestFocus();
    }

    private void handleKeyPress(KeyEvent event) {
        if (dc.isGameOver()) return;

        String toets = event.getText().toUpperCase();

        if (event.getCode().toString().equals("BACK_SPACE")) {
            if (huidigeInvoer.length() > 0) {
                huidigeInvoer = huidigeInvoer.substring(0, huidigeInvoer.length() - 1);
                updateHuidigeRijVisual();
            }
            return;
        }

        if (event.getCode().toString().equals("ENTER")) {
            if (huidigeInvoer.length() != 5) {
                messageLabel.setText("Woord moet 5 letters lang zijn!");
                return;
            }

            try {
                dc.submitGuess(huidigeInvoer);
                messageLabel.setText("");
                huidigeInvoer = "";
                updateBoard();

                if (dc.isGameWon()) {
                    messageLabel.setText("Gefeliciteerd, je hebt gewonnen!");
                    messageLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #538d4e; -fx-font-family: Arial; -fx-font-weight: bold;");
                } else if (dc.isGameOver()) {
                    messageLabel.setText("Game Over! Het woord was: " + dc.getTargetWord());
                }
            } catch (IllegalArgumentException ex) {
                messageLabel.setText(ex.getMessage());
            }
            return;
        }

        if (toets.matches("[A-Z]") && toets.length() == 1 && huidigeInvoer.length() < 5) {
            huidigeInvoer += toets;
            messageLabel.setText("");
            updateHuidigeRijVisual();
        }
    }

    private void updateHuidigeRijVisual() {
        int actieveRij = 0;
        char[][] letters = dc.getBoardLetters();

        for (int i = 0; i < 6; i++) {
            if (letters[i][0] == ' ') {
                actieveRij = i;
                break;
            }
        }

        for (int col = 0; col < 5; col++) {
            Label tile = tileLabels[actieveRij][col];
            if (col < huidigeInvoer.length()) {
                tile.setText(String.valueOf(huidigeInvoer.charAt(col)));
            } else {
                tile.setText("");
            }
        }
    }

    private void updateBoard() {
        char[][] letters = dc.getBoardLetters();
        String[][] statuses = dc.getBoardStatuses();

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 5; col++) {
                Label tile = tileLabels[row][col];

                tile.setText(String.valueOf(letters[row][col]).trim());

                // Standaard stijl toepassen
                styleTile(tile);

                // Kleur aanpassen op basis van de status van het domein
                switch (statuses[row][col]) {
                    case "CORRECT":
                        tile.setStyle(tile.getStyle() + " -fx-background-color: #538d4e; -fx-border-color: #538d4e;");
                        break;
                    case "PRESENT":
                        tile.setStyle(tile.getStyle() + " -fx-background-color: #b59f3b; -fx-border-color: #b59f3b;");
                        break;
                    case "ABSENT":
                        tile.setStyle(tile.getStyle() + " -fx-background-color: #3a3a3c; -fx-border-color: #3a3a3c;");
                        break;
                }
            }
        }

        if (root != null) {
            root.requestFocus();
        }
    }

    private void styleTile(Label tile) {
        tile.setStyle("-fx-min-width: 60px; -fx-min-height: 60px; " +
                "-fx-max-width: 60px; -fx-max-height: 60px; " +
                "-fx-alignment: center; -fx-font-size: 32px; " +
                "-fx-font-weight: bold; -fx-font-family: Arial; " +
                "-fx-text-fill: white; -fx-border-color: #3a3a3c; " +
                "-fx-border-width: 2px; -fx-background-color: #121213;");
    }

    public static void main(String[] args) {
        launch(args);
    }
}