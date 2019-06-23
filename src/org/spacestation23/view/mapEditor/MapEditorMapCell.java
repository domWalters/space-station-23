package org.spacestation23.view.mapEditor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.spacestation23.model.character.Pawn;
import org.spacestation23.model.grid.GridNode;
import org.spacestation23.model.material.Material;

import java.io.IOException;

public class MapEditorMapCell extends StackPane {

    @FXML
    private ImageView tileView;

    @FXML
    private ImageView pawnView;

    @FXML
    private Pane focus;

    public MapEditorMapCell() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/mapEditor/MapEditorMapCell.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.setFocusListener();
    }

    public MapEditorMapCell(GridNode cell) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/mapEditor/MapEditorMapCell.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        // Set Images
        tileView.setImage(cell.getMaterial().getImgSprite());
        Pawn pawn = cell.getPawn();
        if (pawn != null) {
            pawnView.setImage(pawn.getSprite());
        } else {
            this.disablePawn();
        }
        // Set Event Handlers
        this.setFocusListener();
    }

    @FXML
    void handleMouseClick(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) {
            this.requestFocus();
        }
    }

    public void enablePawn(Image image) {
        this.pawnView.setImage(image);
        this.getChildren().add(1, pawnView);
    }

    public void disablePawn() {
        this.getChildren().remove(pawnView);
    }

    public void setMaterial(Material material) {
        this.tileView.setImage(material.getImgSprite());
    }

    private void setFocusListener() {
        this.focusedProperty().addListener((ov, oldV, newV) -> {
            if (newV) {
                focus.getStyleClass().remove("rect-border-unselected");
                focus.getStyleClass().add("rect-border-selected");
            } else {
                focus.getStyleClass().remove("rect-border-selected");
                focus.getStyleClass().add("rect-border-unselected");
            }
        });
    }

}



