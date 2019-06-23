package org.spacestation23.model.item;

import java.util.Objects;

public class Item {

    public static final String INVALID_NAME = "";
    public static final int INVALID_ITEM_ID = -1;
    public static final int INVALID_STACK_CAPACITY = -1;

    private String name;
    private int itemId;
    private int stackCapacity;

    public Item(String name, int itemId, int stackCapacity) {
        this.setName(name);
        this.setItemId(itemId);
        this.setStackCapacity(stackCapacity);
    }

    public Item(String itemName, String itemId, String itemStackCapacity) {
        if (!itemName.equals(INVALID_NAME)) {
            this.setName(itemName);
            try {
                int itemIdNumber;
                if (itemId.equals("")) {
                    itemIdNumber = (int) (Math.random() * 10000);
                    while (ItemCreator.items.get(itemIdNumber) != null) {
                        itemIdNumber = (int) (Math.random() * 10000);
                    }
                } else {
                    itemIdNumber = Integer.parseInt(itemId);
                }

                if (itemIdNumber >= 0 && itemIdNumber <= 10000) {
                    this.setItemId(itemIdNumber);
                    try {
                        int itemStackCapacityNumber = Integer.parseInt(itemStackCapacity);
                        this.setStackCapacity(itemStackCapacityNumber);
                    } catch (NumberFormatException f) {
                        this.setStackCapacity(INVALID_STACK_CAPACITY);
                    }
                } else {
                    this.setItemId(INVALID_ITEM_ID);
                }
            } catch (NumberFormatException f) {
                this.setItemId(INVALID_ITEM_ID);
            }
        } else {
            this.setName(INVALID_NAME);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getStackCapacity() {
        return stackCapacity;
    }

    public void setStackCapacity(int stackCapacity) {
        this.stackCapacity = stackCapacity;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", itemId=" + itemId +
                ", stackCapacity=" + stackCapacity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return itemId == item.itemId &&
                name.equals(item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, itemId);
    }

    public String toStringForAlert() {
        return "Name: " + this.getName()
                + "\nID: " + this.getItemId()
                + "\nStack Capacity: " + this.getStackCapacity();
    }

}
