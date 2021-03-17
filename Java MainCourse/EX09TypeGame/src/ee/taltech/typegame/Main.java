package ee.taltech.typegame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static final float WIDTH = 600;
    private static final float HEIGHT = 400;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root, WIDTH, HEIGHT));
        stage.setTitle("Fastest Clicker");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
