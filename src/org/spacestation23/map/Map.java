package org.spacestation23.map;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.spacestation23.character.Pawn;

public class Map extends GridPane {

    public Map(Grid grid) {//, Scene scene) {
        super();
        this.setPickOnBounds(false);
        //MapContextMenu menu = new MapContextMenu(scene);
        for (int y = 0; y < grid.ySize(); y++) {
            GridRow row = grid.get(y);
            for (int x = 0; x < grid.xSize(y); x++) {
                GridNode cell = row.get(x);
                Tile cellTile = cell.getTile();
                Label cellTileLabel = cellTile.getLabel();
                Pawn cellPawn = cell.getPawn();
//                cellTile.setOnContextMenuRequested(e -> {
//                    menu.show(cellTile, e.getScreenX(), e.getScreenY());
//                });
                if (cellPawn != null) {
                    cellTileLabel.setText(cellPawn.getTile().getSprite());
                } else {
                    cellTileLabel.setText(cellTile.getSprite());
                }
                this.add(cellTile, x, y);
            }
        }
    }

}
