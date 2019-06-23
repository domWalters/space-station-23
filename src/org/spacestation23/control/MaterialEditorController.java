package org.spacestation23.control;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class MaterialEditorController implements Initializable {

    @FXML
    private ImageView materialListView;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField stringSpriteTextField;

    @FXML
    private ChoiceBox<?> passableChoiceBox;

    @FXML
    private TextField inventoryCapacityTextField;

    @FXML
    private TextField imageSpriteTextField;

    @FXML
    private ImageView imageView;

    @FXML
    void handleOpenMaterialsXML() {

    }

    @FXML
    void handleSaveMaterialsXML() {

    }

    @FXML
    void handleDeleteMaterial() {

    }

    public MaterialEditorController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
