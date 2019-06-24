package org.spacestation23.model.material;

import javafx.util.Pair;
import org.spacestation23.view.materialEditor.alert.MaterialCreationFailedAlert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MaterialCreator {

    public static HashMap<String, Material> materials = new HashMap<>();

    public static void populateMaterialsFromFile(String fileName) {
        materials = new HashMap<>();
        try {
            File file = new File(fileName);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();
            //
            NodeList materialListXML = document.getElementsByTagName("material");
            for (int i = 0; i < materialListXML.getLength(); i++) {
                Node ithMaterialNode = materialListXML.item(i);
                if (ithMaterialNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element ithMaterialElement = (Element) ithMaterialNode;
                    String materialName = ithMaterialElement.getAttribute("name");
                    String materialPassable = ithMaterialElement.getElementsByTagName("passable").item(0).getTextContent();
                    String materialInventoryCapacity = ithMaterialElement.getElementsByTagName("inventoryCapacity").item(0).getTextContent();
                    NodeList materialVisualisations = ithMaterialElement.getElementsByTagName("visualisation");
                    List<Pair<String, String>> materialVisualisationsList = new ArrayList<>();
                    for (int j = 0; j < materialVisualisations.getLength(); j++) {
                        Node jthVisualisationNode = materialVisualisations.item(j);
                        if (jthVisualisationNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element jthVisualisationElement = (Element) jthVisualisationNode;
                            String materialCharacterSprite = jthVisualisationElement.getElementsByTagName("characterSprite").item(0).getTextContent();
                            String materialImageSprite = jthVisualisationElement.getElementsByTagName("imageSprite").item(0).getTextContent();
                            materialVisualisationsList.add(new Pair<>(materialCharacterSprite, materialImageSprite));
                        }
                    }
                    materials.put(materialName, new Material(materialName, materialPassable, materialInventoryCapacity, materialVisualisationsList));
                }
            }
        } catch (IOException | ParserConfigurationException | SAXException e) {

        }
    }

    public static void populateFileWithMaterials(String fileName) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            // Root
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("materials");
            doc.appendChild(rootElement);
            // Create items
            for (Material material : MaterialCreator.materials.values()) {
                // top level with name
                Element materialElement = doc.createElement("material");
                rootElement.appendChild(materialElement);
                materialElement.setAttribute("name", material.getName());
                // passable
                Element passableElement = doc.createElement("passable");
                passableElement.appendChild(doc.createTextNode("" + material.isPassable()));
                materialElement.appendChild(passableElement);
                // inventoryCapacity
                Element inventoryCapacityElement = doc.createElement("inventoryCapacity");
                inventoryCapacityElement.appendChild(doc.createTextNode("" + material.getInventoryCapacity()));
                materialElement.appendChild(inventoryCapacityElement);
                // visualisations
                for(Visualisation visualisation : material.getVisualisations()) {
                    Element visualisationsElement = doc.createElement("visualisation");
                    // characterSprite
                    Element characterSpriteElement = doc.createElement("characterSprite");
                    characterSpriteElement.appendChild(doc.createTextNode(visualisation.getCharacterSprite()));
                    visualisationsElement.appendChild(characterSpriteElement);
                    // imageSprite
                    Element imageSpriteElement = doc.createElement("imageSprite");
                    imageSpriteElement.appendChild(doc.createTextNode(visualisation.getImageSprite().getUrl()));
                    visualisationsElement.appendChild(imageSpriteElement);
                    materialElement.appendChild(visualisationsElement);
                }
            }
            // Create file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException e) {

        }
    }

    public static Material createMaterialFromStrings(String materialName, String materialPassable, String materialInventoryCapacity, List<Pair<String, String>> materialVisualisationsList) {
        MaterialCreationFailedAlert alert = new MaterialCreationFailedAlert();
        Material newMaterial = new Material(materialName, materialPassable, materialInventoryCapacity, materialVisualisationsList);
        if (!newMaterial.getName().equals(Material.INVALID_NAME)) {
            if (newMaterial.isPassable() != Material.INVALID_PASSABILITY) {
                if (!newMaterial.getInventoryCapacity().equals(Material.INVALID_INVENTORY_CAPACITY)) {
                    for (Visualisation visualisation : newMaterial.getVisualisations()) {
                        if (!visualisation.getCharacterSprite().equals(Visualisation.INVALID_STRING_SPRITE)) {
                            if (!visualisation.getImageSprite().getUrl().equals(Visualisation.INVALID_STRING_SPRITE)) {
                                return newMaterial;
                            } else {
                                alert.updateContentText("Image Sprite didn't resolve correctly.");
                            }
                        } else {
                            alert.updateContentText("String Sprite was the empty string.");
                        }
                    }
                } else {
                    alert.updateContentText("Inventory Capacity was negative.");
                }
            }
        } else {
            alert.updateContentText("Material Name was the empty string.");
        }
        alert.showAndWait();
        return null;
    }

}
