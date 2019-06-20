package org.spacestation23.model.item.exceptions;

import org.spacestation23.model.item.ItemStack;

public class EmptyItemStackException extends Exception {

    private ItemStack itemStack;

    public EmptyItemStackException(ItemStack itemStack) {
        this.setItemStack(itemStack);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    private void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
