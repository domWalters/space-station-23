package org.spacestation23.view.mapEditor;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import org.spacestation23.model.grid.Grid;
import org.spacestation23.model.grid.GridNode;
import org.spacestation23.model.grid.GridRow;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class MapEditorMap extends GridPane {

    private Grid grid;
    private List<List<MapEditorMapCell>> mapCellGrid;

    private MapEditorMapCell currentFocus;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public MapEditorMap() {
        super();
    }

    public MapEditorMap(Grid grid, PropertyChangeListener controller) {
        super();
        this.grid = grid;
        this.mapCellGrid = new ArrayList<>();
        for (int y = 0; y < grid.ySize(); y++) {
            List<MapEditorMapCell> mapEditorMapCellRow = new ArrayList<>();
            GridRow row = grid.get(y);
            for (int x = 0; x < grid.xSize(y); x++) {
                GridNode node = row.get(x);
                node.addPropertyChangeListener(controller);
                MapEditorMapCell mapEditorMapCell = new MapEditorMapCell(node);
                mapEditorMapCell.focusedProperty().addListener((ov, oldV, newV) -> {
                    if (newV) {
                        this.setCurrentFocus(mapEditorMapCell);
                    }
                });
                mapEditorMapCellRow.add(mapEditorMapCell);
                this.add(mapEditorMapCell, x, y);
            }
            mapCellGrid.add(mapEditorMapCellRow);
        }
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public List<List<MapEditorMapCell>> getMapCellGrid() {
        return mapCellGrid;
    }

    public void setMapCellGrid(List<List<MapEditorMapCell>> mapCellGrid) {
        this.mapCellGrid = mapCellGrid;
    }

    public MapEditorMapCell getCurrentFocus() {
        return this.currentFocus;
    }

    public void setCurrentFocus(MapEditorMapCell mapEditorMapCell) {
        MapEditorMapCell oldFocus = this.currentFocus;
        this.currentFocus = mapEditorMapCell;
        this.pcs.firePropertyChange("currentFocus", oldFocus, this.currentFocus);
    }

    public void overwriteWithMap(MapEditorMap mapEditorMap) {
        this.setGrid(mapEditorMap.getGrid());
        this.setMapCellGrid(mapEditorMap.getMapCellGrid());
        for (int row = 0; row < mapEditorMap.getGrid().ySize(); row++) {
            for (int col = 0; col < mapEditorMap.getGrid().xSize(row); col++) {
                this.add(this.mapCellGrid.get(row).get(col), col, row);
            }
        }
    }

    public MapEditorMapCell getMapCellFromGridNode(GridNode gridNode) {
        return this.mapCellGrid.get(gridNode.getY()).get(gridNode.getX());
    }

    public GridNode getGridNodeFromMapCell(MapEditorMapCell mapEditorMapCell) {
        int mapCellRow = GridPane.getRowIndex(mapEditorMapCell);
        int mapCellCol = GridPane.getColumnIndex(mapEditorMapCell);
        return grid.get(mapCellRow).get(mapCellCol);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    public void setOnKeyPressed(int row, int col, EventHandler<? super KeyEvent> eventHandler) {
        mapCellGrid.get(row).get(col).setOnKeyPressed(eventHandler);
    }

}
