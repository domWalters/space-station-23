package org.spacestation23.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;

public class LoggerStore {

    private String[] lines;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public LoggerStore(int numberOfLines) {
        String[] lines = new String[numberOfLines];
        Arrays.fill(lines, "");
        this.lines = lines;
    }

    public String[] getLines() {
        return lines;
    }

    public void setLines(String[] lines) {
        this.lines = lines;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    public void scroll(String newLog) {
        String[] oldLines = this.getLines();
        String[] newLines = new String[oldLines.length];
        if (oldLines.length - 1 >= 0) System.arraycopy(oldLines, 0, newLines, 1, oldLines.length - 1);
        newLines[0] = newLog;
        this.setLines(newLines);
        this.pcs.firePropertyChange("lines", oldLines, newLines);
    }

}
