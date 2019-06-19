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
        ItemInfoGridPane gridPane = createFocusGridPane();
        root.setRight(gridPane);
        ListView<Item> itemListView = this.createListView(gridPane);
        gridPane.setItemListView(itemListView);
        menuBar.setItemListView(itemListView);
        root.setLeft(itemListView);
    }

    private ItemInfoGridPane createFocusGridPane() {
        ItemInfoGridPane gridPane = new ItemInfoGridPane();
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

    private ListView<Item> createListView(ItemInfoGridPane gridPane) {
        ListView<Item> listView = new ListView<>();
        ObservableList<Item> items = FXCollections.observableArrayList(ItemCreator.items.values());
        listView.setItems(items);
        listView.setCellFactory(itemListView -> new ItemListViewCell());
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                gridPane.getNameTextField().setText(newValue.getName());
                gridPane.getIdTextField().setText("" + newValue.getItemId());
                gridPane.getStackCapacityTextField().setText("" + newValue.getStackCapacity());
            }
        });
        return listView;
    }

}
