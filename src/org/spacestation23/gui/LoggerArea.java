package org.spacestation23.gui;

import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class LoggerArea extends TextArea {

    private String lineOne;
    private String lineTwo;
    private String lineThree;

    public LoggerArea() {
        super();
        Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
        this.setBorder(border);
        this.setEditable(false);
        this.setPrefRowCount(3);
        this.setLineOne("");
        this.setLineTwo("");
        this.setLineThree("");
    }

    public String getLineOne() {
        return lineOne;
    }

    public void setLineOne(String lineOne) {
        this.lineOne = lineOne;
    }

    public String getLineTwo() {
        return lineTwo;
    }

    public void setLineTwo(String lineTwo) {
        this.lineTwo = lineTwo;
    }

    public String getLineThree() {
        return lineThree;
    }

    public void setLineThree(String lineThree) {
        this.lineThree = lineThree;
    }

    public void scroll(String newLog) {
        this.setLineThree(this.getLineTwo());
        this.setLineTwo(this.getLineOne());
        this.setLineOne(newLog);
        this.setText(this.getLineOne() + "\n" + this.getLineTwo() + "\n" + this.getLineThree());
    }

}
