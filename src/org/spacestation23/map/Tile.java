package org.spacestation23.map;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Region;

public class Tile extends Group {

    private String sprite;
    private GridNode location;

    private Region region;
    private Label label;

    public Tile(String sprite, GridNode location) {
        super();
        this.setOnMouseClicked(e ->  {
            if (e.getButton() == MouseButton.PRIMARY) {
                this.requestFocus();
            }
        });

        this.setSprite(sprite);
        this.setLocation(location);

        Region rect = new Region();
        rect.setStyle("-fx-border-style: solid; -fx-border-width: 1; -fx-border-color: black; -fx-min-width: 50; -fx-min-height:50; -fx-max-width:50; -fx-max-height: 50;");
        this.setRegion(rect);
        Label label = new Label(sprite);
        label.setAlignment(Pos.CENTER);
        label.setTranslateX(20);
        label.setTranslateY(16);
        this.setLabel(label);
        this.getChildren().addAll(region, label);
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    private Region getRegion() {
        return region;
    }

    private void setRegion(Region region) {
        this.region = region;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public GridNode getLocation() {
        return location;
    }

    public void setLocation(GridNode location) {
        this.location = location;
    }

}
