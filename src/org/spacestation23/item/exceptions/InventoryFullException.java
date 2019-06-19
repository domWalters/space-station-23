package org.spacestation23.item.exceptions;

import org.spacestation23.item.Inventory;
import org.spacestation23.item.ItemStack;

public class InventoryFullException extends Exception {

    private Inventory inventory;
    private ItemStack itemStack;

    public InventoryFullException(Inventory inventory, ItemStack itemStack) {
        this.setInventory(inventory);
        this.setItemStack(itemStack);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    private void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
