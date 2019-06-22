package org.spacestation23.view.main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MapCell extends StackPane {

    @FXML
    private ImageView tileView;

    @FXML
    private ImageView pawnView;

    @FXML
    private Pane focus;

    public MapCell(Image tileImg, Image pawnImg) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/main/Tile.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        tileView.setImage(tileImg);
        pawnView.setImage(pawnImg);
        if (pawnImg == null) {
            this.disablePawn();
        }
    }

    public void enablePawn(Image image) {
        this.pawnView.setImage(image);
        this.getChildren().add(1, pawnView);
    }

    public void disablePawn() {
        this.getChildren().remove(pawnView);
    }

    public void focused() {
        focus.getStyleClass().remove("rect-border-unselected");
        focus.getStyleClass().add("rect-border-selected");
    }

    public void unfocused() {
        focus.getStyleClass().remove("rect-border-selected");
        focus.getStyleClass().add("rect-border-unselected");
    }

}



