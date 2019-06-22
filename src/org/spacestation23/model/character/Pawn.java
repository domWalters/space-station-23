package org.spacestation23.model.character;

import javafx.scene.image.Image;
import javafx.util.Pair;
import org.spacestation23.model.grid.GridNode;
import org.spacestation23.model.grid.exceptions.FailedMovementException;
import org.spacestation23.model.item.Inventory;
import org.spacestation23.model.item.ItemStack;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import static org.spacestation23.model.item.Inventory.CHARACTER_DEFAULT_INVENTORY_STACK_CAPACITY;

public class Pawn {

    private String name;
    private Inventory inventory;
    private ItemStack equippedItem;

    private GridNode location;
    private Image sprite;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public Pawn(String name, GridNode location, Image sprite) {
        this.setName(name);
        this.setInventory(new Inventory("Personal Inventory of " + this.getName(), CHARACTER_DEFAULT_INVENTORY_STACK_CAPACITY));
        equippedItem = null;
        this.setLocation(location);
        this.setSprite(sprite);
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
        GridNode oldLocation = this.location;
        this.location = location;
        this.pcs.firePropertyChange("pawnLocation", oldLocation, new Pair<>(location, this.sprite));
    }

    public Image getSprite() {
        return sprite;
    }

    public void setSprite(Image sprite) {
        this.sprite = sprite;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    public void move(boolean predicate, int dy, int dx, String direction) throws FailedMovementException {
        GridNode location = this.getLocation();
        if (predicate) {
            GridNode newLocation = location.getGrid().get(location.getY() + dy).get(location.getX() + dx);
            if (newLocation.getMaterial().passable && newLocation.getPawn() == null) {
                location.setPawn(null);
                this.setLocation(newLocation);
                newLocation.setPawn(this);
            } else {
                throw new FailedMovementException(this, direction);
            }
        } else {
            throw new FailedMovementException(this, direction);
        }

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
