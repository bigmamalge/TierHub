package org.example.model;

import java.util.List;

public class TierList {
    List<Tier> tiers;
    public TierList() {
    }
    public TierList(List<Tier> tiers) {
        this.tiers = tiers;
    }

    public List<Tier> getTiers() {
        return tiers;
    }

    public void setTiers(List<Tier> tiers) {
        this.tiers = tiers;
    }
}
