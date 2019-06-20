package org.spacestation23.view.main.map;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Region;
import org.spacestation23.model.LoggerStore;
import org.spacestation23.model.character.Pawn;
import org.spacestation23.model.grid.exceptions.FailedMovementException;
import org.spacestation23.model.grid.GridNode;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;

public class Tile implements Initializable, PropertyChangeListener {

    private String sprite;
    private GridNode location;
    private LoggerStore loggerStore;

    @FXML
    private Group group;

    @FXML
    private Region region;

    @FXML
    private Label label;

    public Tile(String sprite, GridNode location, LoggerStore loggerStore) {
        this.setSprite(sprite);
        this.setLocation(location);
        this.setLoggerStore(loggerStore);
        location.addPropertyChangeListener(this);
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public GridNode getLocation() {
        return location;
    }

    public void setLocation(GridNode location) {
        this.location = location;
    }

    public LoggerStore getLoggerStore() {
        return loggerStore;
    }

    public void setLoggerStore(LoggerStore loggerStore) {
        this.loggerStore = loggerStore;
    }

    public Group getGroup() {
        return group;
    }

    public Label getLabel() {
        return label;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        group.setOnContextMenuRequested(e -> {
            MapContextMenu menu = new MapContextMenu(group.getScene());
            menu.show(group, e.getScreenX(), e.getScreenY());
        });
        group.setOnMouseClicked(e ->  {
            if (e.getButton() == MouseButton.PRIMARY) {
                group.requestFocus();
            }
        });
        group.setOnKeyPressed(event -> {
            Pawn clickedPawn = this.getLocation().getPawn();
            if (clickedPawn != null) {
                try {
                    switch(event.getCode()) {
                        case W:
                            clickedPawn.moveUp(); break;
                        case D:
                            clickedPawn.moveRight(); break;
                        case S:
                            clickedPawn.moveDown(); break;
                        case A:
                            clickedPawn.moveLeft(); break;
                    }
                } catch (FailedMovementException e) {
                    this.getLoggerStore().scroll("FAILED MOVEMENT FROM " + e.getPawn().getLocation() + " IN DIRECTION " + e.getDirection() + ".");
                }
            } else {
                System.out.println("NO PAWN IN FOCUS TILE");
            }
        });
        group.focusedProperty().addListener((ov, oldV, newV) -> {
            if (newV) {
                region.setStyle("-fx-border-color: blue;");
            } else {
                region.setStyle("-fx-border-color: black; ");
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("pawn") && label != null) {
            if (evt.getNewValue() == null) {
                label.setText(this.getLocation().getSprite());
            } else {
                Pawn pawn = (Pawn) evt.getNewValue();
                label.setText(pawn.getSprite());
                group.requestFocus();
            }
        }
    }
}
