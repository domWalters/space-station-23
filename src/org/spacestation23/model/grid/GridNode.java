package org.spacestation23.model.grid;

import org.spacestation23.model.material.Material;
import org.spacestation23.model.character.Pawn;
import org.spacestation23.model.item.Inventory;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Objects;

public class GridNode implements Serializable {

    private Integer x;
    private Integer y;
    private Grid grid;

    private Material material;      // Can be observed
    private Inventory inventory;
    private Pawn pawn;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public GridNode(Integer x, Integer y, Grid grid, Material material) {
        super();
        this.x = x;
        this.y = y;
        this.grid = grid;
        this.material = material;
        this.inventory = (material.passable) ? new Inventory("Floor at (" + x + ", " + y + ")", material.inventoryCapacity) : null;
        this.pawn = null;
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

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        Material oldMaterial = this.getMaterial();
        this.material = material;
        this.pcs.firePropertyChange("material", oldMaterial, this.material);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GridNode)) return false;
        GridNode gridNode = (GridNode) o;
        return x.equals(gridNode.x) &&
                y.equals(gridNode.y) &&
                Objects.equals(grid, gridNode.grid) &&
                material.equals(gridNode.material) &&
                Objects.equals(inventory, gridNode.inventory) &&
                Objects.equals(pawn, gridNode.pawn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, grid, material, inventory, pawn);
    }
}