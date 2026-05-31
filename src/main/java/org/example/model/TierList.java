package org.example.model;

import java.util.List;

public class TierList {
    private List<Tier> tiers;
    private ItemImage img;
    private String name;

    public TierList() {
    }

    public TierList(List<Tier> tiers, ItemImage img, String name) {
        this.tiers = tiers;
        this.img = img;
        this.name = name;
    }

    public List<Tier> getTiers() {
        return tiers;
    }

    public void setTiers(List<Tier> tiers) {
        this.tiers = tiers;
    }

    public ItemImage getImg() {
        return img;
    }

    public void setImg(ItemImage img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
