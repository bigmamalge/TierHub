package org.example.tierhub;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.*;
public class Main {
    public static void main(String[] args) throws Exception {

        Item mario = new Item("Mario");
        Item link = new Item(
                "Link",
                "https://zelda.fandom.com/fr/wiki/Link"
        );
        Item gaw=new Item("game and watch");
        Item ganondorf=new Item("ganondorf");
        Item bowser=new Item("Bowser");


        Tier s = new Tier();
        s.getTier().put("S", List.of(mario, link));
        Tier a = new Tier();
        a.getTier().put("A", List.of(gaw, ganondorf, bowser));

        Tierlist tierlist = new Tierlist(List.of(s, a), "ssbu characters");

        ObjectMapper mapper = new ObjectMapper();

        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(tierlist.getName()+".json"), tierlist);

        Tierlist ssbu_chars = mapper.readValue(
                new File(tierlist.getName()+".json"),
                Tierlist.class
        );
        System.out.println(ssbu_chars);

        /* EXAMPLE : utilisation des views models
                public class MainController {

                private TierlistViewModel viewModel;

                public void setTierlist(Tierlist tierlist) {
                    this.viewModel = new TierlistViewModel(tierlist);
                    render();
                }

                private void render() {
                    for (TierViewModel tier : viewModel.getTiers()) {

                        System.out.println("Tier: " + tier.getTierName());

                        for (ItemViewModel item : tier.getItems()) {
                            System.out.println(" - " + item.nameProperty().get());
                        }
                    }
                }
            }
         */
    }
}
