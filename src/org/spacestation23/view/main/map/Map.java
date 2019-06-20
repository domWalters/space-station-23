package org.spacestation23.view.main.map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.spacestation23.model.LoggerStore;
import org.spacestation23.model.character.Pawn;
import org.spacestation23.model.grid.Grid;
import org.spacestation23.model.grid.GridNode;
import org.spacestation23.model.grid.GridRow;

import java.io.IOException;

public class Map extends GridPane {

    public Map(Grid grid, LoggerStore loggerStore) {
        super();
        this.setPickOnBounds(false);
        for (int y = 0; y < grid.ySize(); y++) {
            GridRow row = grid.get(y);
            for (int x = 0; x < grid.xSize(y); x++) {
                GridNode cell = row.get(x);
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Tile.fxml"));
                    // Create a controller instance
                    Tile tile = new Tile(cell.getSprite(), cell, loggerStore);
                    loader.setController(tile);
                    Group group = loader.load();

                    Label cellTileLabel = tile.getLabel();
                    Pawn cellPawn = cell.getPawn();
                    if (cellPawn != null) {
                        cellTileLabel.setText(cellPawn.getSprite());
                    } else {
                        cellTileLabel.setText(tile.getSprite());
                    }
                    this.add(group, x, y);
                } catch (IOException e) {

                }
            }
        }
    }

}
