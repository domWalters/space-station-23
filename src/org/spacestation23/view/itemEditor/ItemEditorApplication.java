package org.spacestation23.view.itemEditor;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ItemEditorApplication extends Stage {

    public ItemEditorApplication() {
        super();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/itemEditor/ItemEditor.fxml"));
            Scene scene = new Scene(root);
            this.setScene(scene);
            this.setTitle("Space-Station-23: Item Editor");
        } catch (IOException e) {

        }
    }

}
