package org.spacestation23.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.spacestation23.model.item.Item;
import org.spacestation23.model.item.ItemCreator;
import org.spacestation23.view.itemEditor.ItemEditorListViewCell;
import org.spacestation23.view.itemEditor.alert.ItemCreationSucceededAlert;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemEditorController implements Initializable {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField stackCapacityTextField;

    @FXML
    private ListView<Item> itemListView;

    private ObservableList<Item> itemsObservableList;

    public ItemEditorController() {
        itemsObservableList = FXCollections.observableArrayList(ItemCreator.items.values());
    }

    @FXML
    void handleOpenItemsXML() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            ItemCreator.populateItemsFromFile(file.getPath());
        }
        itemsObservableList.setAll(ItemCreator.items.values());
    }

    @FXML
    void handleSaveItemsXML() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            ItemCreator.populateFileWithItems(file.getPath());
        }
    }

    @FXML
    void handleDeleteItem() {
        Item focusedItem = itemListView.getFocusModel().getFocusedItem();
        ItemCreator.items.remove(focusedItem.getItemId());
        itemsObservableList.remove(focusedItem);
    }

    @FXML
    void handleKeyPressOnItemListView(KeyEvent e) {
        if (e.getCode().equals(KeyCode.DELETE) && itemListView.getFocusModel().getFocusedItem() != null) {
            this.handleDeleteItem();
        }
    }

    @FXML
    void handleSubmitButton() {
        String itemName = nameTextField.getText();
        String itemId = idTextField.getText();
        String itemStackCapacity = stackCapacityTextField.getText();
        Item newItem = ItemCreator.createItemFromStrings(itemName, itemId, itemStackCapacity);
        if (newItem != null) {
            ItemCreator.items.put(newItem.getItemId(), newItem);
            ItemCreationSucceededAlert goodAlert = new ItemCreationSucceededAlert(newItem.getItemId());
            goodAlert.showAndWait();
            itemsObservableList.add(newItem);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        itemListView.setItems(itemsObservableList);
        itemListView.setCellFactory(itemListView -> new ItemEditorListViewCell());
        itemListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                nameTextField.setText(newValue.getName());
                idTextField.setText("" + newValue.getItemId());
                stackCapacityTextField.setText("" + newValue.getStackCapacity());
            }
        });
    }

}
