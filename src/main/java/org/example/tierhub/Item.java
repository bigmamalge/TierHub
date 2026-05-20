package org.example.tierhub;

public class Item {

    private static final String DEFAULT_LINK = "NaN";

    private String nom;
    private String lienImg;

    public Item(String nom, String lienImg) {
        this.nom = nom;
        this.lienImg = lienImg;
    }

    public Item(String nom) {
        this(nom, DEFAULT_LINK);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLienImg() {
        return lienImg;
    }

    public void setLienImg(String lienImg) {
        this.lienImg = lienImg;
    }

    @Override
    public String toString() {
        return "Item{" +
                "nom='" + nom + '\'' +
                ", lienImg='" + lienImg + '\'' +
                '}';
    }
}