package org.spacestation23.model.grid;

import org.spacestation23.model.character.Pawn;
import org.spacestation23.model.item.Inventory;
import org.spacestation23.model.material.Material;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Objects;

public class GridNode implements Serializable {

    private Integer x;
    private Integer y;
    private Grid grid;
    private Material material;      // Can be observed
    private Integer rotation;
    private Integer visualisationIndex;

    private Inventory inventory;
    private Pawn pawn;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public GridNode(Integer x, Integer y, Grid grid, Material material, Integer rotation, Integer visualisationIndex) {
        super();
        this.x = x;
        this.y = y;
        this.grid = grid;
        this.material = material;
        this.rotation = rotation;
        this.visualisationIndex = visualisationIndex;

        this.inventory = (material.isPassable()) ? new Inventory("Floor at (" + x + ", " + y + ")", material.getInventoryCapacity()) : null;
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
        Material oldMaterial = this.material;
        this.material = material;
        this.pcs.firePropertyChange("material", oldMaterial, this.material);
    }

    public Integer getRotation() {
        return rotation;
    }

    public void setRotation(Integer rotation) {
        Integer oldRotation = this.rotation;
        this.rotation = rotation;
        this.pcs.firePropertyChange("rotation", oldRotation, this.rotation);
    }

    public Integer getVisualisationIndex() {
        return visualisationIndex;
    }

    public void setVisualisationIndex(Integer visualisationIndex) {
        Integer oldVisualisationIndex = this.visualisationIndex;
        this.visualisationIndex = visualisationIndex;
        this.pcs.firePropertyChange("visualisationIndex", oldVisualisationIndex, this.visualisationIndex);
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
                y.equals(gridNode.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}