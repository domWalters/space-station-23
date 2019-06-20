package org.spacestation23.model.item.exceptions;

import org.spacestation23.model.item.ItemStack;

public class OverfullItemStackException extends Exception {

    private ItemStack itemStack;
    private int overflow;

    public OverfullItemStackException(ItemStack itemStack, int overflow) {
        this.setItemStack(itemStack);
        this.setOverflow(overflow);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    private void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public int getOverflow() {
        return overflow;
    }

    private void setOverflow(int overflow) {
        this.overflow = overflow;
    }
}
