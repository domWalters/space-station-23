package org.spacestation23.model.item;

import org.spacestation23.view.itemManager.alert.ItemCreationFailedAlert;
import org.w3c.dom.*;
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

public class ItemCreator {

    public static HashMap<Integer, Item> items = new HashMap<>();

    public static void populateItemsFromFile(String fileName) {
        items = new HashMap<>();
        try {
            File file = new File(fileName);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();
            //
            NodeList itemListXML = document.getElementsByTagName("item");
            for (int i = 0; i < itemListXML.getLength(); i++) {
                Node ithItemNode = itemListXML.item(i);
                if (ithItemNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element ithItemElement = (Element) ithItemNode;
                    String itemName = ithItemElement.getElementsByTagName("name").item(0).getTextContent();
                    int itemId = Integer.parseInt(ithItemElement.getAttribute("id"));
                    int itemStackCapacity = Integer.parseInt(ithItemElement.getElementsByTagName("stackCapacity").item(0).getTextContent());
                    items.put(itemId, new Item(itemName, itemId, itemStackCapacity));
                }
            }
        } catch (IOException | ParserConfigurationException | SAXException e) {

        }
    }

    public static void populateFileWithItems(String fileName) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            // Root
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("items");
            doc.appendChild(rootElement);
            // Create items
            for (Item item : ItemCreator.items.values()) {
                // top level with id
                Element itemElement = doc.createElement("item");
                rootElement.appendChild(itemElement);
                itemElement.setAttribute("id", "" + item.getItemId());
                // name
                Element nameElement = doc.createElement("name");
                nameElement.appendChild(doc.createTextNode(item.getName()));
                itemElement.appendChild(nameElement);
                // stack
                Element stackCapacityElement = doc.createElement("stackCapacity");
                stackCapacityElement.appendChild(doc.createTextNode("" + item.getStackCapacity()));
                itemElement.appendChild(stackCapacityElement);
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

    public static Item createItemFromStrings(String itemName, String itemId, String itemStackCapacity) {
        ItemCreationFailedAlert alert = new ItemCreationFailedAlert();
        Item newItem = new Item(itemName, itemId, itemStackCapacity);
        if (!newItem.getName().equals(Item.INVALID_NAME)) {
            if (newItem.getItemId() != Item.INVALID_ITEM_ID) {
                if (newItem.getStackCapacity() != Item.INVALID_STACK_CAPACITY) {
                    return newItem;
                } else {
                    alert.updateContentText("Stack Capacity wasn't an integer.");
                }
            } else {
                alert.updateContentText("ID Number outside of acceptable bounds (0 to 10000).");
            }
        } else {
            alert.updateContentText("Item Name was the empty string.");
        }
        alert.showAndWait();
        return null;
    }

}
