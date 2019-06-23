package org.spacestation23.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.spacestation23.model.material.Material;
import org.spacestation23.model.material.MaterialCreator;
import org.spacestation23.view.mapEditor.MapEditorMaterialCell;
import org.spacestation23.view.materialEditor.alert.MaterialCreationSucceededAlert;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MaterialEditorController implements Initializable {

    @FXML
    private ListView<Material> materialListView;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField stringSpriteTextField;

    @FXML
    private ChoiceBox<Boolean> passableChoiceBox;

    @FXML
    private TextField inventoryCapacityTextField;

    @FXML
    private Button imageSpriteButton;

    private ObservableList<Material> materialsObservableList;

    public MaterialEditorController() {
        materialsObservableList = FXCollections.observableArrayList(MaterialCreator.materials.values());
    }

    @FXML
    void handleOpenMaterialsXML() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            MaterialCreator.populateMaterialsFromFile(file.getPath());
        }
        materialsObservableList.setAll(MaterialCreator.materials.values());
    }

    @FXML
    void handleSaveMaterialsXML() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            MaterialCreator.populateFileWithMaterials(file.getPath());
        }
    }

    @FXML
    void handleDeleteMaterial() {
        Material focusedMaterial = materialListView.getFocusModel().getFocusedItem();
        MaterialCreator.materials.remove(focusedMaterial.getName());
        materialsObservableList.remove(focusedMaterial);
    }

    @FXML
    void handleKeyPressOnMaterialListView(KeyEvent e) {
        if (e.getCode().equals(KeyCode.DELETE) && materialListView.getFocusModel().getFocusedItem() != null) {
            this.handleDeleteMaterial();
        }
    }

    @FXML
    void handleImageSpriteButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            String path = file.getPath();
            imageSpriteButton.setUserData(path);
            String[] pathArray = path.split("/");
            imageSpriteButton.setText(pathArray[pathArray.length - 1]);
        }
    }

    @FXML
    void handleSubmitButton() {
        String materialName = nameTextField.getText();
        String materialStringSprite = stringSpriteTextField.getText();
        String materialPassable = passableChoiceBox.getValue().toString();
        String materialInventoryCapacity = inventoryCapacityTextField.getText();
        String materialImageSprite = (String) imageSpriteButton.getUserData();
        Material newMaterial = MaterialCreator.createMaterialFromStrings(materialName, materialStringSprite, materialPassable, materialInventoryCapacity, materialImageSprite);
        if (newMaterial != null) {
            MaterialCreator.materials.put(newMaterial.getName(), newMaterial);
            MaterialCreationSucceededAlert goodAlert = new MaterialCreationSucceededAlert(newMaterial.getName());
            goodAlert.showAndWait();
            materialsObservableList.add(newMaterial);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        materialListView.setItems(materialsObservableList);
        materialListView.setCellFactory(itemListView -> new MapEditorMaterialCell());
        materialListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                nameTextField.setText(newValue.getName());
                stringSpriteTextField.setText(newValue.getCharacterSprite());
                passableChoiceBox.getSelectionModel().select((newValue.isPassable()) ? 0 : 1);
                inventoryCapacityTextField.setText("" + newValue.getInventoryCapacity());
                String path = newValue.getImgSprite().getUrl();
                imageSpriteButton.setUserData(path);
                String[] pathArray = path.split("/");
                imageSpriteButton.setText(pathArray[pathArray.length - 1]);
            }
        });
    }
}
