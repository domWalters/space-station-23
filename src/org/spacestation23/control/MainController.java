package org.spacestation23.control;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.util.Pair;
import org.spacestation23.model.character.Pawn;
import org.spacestation23.model.grid.Grid;
import org.spacestation23.model.grid.GridCreator;
import org.spacestation23.model.grid.GridNode;
import org.spacestation23.model.grid.exceptions.FailedMovementException;
import org.spacestation23.model.item.ItemCreator;
import org.spacestation23.view.itemEditor.ItemEditorApplication;
import org.spacestation23.view.main.LoggerStore;
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
    private LoggerStore loggerStore;

    @FXML
    private Map map;

    private Grid grid;

    public MainController() {
        grid = GridCreator.loadFromFile("mapFiles/exampleGrid1.txt");
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
        map.overwriteWithMap(new Map(grid, null));
        for (int row = 0; row < grid.ySize(); row++) {
            for (int col = 0; col < grid.xSize(row); col++) {
                final int lambaRow = row;
                final int lambaCol = col;
                map.setOnKeyPressed(lambaRow, lambaCol, event -> {
                    Pawn clickedPawn = grid.get(lambaRow).get(lambaCol).getPawn();
                    if (clickedPawn != null) {
                        try {
                            switch(event.getCode()) {
                                case W:
                                    clickedPawn.moveUp(); break;
                                case D:
                                    clickedPawn.moveRight(); break;
                                case S:
                                    clickedPawn.moveDown(); break;
                                case A:
                                    clickedPawn.moveLeft(); break;
                            }
                        } catch (FailedMovementException e) {
                            loggerStore.scroll("FAILED MOVEMENT FROM " + e.getPawn().getLocation() + " IN DIRECTION " + e.getDirection() + ".");
                        }
                    } else {
                        loggerStore.scroll("NO PAWN IN FOCUS TILE");
                    }
                });
            }
        }
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
