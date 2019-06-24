package org.spacestation23.model.grid;

import java.util.ArrayList;
import java.util.List;

public class Grid {

    private List<GridRow> grid;

    public Grid(List<GridRow> grid) {
        this.setGrid(grid);
    }

    public Grid() {
        this.setGrid(new ArrayList<>());
    }

    public List<GridRow> getGrid() {
        return grid;
    }

    public void setGrid(List<GridRow> grid) {
        this.grid = grid;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (GridRow row : this.getGrid()) {
            for (GridNode cell : row.getGridRow()) {
                if (cell.getPawn() == null) {
                    s.append(cell.getMaterial().getVisualisations().get(0).getCharacterSprite());
                } else {
                    s.append(cell.getPawn().getSprite());
                }
            }
            s.append("\n");
        }
        return s.toString();
    }

    public int ySize() {
        return this.getGrid().size();
    }

    public int xSize(int rowIndex) {
        return this.getGrid().get(rowIndex).size();
    }

    public GridRow get(int index) {
        return this.getGrid().get(index);
    }

    public void add(GridRow row) {
        this.getGrid().add(row);
    }

}
