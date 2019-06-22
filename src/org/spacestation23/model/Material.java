package org.spacestation23.model;

import javafx.scene.image.Image;

public class Material {

    public final static Material VACUUM = new Material(".", true, 50, new Image("/resources/tileSprites/vacuum.png"));
    public final static Material STEELBULKHEAD = new Material("X", false, null, new Image("/resources/tileSprites/steel-wall.png"));
    public final static Material STEELBULKHEADDOOR = new Material("+", true, 25, new Image("/resources/tileSprites/steel-door.png"));
    public final static Material STEELBULKHEADFLOOR = new Material("-", true, 50, new Image("/resources/tileSprites/steel-floor.png"));

    public String characterSprite;
    public boolean passable;
    public Integer inventoryCapacity;
    public Image imgSprite;

    public Material(String characterSprite, boolean passable, Integer inventoryCapacity, Image imgSprite) {
        this.characterSprite = characterSprite;
        this.passable = passable;
        this.inventoryCapacity = inventoryCapacity;
        this.imgSprite = imgSprite;
    }

}
