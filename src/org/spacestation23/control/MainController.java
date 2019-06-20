package org.spacestation23.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.spacestation23.model.LoggerStore;
import org.spacestation23.model.character.Pawn;
import org.spacestation23.view.itemManager.ItemManagerApplication;
import org.spacestation23.model.item.ItemCreator;
import org.spacestation23.model.grid.Grid;
import org.spacestation23.model.grid.GridLoader;
import org.spacestation23.view.main.map.Map;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, PropertyChangeListener {

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
        pawn1 = new Pawn("Dom", grid.get(3 - 1).get(3 - 1), "D");
        pawn2 = new Pawn("David", grid.get(6 - 1).get(6 - 1), "d");
        loggerStore = new LoggerStore(3);
        loggerStore.addPropertyChangeListener(this);
        ItemCreator.populateItemsFromFile("mapFiles/items.xml");
        map = new Map(grid, loggerStore);
    }

    @FXML
    void handleOpenItemManager(ActionEvent event) {
        ItemManagerApplication itemManager = new ItemManagerApplication();
        itemManager.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        borderPane.setCenter(map);
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
        }
    }
}
