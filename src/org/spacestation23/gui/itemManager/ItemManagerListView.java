package org.spacestation23.gui.itemManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import org.spacestation23.item.Item;
import org.spacestation23.item.ItemCreator;

public class ItemManagerListView extends ListView<Item> {

    public ItemManagerListView(ItemManagerGridPane gridPane) {
        super();
        ObservableList<Item> items = FXCollections.observableArrayList(ItemCreator.items.values());
        this.setItems(items);
        this.setCellFactory(itemListView -> new ItemManagerListViewCell());
        this.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                gridPane.getNameTextField().setText(newValue.getName());
                gridPane.getIdTextField().setText("" + newValue.getItemId());
                gridPane.getStackCapacityTextField().setText("" + newValue.getStackCapacity());
            }
        });
    }

}
