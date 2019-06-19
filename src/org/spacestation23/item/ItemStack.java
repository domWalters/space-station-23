package org.spacestation23.item;

import org.spacestation23.item.exceptions.EmptyItemStackException;
import org.spacestation23.item.exceptions.OverfullItemStackException;
import org.spacestation23.item.exceptions.UnderfullItemStackException;

public class ItemStack {

    private Item item;
    private int quantity;

    public ItemStack(Item item, int quantity) {
        this.setItem(item);
        this.setQuantity(quantity);
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isFull() {
        return this.getQuantity() == this.getItem().getStackCapacity();
    }

    public void add(int quantity) throws OverfullItemStackException {
        if (this.getQuantity() + quantity > this.getItem().getStackCapacity()) {
            this.setQuantity(this.getItem().getStackCapacity());
            throw new OverfullItemStackException(this, this.getQuantity() + quantity - this.getItem().getStackCapacity());
        } else {
            this.setQuantity(this.getQuantity() + quantity);
        }
    }

    public void remove(int quantity) throws UnderfullItemStackException, EmptyItemStackException {
        if (this.getQuantity() - quantity < 0) {
            throw new UnderfullItemStackException(this, quantity - this.getQuantity());
        } else if (this.getQuantity() - quantity == 0) {
            this.setQuantity(0);
            throw new EmptyItemStackException(this);
        } else {
            this.setQuantity(this.getQuantity() - quantity);
        }
    }
}
