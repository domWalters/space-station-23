package org.spacestation23.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import org.spacestation23.model.LoggerStore;
import org.spacestation23.model.character.Pawn;
import org.spacestation23.model.grid.Grid;
import org.spacestation23.model.grid.GridLoader;
import org.spacestation23.model.grid.GridNode;
import org.spacestation23.model.grid.GridRow;
import org.spacestation23.model.grid.exceptions.FailedMovementException;
import org.spacestation23.model.item.ItemCreator;
import org.spacestation23.view.itemManager.ItemManagerApplication;
import org.spacestation23.view.main.MapCell;
import org.spacestation23.view.main.SpriteFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, PropertyChangeListener {

    @FXML
    private TextArea logger;

    @FXML
    private GridPane mapGridPane;

    private Grid grid;
    private Pawn pawn1;
    private Pawn pawn2;
    private LoggerStore loggerStore;

    public MainController() {
        grid = GridLoader.loadFromFile("mapFiles/exampleGrid1.txt");
        pawn1 = new Pawn("Dom", grid.get(3 - 1).get(3 - 1), SpriteFactory.pawnImg);
        pawn2 = new Pawn("David", grid.get(6 - 1).get(6 - 1), SpriteFactory.pawnImg);
        loggerStore = new LoggerStore(3);
        loggerStore.addPropertyChangeListener(this);
        pawn1.addPropertyChangeListener(this);
        pawn2.addPropertyChangeListener(this);
        ItemCreator.populateItemsFromFile("mapFiles/items.xml");
    }

    @FXML
    void handleOpenItemManager(ActionEvent event) {
        ItemManagerApplication itemManager = new ItemManagerApplication();
        itemManager.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (int y = 0; y < grid.ySize(); y++) {
            GridRow row = grid.get(y);
            for (int x = 0; x < grid.xSize(y); x++) {
                GridNode cell = row.get(x);
                Pawn cellPawn = cell.getPawn();
                MapCell mapCell;
                if (cellPawn != null) {
                    mapCell = new MapCell(cell.getMaterial().imgSprite, cellPawn.getSprite());
                } else {
                    mapCell = new MapCell(cell.getMaterial().imgSprite, null);
                }
                mapCell.setOnMouseClicked(e ->  {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        mapCell.requestFocus();
                    }
                });
                mapCell.setOnKeyPressed(event -> {
                    Pawn clickedPawn = cell.getPawn();
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
                    } else {
                        loggerStore.scroll("NO PAWN IN FOCUS TILE");
                    }
                });
                mapCell.focusedProperty().addListener((ov, oldV, newV) -> {
                    if (newV) {
                        mapCell.focused();
                    } else {
                        mapCell.unfocused();
                    }
                });
                mapGridPane.add(mapCell, x, y);
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("lines") && logger != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String line : (String[]) evt.getNewValue()) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            logger.setText(stringBuilder.toString());
        } else if (evt.getPropertyName().equals("pawnLocation") && mapGridPane != null) {
            GridNode oldLocation = (GridNode) evt.getOldValue();
            Pair newLocationPair = (Pair) evt.getNewValue();
            GridNode newLocation = (GridNode) newLocationPair.getKey();
            Image pawnSprite = (Image) newLocationPair.getValue();
            for (Node node : mapGridPane.getChildren()) {
                MapCell mapCell = (MapCell) node;
                int row = GridPane.getRowIndex(node);
                int col = GridPane.getColumnIndex(node);
                if (oldLocation.getX() == col && oldLocation.getY() == row) {
                    mapCell.disablePawn();
                }
                if (newLocation.getX() == col && newLocation.getY() == row) {
                    mapCell.enablePawn(pawnSprite);
                    mapCell.requestFocus();
                }
            }
        }
    }
}
