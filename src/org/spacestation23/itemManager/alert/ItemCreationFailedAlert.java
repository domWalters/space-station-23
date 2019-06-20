package org.spacestation23.itemManager.alert;

import javafx.scene.control.Alert;

public class ItemCreationFailedAlert extends Alert {

    private final static String DEFAULT_PREAMBLE = "Item creation failed: ";

    public ItemCreationFailedAlert() {
        super(Alert.AlertType.INFORMATION);
        this.setTitle("Item Creation Failed");
        this.setHeaderText(null);
        this.setContentText(DEFAULT_PREAMBLE);
    }

    public void updateContentText(String addOn) {
        this.setContentText(DEFAULT_PREAMBLE + addOn);
    }

}
