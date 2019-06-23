package org.spacestation23.view.materialEditor;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MaterialEditorApplication extends Stage {

    public MaterialEditorApplication() {
        super();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/materialEditor/MaterialEditor.fxml"));
            Scene scene = new Scene(root);
            this.setScene(scene);
            this.setTitle("Space-Station-23: Material Editor");
        } catch (IOException e) {

        }
    }

}
