package org.example.tierhub.viewmodel;

import org.example.tierhub.Tierlist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.stream.Collectors;

public class TierlistViewModel {

    private final Tierlist tierlist;

    private final ObservableList<TierViewModel> tiers = FXCollections.observableArrayList();

    public TierlistViewModel(Tierlist tierlist) {
        this.tierlist = tierlist;

        tiers.setAll(
                tierlist.getTiers().stream()
                        .map(TierViewModel::new)
                        .collect(Collectors.toList())
        );
    }

    public ObservableList<TierViewModel> getTiers() {
        return tiers;
    }

    public Tierlist getTierlist() {
        tierlist.setTiers(
                tiers.stream()
                        .map(TierViewModel::getTier)
                        .collect(Collectors.toList())
        );
        return tierlist;
    }
}