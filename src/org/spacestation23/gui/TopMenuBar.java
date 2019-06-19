package org.spacestation23.gui;

import javafx.scene.control.*;
import org.spacestation23.gui.itemManager.ItemManagerStage;

public class TopMenuBar extends MenuBar {

    public TopMenuBar() {
        super();
        Menu menu1 = new Menu("File");
        Menu menu2 = new Menu("Utilities");
        MenuItem createItem = new MenuItem("Item Manager");
        createItem.setOnAction(e -> {
            ItemManagerStage itemManager = new ItemManagerStage();
            itemManager.show();
        });
        menu2.getItems().add(createItem);
        this.getMenus().addAll(menu1, menu2);
    }

}
