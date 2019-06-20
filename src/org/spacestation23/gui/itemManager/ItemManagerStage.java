package org.spacestation23.gui.itemManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.spacestation23.gui.itemManager.alert.ItemCreationSucceededAlert;
import org.spacestation23.item.Item;
import org.spacestation23.item.ItemCreator;

public class ItemManagerStage extends Stage {

    public ItemManagerStage() {
        super();
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);
        this.setScene(scene);
        this.setTitle("Space-Station-23: Item Manager");
        ItemManagerMenuBar menuBar = new ItemManagerMenuBar(this);
        root.setTop(menuBar);
        ItemManagerGridPane gridPane = createFocusGridPane();
        root.setRight(gridPane);
        ItemManagerListView itemListView = new ItemManagerListView(gridPane);
        gridPane.setItemListView(itemListView);
        menuBar.setItemListView(itemListView);
        root.setLeft(itemListView);
    }

    private ItemManagerGridPane createFocusGridPane() {
        ItemManagerGridPane gridPane = new ItemManagerGridPane();
        Button buttonSubmit = new Button("Submit");
        buttonSubmit.setOnAction(e -> {
            Item newItem = ItemCreator.createItemFromGridPane(gridPane);
            if (newItem != null) {
                ItemCreator.items.put(newItem.getItemId(), newItem);
                ItemCreationSucceededAlert goodAlert = new ItemCreationSucceededAlert(newItem.getItemId());
                goodAlert.showAndWait();
                ObservableList<Item> items = FXCollections.observableArrayList(ItemCreator.items.values());
                gridPane.getItemListView().setItems(items);
            }
        });
        gridPane.add(buttonSubmit, 3, 4);
        return gridPane;
    }

}
