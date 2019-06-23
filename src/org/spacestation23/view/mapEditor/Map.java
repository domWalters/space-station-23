package org.spacestation23.view.mapEditor;

import javafx.scene.layout.GridPane;
import org.spacestation23.control.MapEditorController;
import org.spacestation23.view.main.LoggerStore;
import org.spacestation23.model.character.Pawn;
import org.spacestation23.model.grid.Grid;
import org.spacestation23.model.grid.GridNode;
import org.spacestation23.model.grid.GridRow;
import org.spacestation23.model.grid.exceptions.FailedMovementException;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class Map extends GridPane {

    private Grid grid;
    private List<List<MapCell>> mapCellGrid;

    private MapCell currentFocus;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public Map(Grid grid, MapEditorController controller) {
        super();
        this.grid = grid;
        this.mapCellGrid = new ArrayList<>();
        for (int y = 0; y < grid.ySize(); y++) {
            List<MapCell> mapCellRow = new ArrayList<>();
            GridRow row = grid.get(y);
            for (int x = 0; x < grid.xSize(y); x++) {
                GridNode node = row.get(x);
                node.addPropertyChangeListener(controller);
                MapCell mapCell = new MapCell(node);
                mapCell.focusedProperty().addListener((ov, oldV, newV) -> {
                    if (newV) {
                        this.setCurrentFocus(mapCell);
                    }
                });
                mapCellRow.add(mapCell);
                this.add(mapCell, x, y);
            }
            mapCellGrid.add(mapCellRow);
        }
    }

    public MapCell getMapCellFromGridNode(GridNode gridNode) {
        return this.mapCellGrid.get(gridNode.getY()).get(gridNode.getX());
    }

    public GridNode getGridNodeFromMapCell(MapCell mapCell) {
        int mapCellRow = GridPane.getRowIndex(mapCell);
        int mapCellCol = GridPane.getColumnIndex(mapCell);
        return grid.get(mapCellRow).get(mapCellCol);
    }

    public void setCurrentFocus(MapCell mapCell) {
        MapCell oldFocus = this.currentFocus;
        this.currentFocus = mapCell;
        this.pcs.firePropertyChange("currentFocus", oldFocus, this.currentFocus);
    }

    public MapCell getCurrentFocus() {
        return this.currentFocus;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    public void setOnKeyPressed(LoggerStore loggerStore, int row, int col) {
        mapCellGrid.get(row).get(col).setOnKeyPressed(event -> {
            Pawn clickedPawn = grid.get(row).get(col).getPawn();
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

    public void setAllKeyPressed(LoggerStore loggerStore) {
        for (int row = 0; row < grid.ySize(); row++) {
            for (int col = 0; col < grid.xSize(row); col++) {
                this.setOnKeyPressed(loggerStore, row, col);
            }
        }
    }

}
