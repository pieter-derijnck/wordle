package Gui;

import Domain.DomeinController;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class WordleGui extends Application {
    private DomeinController dc;
    private Label[][] tileLabels = new Label[6][5];
    private Label messageLabel;
    private VBox root;
    private String huidigeInvoer = "";

    // Houdt de virtuele toetsen bij zodat we de kleuren kunnen aanpassen
    private Map<String, Button> keyboardButtons = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        dc = new DomeinController();
        dc.startNewGame();

        root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #121213; -fx-padding: 20px;");

        Label title = new Label("WORDLE");
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: white; -fx-font-family: Arial;");

        // 1. Het speelbord
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(8);
        grid.setVgap(8);

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 5; col++) {
                Label tile = new Label();
                styleTile(tile);
                tileLabels[row][col] = tile;
                grid.add(tile, col, row);
            }
        }

        messageLabel = new Label("");
        messageLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #ff6b6b; -fx-font-family: Arial; -fx-font-weight: bold;");

        // 2. De Tip / Calculator knop
        Button tipButton = new Button("💡 Bereken Beste Gok");
        tipButton.setStyle("-fx-background-color: #565758; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: Arial; -fx-padding: 8px 15px; -fx-background-radius: 4px; -fx-cursor: hand;");
        tipButton.setOnAction(e -> toonTipVenster());

        // 3. Het virtuele toetsenbord
        VBox keyboardPane = createKeyboard();

        root.getChildren().addAll(title, grid, messageLabel, tipButton, keyboardPane);

        Scene scene = new Scene(root, 500, 800);

        // Luister naar fysiek toetsenbord
        scene.setOnKeyPressed(event -> handleKeyPress(event));

        primaryStage.setTitle("Wordle JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();

        updateBoard();
        root.requestFocus();
    }

    // Opent het pop-up venster voor de calculator/tips
    private void toonTipVenster() {
        Stage tipStage = new Stage();
        tipStage.setTitle("Wordle Calculator & Tips");

        VBox tipLayout = new VBox(15);
        tipLayout.setAlignment(Pos.CENTER);
        tipLayout.setStyle("-fx-background-color: #121213; -fx-padding: 20px;");

        Label tipTitle = new Label("Slimme Calculator");
        tipTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white; -fx-font-family: Arial;");

        Label adviesLabel = new Label("Beste statistische gok op dit moment:\n👉 [ Rekenalgoritme wordt gekoppeld ]");
        adviesLabel.setStyle("-fx-font-size: 15px; -fx-text-fill: #538d4e; -fx-text-alignment: center; -fx-font-family: Arial;");

        Button sluitBtn = new Button("Sluiten");
        sluitBtn.setOnAction(e -> tipStage.close());
        sluitBtn.setStyle("-fx-background-color: #3a3a3c; -fx-text-fill: white; -fx-font-family: Arial; -fx-font-weight: bold; -fx-padding: 6px 12px; -fx-background-radius: 4px; -fx-cursor: hand;");

        tipLayout.getChildren().addAll(tipTitle, adviesLabel, sluitBtn);

        Scene tipScene = new Scene(tipLayout, 350, 220);
        tipStage.setScene(tipScene);
        tipStage.show();
    }

    // Bouwt het virtuele toetsenbord met rijen
    private VBox createKeyboard() {
        VBox keyboardBox = new VBox(6);
        keyboardBox.setAlignment(Pos.CENTER);

        String[] rij1 = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"};
        String[] rij2 = {"A", "S", "D", "F", "G", "H", "J", "K", "L"};
        String[] rij3 = {"ENTER", "Z", "X", "C", "V", "B", "N", "M", "BACK"};

        keyboardBox.getChildren().add(createKeyRow(rij1));
        keyboardBox.getChildren().add(createKeyRow(rij2));
        keyboardBox.getChildren().add(createKeyRow(rij3));

        return keyboardBox;
    }

    private HBox createKeyRow(String[] letters) {
        HBox rowBox = new HBox(5);
        rowBox.setAlignment(Pos.CENTER);

        for (String s : letters) {
            Button btn = new Button(s);
            styleKeyboardButton(btn, s);

            btn.setOnAction(e -> processInput(s));

            keyboardButtons.put(s, btn);
            rowBox.getChildren().add(btn);
        }
        return rowBox;
    }

    private void styleKeyboardButton(Button btn, String text) {
        double width = text.length() > 1 ? 65 : 40;
        btn.setStyle("-fx-min-width: " + width + "px; -fx-min-height: 50px; " +
                "-fx-background-color: #818384; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-font-size: 14px; -fx-font-family: Arial; " +
                "-fx-background-radius: 4px; -fx-cursor: hand;");
    }

    private void handleKeyPress(KeyEvent event) {
        String toets = event.getText().toUpperCase();

        if (event.getCode().toString().equals("BACK_SPACE")) {
            processInput("BACK");
            return;
        }
        if (event.getCode().toString().equals("ENTER")) {
            processInput("ENTER");
            return;
        }
        if (toets.matches("[A-Z]") && toets.length() == 1) {
            processInput(toets);
        }
    }

    private void processInput(String input) {
        if (dc.isGameOver()) return;

        if (input.equals("BACK")) {
            if (huidigeInvoer.length() > 0) {
                huidigeInvoer = huidigeInvoer.substring(0, huidigeInvoer.length() - 1);
                updateHuidigeRijVisual();
            }
            return;
        }

        if (input.equals("ENTER")) {
            if (huidigeInvoer.length() != 5) {
                messageLabel.setText("Woord moet 5 letters lang zijn!");
                return;
            }

            try {
                dc.submitGuess(huidigeInvoer);
                messageLabel.setText("");

                updateBoard();
                updateKeyboardColors();

                huidigeInvoer = "";

                if (dc.isGameWon()) {
                    messageLabel.setText("Gefeliciteerd, je hebt gewonnen!");
                    messageLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #538d4e; -fx-font-family: Arial; -fx-font-weight: bold;");
                } else if (dc.isGameOver()) {
                    messageLabel.setText("Game Over! Het woord was: " + dc.getTargetWord());
                }
            } catch (IllegalArgumentException ex) {
                messageLabel.setText(ex.getMessage());
            }
            return;
        }

        if (input.matches("[A-Z]") && input.length() == 1 && huidigeInvoer.length() < 5) {
            huidigeInvoer += input;
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
                styleTile(tile);

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

    private void updateKeyboardColors() {
        char[][] letters = dc.getBoardLetters();
        String[][] statuses = dc.getBoardStatuses();

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 5; col++) {
                char c = letters[row][col];
                String status = statuses[row][col];
                String keyStr = String.valueOf(c);

                if (keyboardButtons.containsKey(keyStr)) {
                    Button btn = keyboardButtons.get(keyStr);
                    String currentStyle = btn.getStyle();

                    if (status.equals("CORRECT")) {
                        setKeyColor(btn, "#538d4e");
                    } else if (status.equals("PRESENT") && !currentStyle.contains("#538d4e")) {
                        setKeyColor(btn, "#b59f3b");
                    } else if (status.equals("ABSENT") && !currentStyle.contains("#538d4e") && !currentStyle.contains("#b59f3b")) {
                        setKeyColor(btn, "#3a3a3c");
                    }
                }
            }
        }
    }

    private void setKeyColor(Button btn, String color) {
        String text = btn.getText();
        double width = text.length() > 1 ? 65 : 40;
        btn.setStyle("-fx-min-width: " + width + "px; -fx-min-height: 50px; " +
                "-fx-background-color: " + color + "; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-font-size: 14px; -fx-font-family: Arial; " +
                "-fx-background-radius: 4px; -fx-cursor: hand;");
    }

    private void styleTile(Label tile) {
        tile.setStyle("-fx-min-width: 55px; -fx-min-height: 55px; " +
                "-fx-max-width: 55px; -fx-max-height: 55px; " +
                "-fx-alignment: center; -fx-font-size: 28px; " +
                "-fx-font-weight: bold; -fx-font-family: Arial; " +
                "-fx-text-fill: white; -fx-border-color: #3a3a3c; " +
                "-fx-border-width: 2px; -fx-background-color: #121213;");
    }

    public static void main(String[] args) {
        launch(args);
    }
}