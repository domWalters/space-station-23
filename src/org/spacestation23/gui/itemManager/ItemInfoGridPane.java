package org.spacestation23.gui.itemManager;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.spacestation23.item.Item;

public class ItemInfoGridPane extends GridPane {

    private Label nameLabel;
    private Label idLabel;
    private Label stackCapacityLabel;

    private TextField nameTextField;
    private TextField idTextField;
    private TextField stackCapacityTextField;

    private ListView<Item> itemListView;

    public ItemInfoGridPane() {
        super();

        this.setNameLabel(new Label("Name: "));
        this.setIdLabel(new Label("ID (can omit): "));
        this.setStackCapacityLabel(new Label("Stack Capacity: "));

        this.setNameTextField(new TextField());
        this.setIdTextField(new TextField());
        this.setStackCapacityTextField(new TextField());

        this.add(this.getNameLabel(), 1, 1);
        this.add(this.getNameTextField(), 2, 1);
        this.add(this.getIdLabel(), 1, 2);
        this.add(this.getIdTextField(), 2, 2);
        this.add(this.getStackCapacityLabel(), 1, 3);
        this.add(this.getStackCapacityTextField(), 2, 3);

        this.setItemListView(null);
    }

    public Label getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(Label nameLabel) {
        this.nameLabel = nameLabel;
    }

    public Label getIdLabel() {
        return idLabel;
    }

    public void setIdLabel(Label idLabel) {
        this.idLabel = idLabel;
    }

    public Label getStackCapacityLabel() {
        return stackCapacityLabel;
    }

    public void setStackCapacityLabel(Label stackCapacityLabel) {
        this.stackCapacityLabel = stackCapacityLabel;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public void setNameTextField(TextField nameTextField) {
        this.nameTextField = nameTextField;
    }

    public TextField getIdTextField() {
        return idTextField;
    }

    public void setIdTextField(TextField idTextField) {
        this.idTextField = idTextField;
    }

    public TextField getStackCapacityTextField() {
        return stackCapacityTextField;
    }

    public void setStackCapacityTextField(TextField stackCapacityTextField) {
        this.stackCapacityTextField = stackCapacityTextField;
    }

    public ListView<Item> getItemListView() {
        return itemListView;
    }

    public void setItemListView(ListView<Item> itemListView) {
        this.itemListView = itemListView;
    }
}
