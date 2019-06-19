package org.spacestation23.gui.itemManager;

import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import org.spacestation23.item.Item;

public class ItemListViewCell extends ListCell<Item> {

    @Override
    protected void updateItem(Item item, boolean empty) {
        super.updateItem(item, empty);
        if(empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            GridPane gridPane = new GridPane();
            Label idLabel = new Label("" + item.getItemId());
            Label nameLabel = new Label(item.getName());
            Separator separator = new Separator();
            separator.setOrientation(Orientation.VERTICAL);
            separator.setValignment(VPos.CENTER);
            gridPane.addRow(1, idLabel, separator, nameLabel);
            setText(null);
            setGraphic(gridPane);
        }
    }

}
