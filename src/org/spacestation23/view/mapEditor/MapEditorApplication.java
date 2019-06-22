package org.spacestation23.view.mapEditor;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MapEditorApplication extends Stage {

    public MapEditorApplication() {
        super();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/mapEditor/MapEditor.fxml"));
            Scene scene = new Scene(root);
            this.setScene(scene);
            this.setTitle("Space-Station-23: Map Editor");
        } catch (IOException e) {

        }
    }

}
