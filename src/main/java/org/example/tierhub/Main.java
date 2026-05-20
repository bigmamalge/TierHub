package org.example.tierhub;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    }
}
