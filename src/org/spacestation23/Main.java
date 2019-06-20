package org.spacestation23;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.spacestation23.character.Pawn;
import org.spacestation23.gui.LoggerArea;
import org.spacestation23.gui.Map;
import org.spacestation23.gui.TopMenuBar;
import org.spacestation23.item.ItemCreator;
import org.spacestation23.map.Grid;
import org.spacestation23.map.GridLoader;
import org.spacestation23.map.Tile;
import org.spacestation23.map.exceptions.FailedMovementException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Initialise Stage, Scene, and Scene Root
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Space-Station-23");
        // Create the game
        Grid grid = GridLoader.loadFromFile("mapFiles/exampleGrid1.txt");
        Pawn pawn = new Pawn("Dom", grid.get(3 - 1).get(3 - 1), new Tile("D", grid.get(3 - 1).get(3 - 1)));
        Pawn pawn2 = new Pawn("David", grid.get(6 - 1).get(6 - 1), new Tile("d", grid.get(6 - 1).get(6 - 1)));
        ItemCreator.populateItemsFromFile("mapFiles/items.xml");
        root.setCenter(new Map(grid, scene));
        // Create MenuBar
        TopMenuBar menuBar = new TopMenuBar();
        root.setTop(menuBar);
        // Create Logging Pane
        BorderPane loggingPane = new BorderPane();
        TextField focus = new TextField();
        loggingPane.setTop(focus);
        LoggerArea logger = new LoggerArea();
        loggingPane.setBottom(logger);
        root.setRight(loggingPane);
        // Setting event handlers
        scene.focusOwnerProperty().addListener(e -> {
            System.out.println(primaryStage.getScene().getFocusOwner().getClass());
            if (scene.getFocusOwner() instanceof Tile) {
                focus.setText(((Tile)scene.getFocusOwner()).getLocation().toString());
            }
        });
        root.setOnKeyPressed(event -> {
            if (scene.getFocusOwner() instanceof Tile) {
                Tile tile = (Tile) scene.getFocusOwner();
                Pawn clickedPawn = tile.getLocation().getPawn();
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
                        System.out.println("FAILED MOVEMENT FROM " + e.getPawn().getLocation() + " IN DIRECTION " + e.getDirection() + ".");
                        logger.scroll("FAILED MOVEMENT FROM " + e.getPawn().getLocation() + " IN DIRECTION " + e.getDirection() + ".");
                    }
                }
            }
        });
        root.requestFocus();
        primaryStage.show();
    }
}
