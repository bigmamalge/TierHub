package org.example.model;

public class ItemTexte extends Item {
    private String texte;
    private String color;

    public ItemTexte(){
    }
    public ItemTexte(String color, String texte) {
        this.color = color;
        this.texte = texte;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
