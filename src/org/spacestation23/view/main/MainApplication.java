package org.spacestation23.view.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/main/Main.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("css/main/Tile.css");
            primaryStage.setScene(scene);
            primaryStage.setTitle("Space-Station-23");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
