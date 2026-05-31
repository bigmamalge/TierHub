package org.example.model;

import javafx.scene.paint.Color;

import java.util.List;

public class Tier {
    private List<Item> items;
    private String Name;
    private String Color;

    public Tier() {
    }

    public Tier(List<Item> items, String name, String color) {
        this.items = items;
        Name = name;
        Color = color;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }
}
