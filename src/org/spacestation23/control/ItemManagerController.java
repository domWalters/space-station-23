package org.spacestation23.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.spacestation23.view.itemManager.ItemManagerListViewCell;
import org.spacestation23.view.itemManager.alert.ItemCreationSucceededAlert;
import org.spacestation23.model.item.Item;
import org.spacestation23.model.item.ItemCreator;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemManagerController implements Initializable {

    @FXML
    private TextField idTextField;

    @FXML
    private TextField stackCapacityTextField;

    @FXML
    private MenuItem newItem;

    @FXML
    private TextField nameTextField;

    @FXML
    private MenuItem save;

    @FXML
    private Button buttonSubmit;

    @FXML
    private MenuItem delete;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu editMenu;

    @FXML
    private Label idLabel;

    @FXML
    private ListView<Item> itemListView;

    @FXML
    private Label stackCapacityLabel;

    @FXML
    private Menu fileMenu;

    @FXML
    private MenuItem open;

    @FXML
    private Label nameLabel;

    private ObservableList<Item> itemsObservableList;

    public ItemManagerController() {
        itemsObservableList = FXCollections.observableArrayList(ItemCreator.items.values());
    }

    @FXML
    void handleSubmitButton(ActionEvent event) {
        String itemName = nameTextField.getText();
        String itemId = idTextField.getText();
        String itemStackCapacity = stackCapacityTextField.getText();
        Item newItem = ItemCreator.createItemFromStrings(itemName, itemId, itemStackCapacity);
        if (newItem != null) {
            ItemCreator.items.put(newItem.getItemId(), newItem);
            ItemCreationSucceededAlert goodAlert = new ItemCreationSucceededAlert(newItem.getItemId());
            goodAlert.showAndWait();
            ObservableList<Item> items = FXCollections.observableArrayList(ItemCreator.items.values());
            itemListView.setItems(items);
        }
    }

    @FXML
    void handleOpenItemsXML(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            ItemCreator.populateItemsFromFile(file.getPath());
        }
        ObservableList<Item> items = FXCollections.observableArrayList(ItemCreator.items.values());
        itemListView.setItems(items);
    }

    @FXML
    void handleSaveItemsXML(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            ItemCreator.populateFileWithItems(file.getPath());
        }
    }

    @FXML
    void handleDeleteItem(ActionEvent event) {
        ItemCreator.items.remove(itemListView.getFocusModel().getFocusedItem().getItemId());
        ObservableList<Item> items = FXCollections.observableArrayList(ItemCreator.items.values());
        itemListView.setItems(items);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        itemListView.setItems(itemsObservableList);
        itemListView.setCellFactory(itemListView -> new ItemManagerListViewCell());
        itemListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                nameTextField.setText(newValue.getName());
                idTextField.setText("" + newValue.getItemId());
                stackCapacityTextField.setText("" + newValue.getStackCapacity());
            }
        });
    }

}
