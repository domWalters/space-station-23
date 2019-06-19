package org.spacestation23.item.exceptions;

import org.spacestation23.item.ItemStack;

public class UnderfullItemStackException extends Exception {

    private ItemStack itemStack;
    private int underflow;

    public UnderfullItemStackException(ItemStack itemStack, int underflow) {
        this.setItemStack(itemStack);
        this.setUnderflow(underflow);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    private void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public int getUnderflow() {
        return underflow;
    }

    private void setUnderflow(int underflow) {
        this.underflow = underflow;
    }
}
