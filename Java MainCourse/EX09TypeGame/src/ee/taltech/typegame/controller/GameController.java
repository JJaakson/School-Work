package ee.taltech.typegame.controller;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GameController {

    private boolean gameStarted = false;
    private double multiplier = 1.0;
    private int score = 0;
    private Label scoreText = new Label();
    private Label multiplierText = new Label();
    private LocalDateTime currentTime;
    private LocalDateTime lastCorrectTime = LocalDateTime.now();
    private List<Label> toRemove = new LinkedList<>();
    private List<Label> chars = new LinkedList<>();
    private Random random = new Random();

    private static final int FONT_MAX_SIZE = 75;
    private static final int FONT_MIN_SIZE = 20;
    private static final int FONT_SEPARATOR = 25;
    private static final int FONT_SIZE_TEXT = 20;
    private static final int MULTIPLIER_TEXT_COORDINATE_Y = 25;
    private static final int TEXT_COORDINATE_X = 450;
    private static final int LARGEST_COORDINATE_X = 400;
    private static final int LARGEST_COORDINATE_Y = 350;
    private static final double INCREASE_MULTIPLIER = 0.1;
    private static final double DECREASE_SCORE = 15;
    private static final double INCREASE_SCORE = 10;
    private static final int FADE_DURATION = 250;


    @FXML
    private Button leaveButton;

    @FXML
    private Text startText;

    @FXML
    private AnchorPane gameScreen;

    public void generateSymbol() {
        String keyboard = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
        int symbolAt = random.nextInt(keyboard.length());
        for (Label c : chars) {
            if ((keyboard.charAt(symbolAt) + "").toLowerCase().equals(c.getText().toLowerCase())) {
                symbolAt -= 1;
            }
        }
        Label label = new Label(keyboard.charAt(symbolAt) + "");
        compareWithOthers(label);
        label.setTextFill(Color.color(random.nextFloat(), random.nextFloat(), random.nextFloat()));
        int fontsize = random.nextInt(FONT_MAX_SIZE);
        if (fontsize < FONT_MIN_SIZE) {
            fontsize += FONT_SEPARATOR;
        }
        label.setFont(new Font("Cambria", fontsize));
        chars.add(label);
        gameScreen.getChildren().add(label);
    }

    private void compareWithOthers(Label label) {
        label.setLayoutX(random.nextInt(LARGEST_COORDINATE_X));
        label.setLayoutY(random.nextInt(LARGEST_COORDINATE_Y));
        for (Label c : chars) {
            if (label.intersects(c.getLayoutBounds())
                    || label.intersects(leaveButton.getLayoutBounds())
                    || label.intersects(scoreText.getLayoutBounds())
                    || label.intersects(multiplierText.getLayoutBounds())) {
                label.setLayoutX(random.nextInt(LARGEST_COORDINATE_X));
                label.setLayoutY(random.nextInt(LARGEST_COORDINATE_Y));
            }
        }
    }

    @FXML
    public void generate(KeyEvent e) {
        currentTime = LocalDateTime.now();
        checkFalseEvents(e);
        checkCorrectEvents(e);
        chars.removeAll(toRemove);
        toRemove.clear();
        if (!gameStarted && e.getCode().toString().equals("BACK_SPACE")) {
            initializeGame();
        }
        if (gameStarted) {
            while (chars.size() < 3) {
                generateSymbol();
            }
        }
        updateScoreAndMultiplierTexts();
    }

    private void correctMultiplier() {
        long seconds = ChronoUnit.SECONDS.between(lastCorrectTime, currentTime);
        if (seconds < 2) {
            multiplier += INCREASE_MULTIPLIER;
        } else {
            multiplier = 1;
        }
        BigDecimal bd = new BigDecimal(multiplier).setScale(2, RoundingMode.HALF_UP);
        multiplier = bd.doubleValue();
    }

    private void updateScoreAndMultiplierTexts() {
        score += INCREASE_SCORE * multiplier;
        scoreText.setText("Score: " + score);
        multiplierText.setText("Multiplier: " + multiplier);
    }

    public void fadeAnimation(Label label) {
        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(label.opacityProperty(), 0);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(FADE_DURATION), keyValue);
        timeline.setCycleCount(1);
        timeline.setAutoReverse(true);
        timeline.getKeyFrames().addAll(keyFrame);
        timeline.play();
    }

    public void initializeGame() {
        startText.setText("");
        gameScreen.getChildren().addAll(scoreText, multiplierText);
        scoreText.setText("Score: " + score);
        scoreText.setFont(new Font("Cambria", FONT_SIZE_TEXT));
        scoreText.setLayoutX(TEXT_COORDINATE_X);
        multiplierText.setText("Multiplier: " + multiplier);
        multiplierText.setFont(new Font("Cambria", FONT_SIZE_TEXT));
        multiplierText.setLayoutX(TEXT_COORDINATE_X);
        multiplierText.setLayoutY(MULTIPLIER_TEXT_COORDINATE_Y);
        gameStarted = true;
    }

    public void checkCorrectEvents(KeyEvent e) {
        for (Label c : chars) {
            if (e.getCode().toString().length() > 1
                    && c.getText().toLowerCase().equals(e.getCode().toString().charAt(5) + "")
                    || c.getText().toLowerCase().equals(e.getCode().toString().toLowerCase())) {
                fadeAnimation(c);
                toRemove.add(c);
                correctMultiplier();
                lastCorrectTime = LocalDateTime.now();
            }
        }
    }

    public void checkFalseEvents(KeyEvent e) {
        List<String> currentActiveChars = new LinkedList<>();
        for (Label c : chars) {
            currentActiveChars.add(c.getText().toLowerCase());
        }
        if (!currentActiveChars.contains(e.getCode().toString().toLowerCase())
                || e.getCode().toString().length() > 1
                && !currentActiveChars.contains(e.getCode().toString().charAt(5) + "")) {
            score -= DECREASE_SCORE;
            multiplier = 1;
        }
    }

    @FXML
    public void setMenuScreen() throws IOException {
        Stage stage = (Stage) leaveButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/menu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
