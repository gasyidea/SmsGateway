package gasyidea.sms.fx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static final String MY_DOC = "/MyFxDocument.fxml";
    private static final Integer WIDTH = 600;
    private static final Integer HEIGHT = 450;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(MY_DOC));
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.setFullScreen(false);
        Platform.setImplicitExit(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}