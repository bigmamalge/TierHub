package org.example.tierhub;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Tier {
    Map<String, List<Item>> tier = new LinkedHashMap<>();

    @Override
    public String toString() {
        return "Tier{" +
                "tier=" + tier +
                '}';
    }
}