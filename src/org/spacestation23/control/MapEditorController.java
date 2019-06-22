package org.spacestation23.control;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.spacestation23.model.grid.Grid;
import org.spacestation23.model.grid.GridLoader;
import org.spacestation23.view.mapEditor.Map;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MapEditorController implements Initializable {

    @FXML
    private BorderPane borderPane;

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
            // Do Map Open
        }
    }

    @FXML
    void handleSaveMap() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            // Do Map Save
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        map = new Map(grid);
        borderPane.setCenter(map);
    }

}
