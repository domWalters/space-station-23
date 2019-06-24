package org.spacestation23.model.material;

import javafx.scene.image.Image;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Objects;

public class Visualisation implements Serializable {

    public static final String INVALID_STRING_SPRITE = "";
    public static final String INVALID_IMAGE_SPRITE = "";

    private String characterSprite;
    private Image imageSprite;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public Visualisation(String visualisationCharacterSprite, String visualisationImageSprite) {
        if (!visualisationCharacterSprite.equals(INVALID_STRING_SPRITE)) {
            this.setCharacterSprite(visualisationCharacterSprite);
            if (!visualisationImageSprite.equals(INVALID_IMAGE_SPRITE)) {
                this.setImageSprite(new Image(visualisationImageSprite));
            } else {
                this.setImageSprite(null);
            }
        } else {
            this.setCharacterSprite(INVALID_STRING_SPRITE);
        }
    }

    public Visualisation(String characterSprite, Image imageSprite) {
        this.characterSprite = characterSprite;
        this.imageSprite = imageSprite;
    }

    public String getCharacterSprite() {
        return characterSprite;
    }

    public void setCharacterSprite(String characterSprite) {
        String oldCharacterSprite = this.characterSprite;
        this.characterSprite = characterSprite;
        this.pcs.firePropertyChange("characterSprite", oldCharacterSprite, this.characterSprite);
    }

    public Image getImageSprite() {
        return imageSprite;
    }

    public void setImageSprite(Image imageSprite) {
        Image oldImageSprite = this.imageSprite;
        this.imageSprite = imageSprite;
        this.pcs.firePropertyChange("imageSprite", oldImageSprite, this.imageSprite);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    @Override
    public String toString() {
        return "Visualisation{" +
                "characterSprite='" + characterSprite + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Visualisation)) return false;
        Visualisation that = (Visualisation) o;
        return characterSprite.equals(that.characterSprite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(characterSprite);
    }
}
