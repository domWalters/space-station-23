package org.spacestation23.model;

import javafx.scene.control.TextArea;

import java.util.Arrays;

public class LoggerStore extends TextArea {

    private static final int DEFAULT_LINE_COUNT = 3;

    private String[] lines;

    public LoggerStore() {
        super();
        String[] lines = new String[DEFAULT_LINE_COUNT];
        Arrays.fill(lines, "");
        this.lines = lines;
    }

    public String[] getLines() {
        return lines;
    }

    public void setLines(String[] lines) {
        this.lines = lines;
    }

    public void scroll(String newLog) {
        String[] oldLines = this.getLines();
        String[] newLines = new String[oldLines.length];
        if (oldLines.length - 1 >= 0) System.arraycopy(oldLines, 0, newLines, 1, oldLines.length - 1);
        newLines[0] = newLog;
        this.setLines(newLines);

        StringBuilder stringBuilder = new StringBuilder();
        for (String line : newLines) {
            stringBuilder.append(line);
            stringBuilder.append("\n");
        }
        this.setText(stringBuilder.toString());
    }

}
