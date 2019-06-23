//package org.spacestation23.view.materialEditor;
//
//import javafx.fxml.FXMLLoader;
//import javafx.scene.control.ListCell;
//import org.spacestation23.model.item.Item;
//import org.spacestation23.model.material.Material;
//
//import java.io.IOException;
//
//public class MaterialEditorListCellView extends ListCell<Material> {
//
//
//
//    private FXMLLoader mLLoader;
//
//    @Override
//    protected void updateItem(Material material, boolean empty) {
//        super.updateItem(material, empty);
//        if(empty || material == null) {
//            setText(null);
//            setGraphic(null);
//        } else {
//            if (mLLoader == null) {
//                mLLoader = new FXMLLoader(getClass().getResource("/fxml/materialEditor/MapEditorMaterialCell.fxml"));
//                mLLoader.setController(this);
//                try {
//                    mLLoader.load();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            idLabel.setText("" + material.getItemId());
//            nameLabel.setText(material.getName());
//
//            setText(null);
//            setGraphic(gridPane);
//        }
//    }
//
//}
