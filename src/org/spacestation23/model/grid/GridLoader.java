package org.spacestation23.model.grid;

import org.spacestation23.model.Material;

import java.io.*;

public class GridLoader {
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
        Grid grid = new Grid();
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            Integer y = -1;
            while(br.ready()) {
                GridRow row = new GridRow();
                String[] textRow = br.readLine().split("");
                Integer x = -1;
                y++;
                for (String textNode : textRow) {
                    x++;
                    GridNode gridNode;
                    if (textNode.equals(Material.VACUUM.characterSprite)) {
                        gridNode = new GridNode(x, y, grid, Material.VACUUM);
                    } else if (textNode.equals(Material.STEELBULKHEAD.characterSprite)) {
                        gridNode = new GridNode(x, y, grid, Material.STEELBULKHEAD);
                    } else if (textNode.equals(Material.STEELBULKHEADDOOR.characterSprite)) {
                        gridNode = new GridNode(x, y, grid, Material.STEELBULKHEADDOOR);
                    } else if (textNode.equals(Material.STEELBULKHEADFLOOR.characterSprite)) {
                        gridNode = new GridNode(x, y, grid, Material.STEELBULKHEADFLOOR);
                    } else {
                        gridNode = null;
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
                    string.append(gridNode.getMaterial().characterSprite);
                }
                string.append("\n");
            }
            bw.write(string.toString());
            bw.close();
        } catch (IOException e) {

        }
    }

}
