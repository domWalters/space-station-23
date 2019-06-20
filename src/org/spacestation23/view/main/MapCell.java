package org.spacestation23.view.main;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import java.io.IOException;

public class MapCell extends Group {

    @FXML
    private Region region;

    @FXML
    private Label label;

    public MapCell() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/main/Tile.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public StringProperty textProperty() {
        return this.label.textProperty();
    }

    public void setText(String text) {
        this.textProperty().set(text);
    }

    public void setRegionStyle(String s) {
        this.region.setStyle(s);
    }

}



