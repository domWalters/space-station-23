package org.spacestation23.model.material;

import javafx.scene.image.Image;

public class Material {

    public String name;
    public String characterSprite;
    public boolean passable;
    public Integer inventoryCapacity;
    public Image imgSprite;

    public Material(String name, String characterSprite, boolean passable, Integer inventoryCapacity, Image imgSprite) {
        this.name = name;
        this.characterSprite = characterSprite;
        this.passable = passable;
        this.inventoryCapacity = inventoryCapacity;
        this.imgSprite = imgSprite;
    }

}
