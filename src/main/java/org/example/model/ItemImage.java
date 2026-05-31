package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;

public class ItemImage extends Item {
    private byte[] imageBytes;

    public ItemImage() {
    }

    public ItemImage(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    @JsonIgnore
    public Image getJavaFXImage() {
        if (imageBytes == null || imageBytes.length == 0) {
            return null;
        }
        return new Image(new ByteArrayInputStream(imageBytes));
    }
}
