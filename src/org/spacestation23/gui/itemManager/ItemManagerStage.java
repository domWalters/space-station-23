package org.spacestation23.gui.itemManager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ItemManagerStage extends Stage {

    public ItemManagerStage() {
        super();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/ItemManager.fxml"));
            Scene scene = new Scene(root);
            this.setScene(scene);
            this.setTitle("Space-Station-23: Item Manager");
        } catch (IOException e) {

        }
    }

}
