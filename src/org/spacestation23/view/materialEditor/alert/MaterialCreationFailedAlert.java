package org.spacestation23.view.materialEditor.alert;

import javafx.scene.control.Alert;

public class MaterialCreationFailedAlert extends Alert {

    private final static String DEFAULT_PREAMBLE = "Material creation failed: ";

    public MaterialCreationFailedAlert() {
        super(AlertType.INFORMATION);
        this.setTitle("Material Creation Failed");
        this.setHeaderText(null);
        this.setContentText(DEFAULT_PREAMBLE);
    }

    public void updateContentText(String addOn) {
        this.setContentText(DEFAULT_PREAMBLE + addOn);
    }

}
