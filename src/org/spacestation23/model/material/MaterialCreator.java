package org.spacestation23.model.material;

import javafx.scene.image.Image;
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
import java.util.HashMap;

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
                    String materialCharacterSprite = ithMaterialElement.getElementsByTagName("characterSprite").item(0).getTextContent();
                    String materialPassable = ithMaterialElement.getElementsByTagName("passable").item(0).getTextContent();
                    String materialInventoryCapacity = ithMaterialElement.getElementsByTagName("inventoryCapacity").item(0).getTextContent();
                    String materialImageSprite = ithMaterialElement.getElementsByTagName("imageSprite").item(0).getTextContent();
                    materials.put(materialName, new Material(materialName, materialCharacterSprite, materialPassable.equals("true"), Integer.parseInt(materialInventoryCapacity), new Image(materialImageSprite)));
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
                materialElement.setAttribute("name", material.name);
                // characterSprite
                Element characterSpriteElement = doc.createElement("characterSprite");
                characterSpriteElement.appendChild(doc.createTextNode(material.characterSprite));
                materialElement.appendChild(characterSpriteElement);
                // passable
                Element passableElement = doc.createElement("passable");
                passableElement.appendChild(doc.createTextNode("" + material.passable));
                materialElement.appendChild(passableElement);
                // inventoryCapacity
                Element inventoryCapacityElement = doc.createElement("inventoryCapacity");
                inventoryCapacityElement.appendChild(doc.createTextNode("" + material.inventoryCapacity));
                materialElement.appendChild(inventoryCapacityElement);
                // imageSprite
                Element imageSpriteElement = doc.createElement("imageSprite");
                imageSpriteElement.appendChild(doc.createTextNode(material.imgSprite.getUrl()));
                materialElement.appendChild(imageSpriteElement);
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

}
