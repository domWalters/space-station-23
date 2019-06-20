package org.spacestation23.map;

import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import org.spacestation23.character.Pawn;
import org.spacestation23.map.GridNode;
import org.spacestation23.map.Tile;
import org.spacestation23.map.exceptions.FailedMovementException;

public class MapContextMenu extends ContextMenu {

    public MapContextMenu(Scene mainScene) {
        super();
        MenuItem moveHereManhattanXY = new MenuItem("Move Here (Manhattan - X then Y)...");
        moveHereManhattanXY.setOnAction(e -> {
            if (this.getOwnerNode() instanceof Tile) {
                Tile source = (Tile) this.getOwnerNode();
                GridNode sourceLocation = source.getLocation();
                if (mainScene.getFocusOwner() instanceof Tile) {
                    Tile focusOwner = (Tile) mainScene.getFocusOwner();
                    GridNode focusLocation = focusOwner.getLocation();
                    Pawn focusPawn = focusLocation.getPawn();
                    if (focusPawn != null) {
                        System.out.println("Move " + focusLocation + " to " + sourceLocation);
                        try {
                            int dx = focusLocation.getX() - sourceLocation.getX();
                            for (int i = 0; i < Math.abs(dx); i++) {
                                if (dx < 0) {
                                    focusPawn.moveRight();
                                } else {
                                    focusPawn.moveLeft();
                                }
                            }
                            int dy = focusLocation.getY() - sourceLocation.getY();
                            for (int j = 0; j < Math.abs(dy); j++) {
                                if (dy < 0) {
                                    focusPawn.moveDown();
                                } else {
                                    focusPawn.moveUp();
                                }
                            }
                        } catch (FailedMovementException f) {
                            System.out.println("FAILED MOVEMENT FROM " + f.getPawn().getLocation() + " IN DIRECTION " + f.getDirection() + ".");
                        }
                    }
                }
            }
        });
        MenuItem moveHereManhattanYX = new MenuItem("Move Here (Manhattan - Y then X)...");
        moveHereManhattanYX.setOnAction(e -> {
            if (this.getOwnerNode() instanceof Tile) {
                Tile source = (Tile) this.getOwnerNode();
                GridNode sourceLocation = source.getLocation();
                if (mainScene.getFocusOwner() instanceof Tile) {
                    Tile focusOwner = (Tile) mainScene.getFocusOwner();
                    GridNode focusLocation = focusOwner.getLocation();
                    Pawn focusPawn = focusLocation.getPawn();
                    if (focusPawn != null) {
                        System.out.println("Move " + focusLocation + " to " + sourceLocation);
                        try {
                            int dy = focusLocation.getY() - sourceLocation.getY();
                            for (int j = 0; j < Math.abs(dy); j++) {
                                if (dy < 0) {
                                    focusPawn.moveDown();
                                } else {
                                    focusPawn.moveUp();
                                }
                            }
                            int dx = focusLocation.getX() - sourceLocation.getX();
                            for (int i = 0; i < Math.abs(dx); i++) {
                                if (dx < 0) {
                                    focusPawn.moveRight();
                                } else {
                                    focusPawn.moveLeft();
                                }
                            }
                        } catch (FailedMovementException f) {
                            System.out.println("FAILED MOVEMENT FROM " + f.getPawn().getLocation() + " IN DIRECTION " + f.getDirection() + ".");
                        }
                    }
                }
            }
        });
        this.getItems().addAll(moveHereManhattanXY, moveHereManhattanYX);
    }

}
