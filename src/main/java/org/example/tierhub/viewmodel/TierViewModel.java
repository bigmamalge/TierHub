package org.example.tierhub.viewmodel;

import org.example.tierhub.Tier;
import org.example.tierhub.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TierViewModel {

    private final Tier tier;

    private final ObservableList<ItemViewModel> items = FXCollections.observableArrayList();

    private String tierName;

    public TierViewModel(Tier tier) {
        this.tier = tier;

        // assuming your map has one entry like "S" -> items
        for (Map.Entry<String, List<Item>> entry : tier.getTier().entrySet()) {
            this.tierName = entry.getKey();

            items.setAll(
                    entry.getValue().stream()
                            .map(ItemViewModel::new)
                            .collect(Collectors.toList())
            );
        }
    }

    public String getTierName() {
        return tierName;
    }

    public ObservableList<ItemViewModel> getItems() {
        return items;
    }

    public Tier getTier() {
        // sync back to POJO
        tier.getTier().clear();
        tier.getTier().put(
                tierName,
                items.stream()
                        .map(ItemViewModel::getItem)
                        .collect(Collectors.toList())
        );
        return tier;
    }
}