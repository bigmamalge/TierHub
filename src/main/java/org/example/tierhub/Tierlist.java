package org.example.tierhub;

import java.util.List;

public class Tierlist {
    private static final String DEFAULT_NAME = "GENERIC_TIERLIST";
    private List<Tier> tiers;

    public String getName() {
        return Name;
    }

    public Tierlist() {
    }

    public Tierlist(List<Tier> tiers, String Name) {
        this.tiers = tiers;
        this.Name = Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    private String Name;

    public Tierlist(List<Tier> tiers) {
        this(tiers, DEFAULT_NAME);
    }

    @Override
    public String toString() {
        return "Tierlist{" +
                "tiers=" + tiers +
                '}';
    }

    public List<Tier> getTiers() {
        return tiers;
    }

    public void setTiers(List<Tier> tiers) {
        this.tiers = tiers;
    }
}
