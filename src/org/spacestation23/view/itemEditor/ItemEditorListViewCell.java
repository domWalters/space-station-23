package org.spacestation23.view.itemEditor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import org.spacestation23.model.item.Item;

import java.io.IOException;

public class ItemEditorListViewCell extends ListCell<Item> {

    @FXML
    private Label idLabel;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label nameLabel;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(Item item, boolean empty) {
        super.updateItem(item, empty);
        if(empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/fxml/itemEditor/ItemEditorListViewCell.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            idLabel.setText("" + item.getItemId());
            nameLabel.setText(item.getName());

            setText(null);
            setGraphic(gridPane);
        }
    }

}
