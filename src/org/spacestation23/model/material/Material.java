package org.spacestation23.model.material;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Material {

    public static final String INVALID_NAME = "";
    public static final Boolean INVALID_PASSABILITY = null;
    public static final int INVALID_INVENTORY_CAPACITY = -1;

    private String name;
    private Boolean passable;
    private Integer inventoryCapacity;
    private List<Visualisation> visualisations;

    public Material(String name, boolean passable, Integer inventoryCapacity, List<Visualisation> visualisations) {
        this.name = name;
        this.passable = passable;
        this.inventoryCapacity = inventoryCapacity;
        this.visualisations = visualisations;
    }

    public Material(String materialName, String materialPassable, String materialInventoryCapacity, List<Pair<String, String>> materialVisualisationsList) {
        if (!materialName.equals(INVALID_NAME)) {
            this.setName(materialName);
            if (materialPassable.equals("true") || materialPassable.equals("false")) {
                this.setPassable(materialPassable.equals("true"));
                try {
                    int materialInventoryCapacityNumber = Integer.parseInt(materialInventoryCapacity);
                    this.setInventoryCapacity(materialInventoryCapacityNumber);
                    List<Visualisation> materialVisualisationsObjectsList = new ArrayList<>();
                    for (Pair<String, String> visualisation : materialVisualisationsList) {
                        materialVisualisationsObjectsList.add(new Visualisation(visualisation.getKey(), visualisation.getValue()));
                    }
                    this.setVisualisations(materialVisualisationsObjectsList);
                } catch (NumberFormatException f) {
                    this.setInventoryCapacity(INVALID_INVENTORY_CAPACITY);
                }
            } else {
                this.setPassable(INVALID_PASSABILITY);
            }
        } else {
            this.setName(INVALID_NAME);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isPassable() {
        return passable;
    }

    public void setPassable(Boolean passable) {
        this.passable = passable;
    }

    public Integer getInventoryCapacity() {
        return inventoryCapacity;
    }

    public void setInventoryCapacity(Integer inventoryCapacity) {
        this.inventoryCapacity = inventoryCapacity;
    }

    public List<Visualisation> getVisualisations() {
        return visualisations;
    }

    public void setVisualisations(List<Visualisation> visualisations) {
        this.visualisations = visualisations;
    }

    @Override
    public String toString() {
        return "Material{" +
                "name='" + name + '\'' +
                ", passable=" + passable +
                ", inventoryCapacity=" + inventoryCapacity +
                ", visualisations=" + visualisations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Material)) return false;
        Material material = (Material) o;
        return passable == material.passable &&
                name.equals(material.name) &&
                Objects.equals(inventoryCapacity, material.inventoryCapacity) &&
                visualisations.equals(material.visualisations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, passable, inventoryCapacity, visualisations);
    }

    public String toStringForAlert() {
        return "Name: " + this.getName();
    }

}
