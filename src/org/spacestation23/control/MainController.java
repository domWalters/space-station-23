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
import org.spacestation23.view.itemManager.ItemManagerApplication;
import org.spacestation23.view.main.SpriteFactory;
import org.spacestation23.view.mapEditor.Map;
import org.spacestation23.view.mapEditor.MapCell;
import org.spacestation23.view.mapEditor.MapEditorApplication;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, PropertyChangeListener {

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextArea logger;

//    @FXML
    private Map map;

    private Grid grid;
    private LoggerStore loggerStore;

    public MainController() {
        grid = GridLoader.loadFromFile("mapFiles/exampleGrid1.txt");
        Pawn pawn1 = new Pawn("Dom", grid.get(3 - 1).get(3 - 1), SpriteFactory.pawnImg);
        Pawn pawn2 = new Pawn("David", grid.get(6 - 1).get(6 - 1), SpriteFactory.pawnImg);
        loggerStore = new LoggerStore(3);
        loggerStore.addPropertyChangeListener(this);
        pawn1.addPropertyChangeListener(this);
        pawn2.addPropertyChangeListener(this);
        ItemCreator.populateItemsFromFile("mapFiles/items.xml");
    }

    @FXML
    void handleOpenItemManager() {
        ItemManagerApplication itemManager = new ItemManagerApplication();
        itemManager.show();
    }

    @FXML
    void handleOpenMapEditor() {
        MapEditorApplication mapEditor = new MapEditorApplication();
        mapEditor.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        map = new Map(grid);
        map.setAllKeyPressed(loggerStore);
        borderPane.setLeft(map);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("lines") && logger != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String line : (String[]) evt.getNewValue()) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            logger.setText(stringBuilder.toString());
        } else if (evt.getPropertyName().equals("pawnLocation") && map != null) {
            GridNode oldLocation = (GridNode) evt.getOldValue();
            Pair newLocationPair = (Pair) evt.getNewValue();
            GridNode newLocation = (GridNode) newLocationPair.getKey();
            Image pawnSprite = (Image) newLocationPair.getValue();
            for (Node node : map.getChildren()) {
                MapCell mapCell = (MapCell) node;
                int row = GridPane.getRowIndex(node);
                int col = GridPane.getColumnIndex(node);
                if (oldLocation.getX() == col && oldLocation.getY() == row) {
                    mapCell.disablePawn();
                }
                if (newLocation.getX() == col && newLocation.getY() == row) {
                    mapCell.enablePawn(pawnSprite);
                    mapCell.requestFocus();
                }
            }
        }
    }
}
