package org.spacestation23.view.mapEditor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import org.spacestation23.model.material.Material;

import java.io.IOException;

public class MapEditorMaterialCell extends ListCell<Material> {

    @FXML
    private HBox hBox;

    @FXML
    private MapCell itemGraphic;

    @FXML
    private Label itemLabel;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(Material material, boolean empty) {
        super.updateItem(material, empty);
        if(empty || material == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/fxml/mapEditor/MapEditorMaterialCell.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            itemGraphic.setMaterial(material);
            itemLabel.setText(material.name);

            setText(null);
            setGraphic(hBox);
        }
    }

}
