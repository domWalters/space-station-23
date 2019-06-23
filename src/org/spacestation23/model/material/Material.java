package org.spacestation23.model.material;

import javafx.scene.image.Image;

import java.util.Objects;

public class Material {

    public static final String INVALID_NAME = "";
    public static final String INVALID_STRING_SPRITE = "";
    public static final int INVLAID_INVENTORY_CAPACITY = -1;
    public static final String INVALID_IMAGE_SPRITE = "";

    private String name;
    private String characterSprite;
    private boolean passable;
    private Integer inventoryCapacity;
    private Image imgSprite;

    public Material(String name, String characterSprite, boolean passable, Integer inventoryCapacity, Image imgSprite) {
        this.name = name;
        this.characterSprite = characterSprite;
        this.passable = passable;
        this.inventoryCapacity = inventoryCapacity;
        this.imgSprite = imgSprite;
    }

    public Material(String materialName, String materialStringSprite, String materialPassable, String materialInventoryCapacity, String materialImageSprite) {
        if (!materialName.equals(INVALID_NAME)) {
            this.setName(materialName);
            if (!materialStringSprite.equals(INVALID_STRING_SPRITE)) {
                this.setCharacterSprite(materialStringSprite);
                this.setPassable(materialPassable.equals("true"));
                try {
                    int materialInventoryCapacityNumber = Integer.parseInt(materialInventoryCapacity);
                    this.setInventoryCapacity(materialInventoryCapacityNumber);
                    this.setImgSprite(new Image(materialImageSprite));
                } catch (NumberFormatException f) {
                    this.setInventoryCapacity(INVLAID_INVENTORY_CAPACITY);
                }
            } else {
                this.setCharacterSprite(INVALID_STRING_SPRITE);
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

    public String getCharacterSprite() {
        return characterSprite;
    }

    public void setCharacterSprite(String characterSprite) {
        this.characterSprite = characterSprite;
    }

    public boolean isPassable() {
        return passable;
    }

    public void setPassable(boolean passable) {
        this.passable = passable;
    }

    public Integer getInventoryCapacity() {
        return inventoryCapacity;
    }

    public void setInventoryCapacity(Integer inventoryCapacity) {
        this.inventoryCapacity = inventoryCapacity;
    }

    public Image getImgSprite() {
        return imgSprite;
    }

    public void setImgSprite(Image imgSprite) {
        this.imgSprite = imgSprite;
    }

    @Override
    public String toString() {
        return "Material{" +
                "name='" + name + '\'' +
                ", characterSprite='" + characterSprite + '\'' +
                ", passable=" + passable +
                ", inventoryCapacity=" + inventoryCapacity +
                ", imgSprite=" + imgSprite +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Material)) return false;
        Material material = (Material) o;
        return passable == material.passable &&
                name.equals(material.name) &&
                characterSprite.equals(material.characterSprite) &&
                inventoryCapacity.equals(material.inventoryCapacity) &&
                imgSprite.equals(material.imgSprite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, characterSprite, passable, inventoryCapacity, imgSprite);
    }

    public String toStringForAlert() {
        return "Name: " + this.getName()
                + "\nString Sprite: " + this.getCharacterSprite()
                + "\nImage Sprite: " + this.getImgSprite().getUrl();
    }

}
