package org.example.tierhub.viewmodel;

import org.example.tierhub.Item;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ItemViewModel {

    private final Item item;
    private final StringProperty name;
    private final StringProperty imageUrl;

    public ItemViewModel(Item item) {
        this.item = item;
        this.name = new SimpleStringProperty(item.getNom());
        this.imageUrl = new SimpleStringProperty(item.getLienImg());

        // keep POJO in sync with UI
        this.name.addListener((obs, oldVal, newVal) -> item.setNom(newVal));
        this.imageUrl.addListener((obs, oldVal, newVal) -> item.setLienImg(newVal));
    }

    public Item getItem() {
        return item;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty imageUrlProperty() {
        return imageUrl;
    }
}