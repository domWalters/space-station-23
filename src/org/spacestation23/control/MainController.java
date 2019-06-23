package org.spacestation23.control;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import org.spacestation23.model.LoggerStore;
import org.spacestation23.model.character.Pawn;
import org.spacestation23.model.grid.Grid;
import org.spacestation23.model.grid.GridLoader;
import org.spacestation23.model.grid.GridNode;
import org.spacestation23.model.item.ItemCreator;
import org.spacestation23.view.itemEditor.ItemEditorApplication;
import org.spacestation23.view.main.SpriteFactory;
import org.spacestation23.view.mapEditor.Map;
import org.spacestation23.view.mapEditor.MapCell;
import org.spacestation23.view.mapEditor.MapEditorApplication;
import org.spacestation23.view.materialEditor.MaterialEditorApplication;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, PropertyChangeListener {

    @FXML
    private BorderPane borderPane;

    @FXML
    private LoggerStore loggerStore;

    private Map map;

    private Grid grid;

    public MainController() {
        grid = GridLoader.loadFromFile("mapFiles/exampleGrid1.txt");
        Pawn pawn1 = new Pawn("Dom", grid.get(3 - 1).get(3 - 1), SpriteFactory.pawnImg);
        Pawn pawn2 = new Pawn("David", grid.get(6 - 1).get(6 - 1), SpriteFactory.pawnImg);
        pawn1.addPropertyChangeListener(this);
        pawn2.addPropertyChangeListener(this);
        ItemCreator.populateItemsFromFile("mapFiles/items.xml");
    }

    @FXML
    void handleOpenItemEditor() {
        ItemEditorApplication itemManager = new ItemEditorApplication();
        itemManager.show();
    }

    @FXML
    void handleOpenMapEditor() {
        MapEditorApplication mapEditor = new MapEditorApplication();
        mapEditor.show();
    }

    @FXML
    void handleOpenMaterialEditor() {
        MaterialEditorApplication materialEditor = new MaterialEditorApplication();
        materialEditor.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        map = new Map(grid, null);
        map.setAllKeyPressed(loggerStore);
        borderPane.setLeft(map);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("location") && map != null) {
            map.getMapCellFromGridNode((GridNode) evt.getOldValue()).disablePawn();
            Pair newLocationPair = (Pair) evt.getNewValue();
            MapCell newMapCell = map.getMapCellFromGridNode((GridNode) newLocationPair.getKey());
            newMapCell.enablePawn((Image) newLocationPair.getValue());
            newMapCell.requestFocus();
        }
    }
}
