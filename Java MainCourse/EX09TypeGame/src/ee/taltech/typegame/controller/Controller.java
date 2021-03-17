package ee.taltech.typegame.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    @FXML
    private Button playButton;
    @FXML
    private Button exitButton;

    @FXML
    public void setNewGameScreen() throws IOException {
        Stage stage = (Stage) playButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/game.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void exitGame() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
