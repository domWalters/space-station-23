package org.spacestation23.view.materialEditor.alert;

import javafx.scene.control.Alert;
import org.spacestation23.model.item.ItemCreator;
import org.spacestation23.model.material.MaterialCreator;

public class MaterialCreationSucceededAlert extends Alert {

    public MaterialCreationSucceededAlert(String materialName) {
        super(AlertType.INFORMATION);
        this.setTitle("Material Created");
        this.setHeaderText(null);
        this.setContentText("Successfully created new item!\n\n" + MaterialCreator.materials.get(materialName).toStringForAlert());
    }

}
