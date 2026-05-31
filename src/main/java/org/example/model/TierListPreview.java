package org.example.model;

import javafx.scene.image.Image;

public class TierListPreview {
    private String Name;
    private Image img;

    public TierListPreview(String name, Image img) {
        Name = name;
        this.img = img;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }
}
