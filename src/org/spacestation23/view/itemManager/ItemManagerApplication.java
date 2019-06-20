package org.spacestation23.view.itemManager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ItemManagerApplication extends Stage {

    public ItemManagerApplication() {
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
