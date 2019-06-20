package org.spacestation23.model.grid;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GridLoader {

    private final static String VACUUM_CHAR = ".";
    private final static boolean PASS_OF_VACUUM = true;
    private final static Integer INVCAP_OF_VACUUM = 50;

    private final static String STEELBULKHEAD_CHAR = "X";
    private final static boolean PASS_OF_STEELBULKHEAD = false;

    private final static String STEELBULKHEADDOOR_CHAR = "+";
    private final static boolean PASS_OF_STEELBULKHEADDOOR = true;
    private final static Integer INVCAP_OF_STEELBULKHEADDOOR = 25;

    private final static String STEELFLOORBULKHEAD_CHAR = "-";
    private final static boolean PASS_OF_STEELFLOORBULKHEAD = true;
    private final static Integer INVCAP_OF_STEELFLOORBULKHEAD = 50;

    /*
        Upper left corner is (1,1).
        Right increases x.
        Down increases y.
        Required properties: File is rectangular (every row is the same length, no row is blank)
    */
    public static Grid loadFromFile(String fileName) {
        Grid grid = new Grid();
        try {
            FileReader fr = new FileReader(fileName);
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
                    switch (textNode) {
                        case VACUUM_CHAR:
                            gridNode = new GridNode(x, y, grid, new Pair<>(PASS_OF_VACUUM, INVCAP_OF_VACUUM), VACUUM_CHAR);
                            break;
                        case STEELBULKHEAD_CHAR:
                            gridNode = new GridNode(x, y, grid, new Pair<>(PASS_OF_STEELBULKHEAD, null), STEELBULKHEAD_CHAR);
                            break;
                        case STEELBULKHEADDOOR_CHAR:
                            gridNode = new GridNode(x, y, grid, new Pair<>(PASS_OF_STEELBULKHEADDOOR, INVCAP_OF_STEELBULKHEADDOOR), STEELBULKHEADDOOR_CHAR);
                            break;
                        case STEELFLOORBULKHEAD_CHAR:
                            gridNode = new GridNode(x, y, grid, new Pair<>(PASS_OF_STEELFLOORBULKHEAD, INVCAP_OF_STEELFLOORBULKHEAD), STEELFLOORBULKHEAD_CHAR);
                            break;
                        default:
                            gridNode = null;
                            break;
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

}
