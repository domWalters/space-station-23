package org.spacestation23.model.grid;

import org.spacestation23.model.Material;
import org.spacestation23.model.character.Pawn;
import org.spacestation23.model.item.Inventory;

public class GridNode {

    private Integer x;
    private Integer y;
    private Grid grid;

    private Material material;
    private Inventory inventory;
    private Pawn pawn;

    public GridNode(Integer x, Integer y, Grid grid, Material material) {
        super();
        this.setX(x);
        this.setY(y);
        this.setGrid(grid);
        this.setMaterial(material);
        this.setInventory((material.passable) ? new Inventory("Floor at (" + x + ", " + y + ")", material.inventoryCapacity) : null);
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

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
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

    @Override
    public String toString() {
        return "(" + this.getX() + ", " + this.getY() + ")";
    }

}