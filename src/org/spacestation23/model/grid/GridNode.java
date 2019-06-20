package org.spacestation23.model.grid;

import javafx.util.Pair;
import org.spacestation23.model.character.Pawn;
import org.spacestation23.model.item.Inventory;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GridNode {

    private Integer x;
    private Integer y;
    private Grid grid;

    private Boolean passable;
    private String sprite;
    private Inventory inventory;
    private Pawn pawn;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public GridNode(Integer x, Integer y, Grid grid, Pair<Boolean, Integer> passableAndCapacity, String sprite) {
        super();
        this.setX(x);
        this.setY(y);
        this.setGrid(grid);
        this.setPassable(passableAndCapacity.getKey());
        this.setSprite(sprite);
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

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
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
        Pawn oldPawn = this.getPawn();
        this.pawn = pawn;
        this.pcs.firePropertyChange("pawn", oldPawn, this.getPawn());
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    @Override
    public String toString() {
        return "(" + this.getX() + ", " + this.getY() + ")";
    }

}