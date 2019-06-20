package org.spacestation23;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.spacestation23.character.Pawn;
import org.spacestation23.itemManager.ItemManagerStage;
import org.spacestation23.item.ItemCreator;
import org.spacestation23.map.Grid;
import org.spacestation23.map.GridLoader;
import org.spacestation23.map.Map;
import org.spacestation23.map.Tile;
import org.spacestation23.map.exceptions.FailedMovementException;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private MenuBar menuBar;

    @FXML
    private TextArea logger;

    @FXML
    private MenuItem itemManager;

    @FXML
    private Menu utilitiesMenu;

    @FXML
    private Menu fileMenu;

    private Grid grid;
    private Pawn pawn1;
    private Pawn pawn2;
    private LoggerStore loggerStore;

    private Map map;

    public MainController() {
        grid = GridLoader.loadFromFile("mapFiles/exampleGrid1.txt");
        pawn1 = new Pawn("Dom", grid.get(3 - 1).get(3 - 1), new Tile("D", grid.get(3 - 1).get(3 - 1)));
        pawn2 = new Pawn("David", grid.get(6 - 1).get(6 - 1), new Tile("d", grid.get(6 - 1).get(6 - 1)));
        loggerStore = new LoggerStore(logger);
        ItemCreator.populateItemsFromFile("mapFiles/items.xml");
        map = new Map(grid);
    }

    @FXML
    void handleOpenItemManager(ActionEvent event) {
        ItemManagerStage itemManager = new ItemManagerStage();
        itemManager.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        borderPane.setCenter(map);
        borderPane.setOnKeyPressed(event -> {
            Node focusOwner = borderPane.getScene().getFocusOwner();
            if (focusOwner instanceof Tile) {
                Tile tile = (Tile) focusOwner;
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
                        loggerStore.scroll("FAILED MOVEMENT FROM " + e.getPawn().getLocation() + " IN DIRECTION " + e.getDirection() + ".");
                    }
                }
            }
        });
        borderPane.requestFocus();
    }

}
