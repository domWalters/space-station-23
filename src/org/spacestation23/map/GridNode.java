package org.spacestation23.map;

import javafx.scene.control.Label;
import javafx.util.Pair;
import org.spacestation23.character.Pawn;
import org.spacestation23.item.Inventory;

public class GridNode {

    private Integer x;
    private Integer y;
    private Grid grid;

    private Boolean passable;
    private Tile tile;
    private Inventory inventory;
    private Pawn pawn;

    public GridNode(Integer x, Integer y, Grid grid, Pair<Boolean, Integer> passableAndCapacity, Tile tile) {
        this.setX(x);
        this.setY(y);
        this.setGrid(grid);
        this.setPassable(passableAndCapacity.getKey());
        this.setTile(tile);
        if (passableAndCapacity.getKey()) {
            this.setInventory(new Inventory("Floor at (" + x + ", " + y + ")", passableAndCapacity.getValue()));
        } else {
            this.setInventory(null);
        }
        this.setPawn(null);
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public Boolean isPassable() {
        return passable && (this.getPawn() == null);
    }

    public void setPassable(Boolean passable) {
        this.passable = passable;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
        Tile tile = this.getTile();
        Label tileLabel = tile.getLabel();
        if (pawn == null) {
            tileLabel.setText(tile.getSprite());
        } else {
            tileLabel.setText(this.getPawn().getTile().getSprite());
        }
    }

    public void nullPawn() {
        this.setPawn(null);
        Tile tile = this.getTile();
        tile.getLabel().setText(tile.getSprite());
    }

    @Override
    public String toString() {
        return "(" + this.getX() + ", " + this.getY() + ")";
    }
}