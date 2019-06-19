package org.spacestation23.character;

import org.spacestation23.item.Inventory;
import org.spacestation23.item.ItemStack;
import org.spacestation23.map.GridNode;
import org.spacestation23.map.Tile;
import org.spacestation23.map.exceptions.FailedMovementException;

import static org.spacestation23.item.Inventory.CHARACTER_DEFAULT_INVENTORY_STACK_CAPACITY;

public class Pawn {

    private String name;
    private Inventory inventory;
    private ItemStack equippedItem;

    private GridNode location;
    private Tile tile;

    public Pawn(String name, GridNode location, Tile tile) {
        this.setName(name);
        this.setInventory(new Inventory("Personal Inventory of " + this.getName(), CHARACTER_DEFAULT_INVENTORY_STACK_CAPACITY));
        equippedItem = null;
        this.setLocation(location);
        this.setTile(tile);

        location.setPawn(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public ItemStack getEquippedItem() {
        return equippedItem;
    }

    public void setEquippedItem(ItemStack equippedItem) {
        this.equippedItem = equippedItem;
    }

    public GridNode getLocation() {
        return location;
    }

    public void setLocation(GridNode location) {
        this.location = location;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public void move(boolean predicate, int dy, int dx, String direction) throws FailedMovementException {
        GridNode location = this.getLocation();
        if (predicate) {
            GridNode newLocation = location.getGrid().get(location.getY() + dy).get(location.getX() + dx);
            if (newLocation.isPassable()) {
                location.nullPawn();
                this.setLocation(newLocation);
                newLocation.setPawn(this);
            } else {
                throw new FailedMovementException(this, direction);
            }
        } else {
            throw new FailedMovementException(this, direction);
        }
        this.getLocation().getTile().requestFocus();
    }

    public void moveUp() throws FailedMovementException {
        this.move(this.getLocation().getY() > 0, -1, 0, "U");
    }

    public void moveRight() throws FailedMovementException {
        GridNode location = this.getLocation();
        this.move(location.getX() < location.getGrid().xSize(location.getY()) - 1, 0, 1, "R");
    }

    public void moveDown() throws FailedMovementException {
        GridNode location = this.getLocation();
        this.move(location.getY() < location.getGrid().ySize() - 1, 1, 0, "D");
    }

    public void moveLeft() throws FailedMovementException {
        this.move(this.getLocation().getX() > 0, 0, -1, "L");
    }

}
