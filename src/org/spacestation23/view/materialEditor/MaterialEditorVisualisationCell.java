package org.spacestation23.view.materialEditor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.spacestation23.model.material.Visualisation;

import java.io.File;
import java.io.IOException;

public class MaterialEditorVisualisationCell extends HBox {

    @FXML
    private TextField characterSpriteTextField;

    @FXML
    private Button imageSpriteButton;

    @FXML
    private Button deleteButton;

    public MaterialEditorVisualisationCell() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/materialEditor/MaterialEditorVisualisationCell.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        imageSpriteButton.setText("...");
    }

    public MaterialEditorVisualisationCell(Visualisation visualisation) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/materialEditor/MaterialEditorVisualisationCell.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        characterSpriteTextField.setText(visualisation.getCharacterSprite());
        String path = visualisation.getImageSprite().getUrl();
        imageSpriteButton.setUserData(path);
        String[] pathArray = path.split("/");
        imageSpriteButton.setText(pathArray[pathArray.length - 1]); //TODO: compartmentalise, I use this everywhere
    }

    @FXML
    void handleImageSpriteButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            String path = file.toURI().toString();
            imageSpriteButton.setUserData(path);
            String[] pathArray = path.split("/");
            imageSpriteButton.setText(pathArray[pathArray.length - 1]);
        }
    }

    public String getCharacterSprite() {
        return this.characterSpriteTextField.getText();
    }

    public String getImageSpriteString() {
        return (String) this.imageSpriteButton.getUserData();
    }

    public Button getDeleteButton() {
        return this.deleteButton;
    }

}
