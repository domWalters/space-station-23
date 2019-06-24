package org.spacestation23.model.grid;

import org.spacestation23.model.material.Material;
import org.spacestation23.model.material.MaterialCreator;
import org.spacestation23.model.material.Visualisation;

import java.io.*;

public class GridCreator {
    /*
        Upper left corner is (0,0).
        Right increases x.
        Down increases y.
        Required properties: File is rectangular (every row is the same length, no row is blank)
    */
    public static Grid loadFromFile(String fileName) {
        return loadFromFile(new File(fileName));
    }

    public static Grid loadFromFile(File file) {
        MaterialCreator.populateMaterialsFromFile("mapFiles/materials.xml");
        Grid grid = new Grid();
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            Integer y = -1;
            while(br.ready()) {
                GridRow row = new GridRow();
                String[] textRow = br.readLine().split("\t");
                Integer x = -1;
                y++;
                for (String textNode : textRow) {
                    x++;
                    // 3 components: character, version, rotation (char NUMBER [URLD])
                    String[] textSplit = textNode.split(" ");
                    String textCharacterRep = textSplit[0];
                    String textVersion = textSplit[1];
                    String textRotation = textSplit[2];
                    int rotation = 0;
                    switch(textRotation) {
                        case "U":
                            rotation = 0;
                            break;
                        case "R":
                            rotation = 90;
                            break;
                        case "D":
                            rotation = 180;
                            break;
                        case "L":
                            rotation = 270;
                            break;
                    }
                    GridNode gridNode = null;
                    for (Material material : MaterialCreator.materials.values()) {
                        for (Visualisation visualisation : material.getVisualisations()) {
                            if (textCharacterRep.equals(visualisation.getCharacterSprite())) {
                                gridNode = new GridNode(x, y, grid, material, rotation, Integer.parseInt(textVersion));
                                break;
                            }
                        }
                    }
                    row.add(gridNode);
                }
                grid.add(row);
            }
        } catch (IOException e) {
            return null;
        }
        return grid;
    }

    public static void saveToFile(Grid grid, File file) {
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            StringBuilder string = new StringBuilder();
            for (int row = 0; row < grid.ySize(); row++) {
                GridRow gridRow = grid.get(row);
                for (int col = 0; col < grid.xSize(row); col++) {
                    GridNode gridNode = gridRow.get(col);
                    int visIndex = gridNode.getVisualisationIndex();
                    string.append(gridNode.getMaterial().getVisualisations().get(visIndex).getCharacterSprite());
                    string.append(" ");
                    string.append(visIndex);
                    string.append(" ");
                    String rotation = "U";
                    switch (gridNode.getRotation()) {
                        case 0:
                            rotation = "U";
                            break;
                        case 90:
                            rotation = "R";
                            break;
                        case 180:
                            rotation = "D";
                            break;
                        case 270:
                            rotation = "L";
                            break;
                    }
                    string.append(rotation);
                    if (col != grid.xSize(row) - 1) {
                        string.append("\t");
                    }
                }
                string.append("\n");
            }
            bw.write(string.toString());
            bw.close();
        } catch (IOException e) {

        }
    }

}
