package org.spacestation23.gui.itemManager.alert;

import javafx.scene.control.Alert;
import org.spacestation23.item.ItemCreator;

public class ItemCreationSucceededAlert extends Alert {

    public ItemCreationSucceededAlert(int itemIdNumber) {
        super(Alert.AlertType.INFORMATION);
        this.setTitle("Item Created");
        this.setHeaderText(null);
        this.setContentText("Successfully created new item!\n\n" + ItemCreator.items.get(itemIdNumber).toStringForAlert());
    }

}
