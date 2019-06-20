package org.spacestation23.gui.itemManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.spacestation23.gui.itemManager.alert.ItemCreationSucceededAlert;
import org.spacestation23.item.Item;
import org.spacestation23.item.ItemCreator;

import java.io.File;
import java.util.Optional;

public class ItemManagerMenuBar extends MenuBar {

    private ListView<Item> itemListView;

    public ItemManagerMenuBar(Stage stage) {
        super();
        // File Menu
        Menu fileMenu = new Menu("File");
        MenuItem newItem = new MenuItem("Create Item");
        newItem.setOnAction(e -> {
            Optional<Item> result = dialogForItemCreation().showAndWait();
            if (result.isPresent()) {
                Item item = result.get();
                ItemCreator.items.put(item.getItemId(), item);
                ItemCreationSucceededAlert alert = new ItemCreationSucceededAlert(item.getItemId());
                alert.showAndWait();
                ObservableList<Item> items = FXCollections.observableArrayList(ItemCreator.items.values());
                this.getItemListView().setItems(items);
            }
        });
        MenuItem open = new MenuItem("Open...");
        open.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                ItemCreator.populateItemsFromFile(file.getPath());
            }
            ObservableList<Item> items = FXCollections.observableArrayList(ItemCreator.items.values());
            this.getItemListView().setItems(items);
        });
        MenuItem save = new MenuItem("Save...");
        save.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                ItemCreator.populateFileWithItems(file.getPath());
            }
        });
        fileMenu.getItems().addAll(newItem, open, save);
        // Edit Menu
        Menu editMenu = new Menu("Edit");
        MenuItem delete = new MenuItem("Delete Item");
        delete.setOnAction(e -> {
            ItemCreator.items.remove(itemListView.getFocusModel().getFocusedItem().getItemId());
            ObservableList<Item> items = FXCollections.observableArrayList(ItemCreator.items.values());
            this.getItemListView().setItems(items);
        });
        editMenu.getItems().add(delete);

        this.getMenus().addAll(fileMenu, editMenu);
    }

    public ListView<Item> getItemListView() {
        return itemListView;
    }

    public void setItemListView(ListView<Item> itemListView) {
        this.itemListView = itemListView;
    }

    private Dialog<Item> dialogForItemCreation() {
        Dialog<Item> dialog = new Dialog<>();
        dialog.setTitle("Item Creator");
        dialog.setHeaderText("Fill in the required fields, and submit to create a new item.");
        dialog.setResizable(true);
        ItemManagerGridPane gridPane = new ItemManagerGridPane();
        dialog.getDialogPane().setContent(gridPane);

        ButtonType buttonSubmit = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonSubmit);
        dialog.setResultConverter(b -> (b == buttonSubmit) ? ItemCreator.createItemFromGridPane(gridPane) : null);
        return dialog;
    }

}
