package org.spacestation23.model.item.exceptions;

import org.spacestation23.model.item.Item;

public class NoItemStackException extends Exception {

    private Item item;

    public NoItemStackException(Item item) {
        this.setItem(item);
    }

    public Item getItem() {
        return item;
    }

    private void setItem(Item item) {
        this.item = item;
    }
}
