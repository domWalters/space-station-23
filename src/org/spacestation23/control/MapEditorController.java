package org.spacestation23.control;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.spacestation23.model.Material;
import org.spacestation23.model.grid.Grid;
import org.spacestation23.model.grid.GridLoader;
import org.spacestation23.view.mapEditor.Map;
import org.spacestation23.view.mapEditor.MapEditorMaterialCell;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MapEditorController implements Initializable {

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
            map = new Map(grid);
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        map = new Map(grid);
        borderPane.setCenter(map);
        materialComboBox.getItems().addAll(Material.VACUUM, Material.STEELBULKHEAD, Material.STEELBULKHEADDOOR, Material.STEELBULKHEADFLOOR);
        materialComboBox.setCellFactory(materialComboBox -> new MapEditorMaterialCell());
    }

}
