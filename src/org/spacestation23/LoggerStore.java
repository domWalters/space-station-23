package org.spacestation23;

import javafx.scene.control.TextArea;

public class LoggerStore {

    private String lineOne;
    private String lineTwo;
    private String lineThree;

    private TextArea logger;

    public LoggerStore(TextArea logger) {
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

    public TextArea getLogger() {
        return logger;
    }

    public void setLogger(TextArea logger) {
        this.logger = logger;
    }

    public void scroll(String newLog) {
        this.setLineThree(this.getLineTwo());
        this.setLineTwo(this.getLineOne());
        this.setLineOne(newLog);
        this.getLogger().setText(this.getLineOne() + "\n" + this.getLineTwo() + "\n" + this.getLineThree());
    }

}
