package org.spacestation23.map;

import java.util.ArrayList;
import java.util.List;

public class GridRow {

    private List<GridNode> gridRow;

    public GridRow(List<GridNode> gridRow) {
        this.setGridRow(gridRow);
    }

    public GridRow() {
        this.setGridRow(new ArrayList<>());
    }

    public List<GridNode> getGridRow() {
        return gridRow;
    }

    public GridNode get(int index) {
        return this.getGridRow().get(index);
    }

    public void setGridRow(List<GridNode> gridRow) {
        this.gridRow = gridRow;
    }

    public void add(GridNode gridNode) {
        this.getGridRow().add(gridNode);
    }

    public int size() {
        return this.getGridRow().size();
    }

}
