package org.spacestation23.control;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.spacestation23.model.material.Material;
import org.spacestation23.model.grid.Grid;
import org.spacestation23.model.grid.GridLoader;
import org.spacestation23.model.grid.GridNode;
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

    private Map map;

    private Grid grid;

    public MapEditorController() {
        grid = GridLoader.loadFromFile("mapFiles/exampleGrid1.txt");
    }

    @FXML
    void handleOpenMap() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            grid = GridLoader.loadFromFile(file);
            map = new Map(grid, this);
            borderPane.setCenter(map);
        }
    }

    @FXML
    void handleSaveMap() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            GridLoader.saveToFile(grid, file);
        }
    }

    @FXML
    void handleMaterialComboBoxSelectionChanged() {
        MapCell currentFocus = map.getCurrentFocus();
        if (currentFocus != null) {
            int focusedRow = GridPane.getRowIndex(currentFocus);
            int focusedCol = GridPane.getColumnIndex(currentFocus);
            for (Node node : map.getChildren()) {
                int row = GridPane.getRowIndex(node);
                int col = GridPane.getColumnIndex(node);
                if (focusedCol == col && focusedRow == row) {
                    GridNode selected = grid.get(row).get(col);
                    selected.setMaterial(materialComboBox.getValue());
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        map = new Map(grid, this);
        map.addPropertyChangeListener(this);
        borderPane.setCenter(map);
        Material vacuum = MaterialCreator.materials.get("VACUUM");
        Material steelBulkhead = MaterialCreator.materials.get("STEELBULKHEAD");
        Material steelBulkheadDoor = MaterialCreator.materials.get("STEELBULKHEADDOOR");
        Material steelBulkheadFloor = MaterialCreator.materials.get("STEELBULKHEADFLOOR");
        materialComboBox.getItems().addAll(vacuum, steelBulkhead, steelBulkheadDoor, steelBulkheadFloor);
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
            int newRow = GridPane.getRowIndex(newSelection);
            int newCol = GridPane.getColumnIndex(newSelection);
            for (Node node : map.getChildren()) {
                int row = GridPane.getRowIndex(node);
                int col = GridPane.getColumnIndex(node);
                if (newCol == col && newRow == row) {
                    materialComboBox.getSelectionModel().select(grid.get(row).get(col).getMaterial());
                }
            }
        } else if (evt.getPropertyName().equals("material") && map != null) {
            GridNode location = (GridNode) evt.getSource();
            for (Node node : map.getChildren()) {
                MapCell mapCell = (MapCell) node;
                int row = GridPane.getRowIndex(node);
                int col = GridPane.getColumnIndex(node);
                if (location.getX() == col && location.getY() == row) {
                    mapCell.setMaterial((Material) evt.getNewValue());
                }
            }
        }
    }
}
