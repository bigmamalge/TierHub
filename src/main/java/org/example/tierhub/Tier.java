package org.example.tierhub;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Tier {
    private Map<String, List<Item>> tier = new LinkedHashMap<>();

    public Map<String, List<Item>> getTier() {
        return tier;
    }

    public void setTier(Map<String, List<Item>> tier) {
        this.tier = tier;
    }

    @Override
    public String toString() {
        return "Tier{" +
                "" + tier +
                '}';
    }
}