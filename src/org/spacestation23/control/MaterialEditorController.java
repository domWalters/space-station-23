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
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.spacestation23.model.material.Material;
import org.spacestation23.model.material.MaterialCreator;
import org.spacestation23.model.material.Visualisation;
import org.spacestation23.view.mapEditor.MapEditorMaterialCell;
import org.spacestation23.view.materialEditor.MaterialEditorVisualisationCell;
import org.spacestation23.view.materialEditor.alert.MaterialCreationSucceededAlert;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MaterialEditorController implements Initializable {

    public static final int START_OF_VISUALISATIONS_INDEX = 5;

    @FXML
    private ListView<Material> materialListView;

    @FXML
    private GridPane editingGridPane;

    @FXML
    private TextField nameTextField;

    @FXML
    private ChoiceBox<Boolean> passableChoiceBox;

    @FXML
    private TextField inventoryCapacityTextField;

    private List<MaterialEditorVisualisationCell> visualisationCells;

    @FXML
    private Button addVisualisationButton;

    @FXML
    private Button submitButton;

    private ObservableList<Material> materialsObservableList;

    public MaterialEditorController() {
        materialsObservableList = FXCollections.observableArrayList(MaterialCreator.materials.values());
        visualisationCells = new ArrayList<>();
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
    void handleAddVisualisationButton() {
        MaterialEditorVisualisationCell visualisationCell = new MaterialEditorVisualisationCell();
        visualisationCell.getDeleteButton().setOnAction(e -> {
            int fromIndex = visualisationCells.indexOf(visualisationCell);
            visualisationCells.remove(visualisationCell);
            editingGridPane.getChildren().remove(visualisationCell);
            this.reinitialiseVisualisations(fromIndex);
        });
        visualisationCells.add(visualisationCell);
        editingGridPane.getChildren().removeAll(addVisualisationButton, submitButton);
        editingGridPane.add(visualisationCell, 0, START_OF_VISUALISATIONS_INDEX + visualisationCells.size() - 1, 2, 1);
        editingGridPane.add(addVisualisationButton, 0, START_OF_VISUALISATIONS_INDEX + visualisationCells.size());
        editingGridPane.add(submitButton, 2, START_OF_VISUALISATIONS_INDEX + visualisationCells.size());
    }

    @FXML
    void handleSubmitButton() {
        String materialName = nameTextField.getText();
        String materialPassable = passableChoiceBox.getValue().toString();
        String materialInventoryCapacity = inventoryCapacityTextField.getText();
        List<Pair<String, String>> materialVisualisationsList = new ArrayList<>();
        for (MaterialEditorVisualisationCell visualisationCell : visualisationCells) {
            materialVisualisationsList.add(new Pair<>(visualisationCell.getCharacterSprite(), visualisationCell.getImageSpriteString()));
        }
        Material newMaterial = MaterialCreator.createMaterialFromStrings(materialName, materialPassable, materialInventoryCapacity, materialVisualisationsList);
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
                passableChoiceBox.getSelectionModel().select((newValue.isPassable()) ? 0 : 1);
                inventoryCapacityTextField.setText("" + newValue.getInventoryCapacity());
                editingGridPane.getChildren().removeAll(visualisationCells);
                editingGridPane.getChildren().removeAll(addVisualisationButton, submitButton);
                visualisationCells.clear();
                List<Visualisation> visualisations = newValue.getVisualisations();
                for (int i = 0; i < visualisations.size(); i++) {
                    Visualisation visualisation = visualisations.get(i);
                    MaterialEditorVisualisationCell visualisationCell = new MaterialEditorVisualisationCell(visualisation);
                    visualisationCell.getDeleteButton().setOnAction(e -> {
                        int fromIndex = visualisationCells.indexOf(visualisationCell);
                        visualisationCells.remove(visualisationCell);
                        editingGridPane.getChildren().remove(visualisationCell);
                        this.reinitialiseVisualisations(fromIndex);
                    });
                    editingGridPane.add(visualisationCell, 0, START_OF_VISUALISATIONS_INDEX + i, 2, 1);
                    visualisationCells.add(visualisationCell);
                }
                editingGridPane.add(addVisualisationButton, 0, START_OF_VISUALISATIONS_INDEX + visualisations.size());
                editingGridPane.add(submitButton, 2, START_OF_VISUALISATIONS_INDEX + visualisations.size());
            }
        });

    }

    private void reinitialiseVisualisations(int fromIndex) {
        editingGridPane.getChildren().removeAll(addVisualisationButton, submitButton);
        for (int i = fromIndex; i < visualisationCells.size(); i++) {
            MaterialEditorVisualisationCell visualisationCell = visualisationCells.get(i);
            editingGridPane.getChildren().remove(visualisationCell);
            editingGridPane.add(visualisationCell, 0, START_OF_VISUALISATIONS_INDEX + i, 2, 1);
        }
        editingGridPane.add(addVisualisationButton, 2, START_OF_VISUALISATIONS_INDEX + visualisationCells.size());
        editingGridPane.add(submitButton, 2, START_OF_VISUALISATIONS_INDEX + visualisationCells.size());
    }

}
