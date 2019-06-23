package org.spacestation23.view.mapEditor;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import org.spacestation23.control.MapEditorController;
import org.spacestation23.model.grid.Grid;
import org.spacestation23.model.grid.GridNode;
import org.spacestation23.model.grid.GridRow;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class Map extends GridPane {

    private Grid grid;
    private List<List<MapCell>> mapCellGrid;

    private MapCell currentFocus;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public Map() {
        super();
    }

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

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public List<List<MapCell>> getMapCellGrid() {
        return mapCellGrid;
    }

    public void setMapCellGrid(List<List<MapCell>> mapCellGrid) {
        this.mapCellGrid = mapCellGrid;
    }

    public MapCell getCurrentFocus() {
        return this.currentFocus;
    }

    public void setCurrentFocus(MapCell mapCell) {
        MapCell oldFocus = this.currentFocus;
        this.currentFocus = mapCell;
        this.pcs.firePropertyChange("currentFocus", oldFocus, this.currentFocus);
    }

    public void overwriteWithMap(Map map) {
        this.setGrid(map.getGrid());
        this.setMapCellGrid(map.getMapCellGrid());
        for (int row = 0; row < map.getGrid().ySize(); row++) {
            for (int col = 0; col < map.getGrid().xSize(row); col++) {
                this.add(this.mapCellGrid.get(row).get(col), col, row);
            }
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
