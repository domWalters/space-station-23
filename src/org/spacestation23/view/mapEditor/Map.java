package org.spacestation23.view.mapEditor;

import javafx.scene.layout.GridPane;
import org.spacestation23.model.LoggerStore;
import org.spacestation23.model.character.Pawn;
import org.spacestation23.model.grid.Grid;
import org.spacestation23.model.grid.GridRow;
import org.spacestation23.model.grid.exceptions.FailedMovementException;

import java.util.ArrayList;
import java.util.List;

public class Map extends GridPane {

    private Grid grid;
    private List<List<MapCell>> mapCellGrid;

    public Map(Grid grid) {
        super();
        this.grid = grid;
        this.mapCellGrid = new ArrayList<>();
        for (int y = 0; y < grid.ySize(); y++) {
            List<MapCell> mapCellRow = new ArrayList<>();
            GridRow row = grid.get(y);
            for (int x = 0; x < grid.xSize(y); x++) {
                MapCell mapCell = new MapCell(row.get(x));
                mapCellRow.add(mapCell);
                this.add(mapCell, x, y);
            }
            mapCellGrid.add(mapCellRow);
        }
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
