package org.spacestation23.view.mapEditor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.spacestation23.model.character.Pawn;

import java.io.IOException;

public class MapCell extends StackPane {

    @FXML
    private ImageView tileView;

    @FXML
    private ImageView pawnView;

    @FXML
    private Pane focus;

    public MapCell(Image tileImg, Pawn pawn) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/mapEditor/MapCell.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        // Set Images
        tileView.setImage(tileImg);
        if (pawn != null) {
            pawnView.setImage(pawn.getSprite());
        } else {
            this.disablePawn();
        }
        // Set Event Handlers
        this.setOnMouseClicked(e ->  {
            if (e.getButton() == MouseButton.PRIMARY) {
                this.requestFocus();
            }
        });
        this.focusedProperty().addListener((ov, oldV, newV) -> {
            if (newV) {
                this.focused();
            } else {
                this.unfocused();
            }
        });
    }

    public void enablePawn(Image image) {
        this.pawnView.setImage(image);
        this.getChildren().add(1, pawnView);
    }

    public void disablePawn() {
        this.getChildren().remove(pawnView);
    }

    private void focused() {
        focus.getStyleClass().remove("rect-border-unselected");
        focus.getStyleClass().add("rect-border-selected");
    }

    private void unfocused() {
        focus.getStyleClass().remove("rect-border-selected");
        focus.getStyleClass().add("rect-border-unselected");
    }

}



