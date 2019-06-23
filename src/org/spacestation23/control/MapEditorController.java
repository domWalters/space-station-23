package org.spacestation23.control;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.spacestation23.model.grid.Grid;
import org.spacestation23.model.grid.GridCreator;
import org.spacestation23.model.grid.GridNode;
import org.spacestation23.model.material.Material;
import org.spacestation23.model.material.MaterialCreator;
import org.spacestation23.view.mapEditor.Map;
import org.spacestation23.view.mapEditor.MapCell;
import org.spacestation23.view.mapEditor.MapEditorMaterialCell;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MapEditorController implements Initializable, PropertyChangeListener {

    @FXML
    private BorderPane borderPane;

    @FXML
    private ComboBox<Material> materialComboBox;

    @FXML
    private Map map;

    private Grid grid;

    public MapEditorController() {}

    @FXML
    void handleOpenMap() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            grid = GridCreator.loadFromFile(file);
            map = new Map(grid, this);
            map.addPropertyChangeListener(this);
            borderPane.setCenter(map);
            borderPane.getScene().getWindow().sizeToScene();
        }
    }

    @FXML
    void handleSaveMap() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            GridCreator.saveToFile(grid, file);
        }
    }

    @FXML
    void handleMaterialComboBoxSelectionChanged() {
        MapCell currentFocus = map.getCurrentFocus();
        if (currentFocus != null) {
            map.getGridNodeFromMapCell(currentFocus).setMaterial(materialComboBox.getValue());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        materialComboBox.getItems().addAll(MaterialCreator.materials.values());
        materialComboBox.setCellFactory(materialComboBox -> new MapEditorMaterialCell());
        materialComboBox.getSelectionModel().selectFirst();
        materialComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Material material) {
                return material.getName();
            }

            @Override
            public Material fromString(String string) {
                return materialComboBox.getItems().stream().filter(ap ->
                        ap.getName().equals(string)).findFirst().orElse(null);
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("currentFocus") && materialComboBox != null) {
            MapCell newSelection = (MapCell) evt.getNewValue();
            GridNode newSelectionGridNode = map.getGridNodeFromMapCell(newSelection);
            materialComboBox.getSelectionModel().select(newSelectionGridNode.getMaterial());
        } else if (evt.getPropertyName().equals("material") && map != null) {
            GridNode location = (GridNode) evt.getSource();
            map.getMapCellFromGridNode(location).setMaterial((Material) evt.getNewValue());
        }
    }
}
