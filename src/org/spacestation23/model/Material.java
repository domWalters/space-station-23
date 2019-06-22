package org.spacestation23.model;

import javafx.scene.image.Image;

public class Material {

    public final static Material VACUUM = new Material("VACUUM",".", true, 50, new Image("/resources/tileSprites/vacuum.png"));
    public final static Material STEELBULKHEAD = new Material("STEELBULKHEAD", "X", false, null, new Image("/resources/tileSprites/steel-wall.png"));
    public final static Material STEELBULKHEADDOOR = new Material("STEELBULKHEADDOOR", "+", true, 25, new Image("/resources/tileSprites/steel-door.png"));
    public final static Material STEELBULKHEADFLOOR = new Material("STEELBULKHEADFLOOR", "-", true, 50, new Image("/resources/tileSprites/steel-floor.png"));

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
