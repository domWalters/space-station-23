package org.spacestation23.item;

import org.spacestation23.item.exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    public final static Integer CHARACTER_DEFAULT_INVENTORY_STACK_CAPACITY = 10;

    private String name;
    private List<ItemStack> itemStacks;

    private Integer stackCapacity;

    public Inventory(String name, List<ItemStack> itemStacks, Integer stackCapacity) {
        this.setName(name);
        this.setItemStacks(itemStacks);
        this.setStackCapacity(stackCapacity);
    }

    public Inventory(String name, Integer stackCapacity) {
        this.setName(name);
        this.setItemStacks(new ArrayList<>());
        this.setStackCapacity(stackCapacity);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ItemStack> getItemStacks() {
        return itemStacks;
    }

    public void setItemStacks(List<ItemStack> itemStacks) {
        this.itemStacks = itemStacks;
    }

    public Integer getStackCapacity() {
        return stackCapacity;
    }

    public void setStackCapacity(Integer stackCapacity) {
        this.stackCapacity = stackCapacity;
    }

    public ItemStack getNonFullItemStack(Item item) throws NoNonFullItemStackException {
        for(ItemStack itemStack : this.getItemStacks()) {
            if (itemStack.getItem().equals(item) && !itemStack.isFull()) {
                return itemStack;
            }
        }
        throw new NoNonFullItemStackException(item);
    }

    public ItemStack getFullItemStack(Item item) throws NoFullItemStackException {
        for(ItemStack itemStack : this.getItemStacks()) {
            if (itemStack.getItem().equals(item) && itemStack.isFull()) {
                return itemStack;
            }
        }
        throw new NoFullItemStackException(item);
    }

    public ItemStack getAnyItemStackPreferringNonFull(Item item) throws NoItemStackException {
        // Requires 2n operations in worst case
        try {
            return this.getNonFullItemStack(item);
        } catch (NoNonFullItemStackException  e1) {
            try {
                return this.getFullItemStack(item);
            } catch (NoFullItemStackException e2) {
                throw new NoItemStackException(item);
            }
        }
    }

    public void addItemStack(ItemStack itemStack) throws InventoryFullException {
        // Obtain a stack to add this to
        if (itemStack.isFull()) {
            if (this.getItemStacks().size() < this.getStackCapacity()) {
                this.getItemStacks().add(itemStack);
            } else {
                throw new InventoryFullException(this, itemStack);
            }
        } else {
            ItemStack stackToAddTo;
            try {
                stackToAddTo = this.getNonFullItemStack(itemStack.getItem());
                // Add to this stack, and handle overflow
                try {
                    stackToAddTo.add(itemStack.getQuantity());
                } catch (OverfullItemStackException e) {
                    this.addItemStack(new ItemStack(itemStack.getItem(), e.getOverflow()));
                }
            } catch (NoNonFullItemStackException e) {
                this.getItemStacks().add(itemStack);
            }
        }
    }

    public void addItemStacks(List<ItemStack> itemStacks) throws InventoryFullException {
        // Could make this more efficient by moving all full stacks first
        for (ItemStack itemStack : itemStacks) {
            this.addItemStack(itemStack);
        }
    }

    public void removeItemStack(ItemStack itemStack) throws NoItemStackException {
        // Obtain a stack to remove this from
        ItemStack stackToRemoveFrom = this.getAnyItemStackPreferringNonFull(itemStack.getItem());
        // Remove from this stack, and handle overflow
        try {
            stackToRemoveFrom.remove(itemStack.getQuantity());
        } catch (UnderfullItemStackException e) {
            this.removeItemStack(new ItemStack(itemStack.getItem(), e.getUnderflow()));
        } catch (EmptyItemStackException e) {
            this.getItemStacks().remove(stackToRemoveFrom); //remove the empty stack
        }
    }

    public void removeItemStacks(List<ItemStack> itemStacks) throws RemoveItemStacksException {
        List<NoItemStackException> errors = new ArrayList<>();
        for (ItemStack itemStack : itemStacks) {
            try {
                this.removeItemStack(itemStack);
            } catch (NoItemStackException e) {
                errors.add(e);
            }
        }
        if (errors.size() > 0) {
            throw new RemoveItemStacksException(errors);
        }
    }

}
