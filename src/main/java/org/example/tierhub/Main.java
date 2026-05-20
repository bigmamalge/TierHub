package org.example.tierhub;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        Tier s = new Tier();
        Item Mario = new Item("Mario");
        Item Link = new Item("Link", "https://www.google.com/url?sa=t&source=web&rct=j&url=https%3A%2F%2Fzelda.fandom.com%2Ffr%2Fwiki%2FLink&ved=0CBYQjRxqFwoTCLC3rsXvx5QDFQAAAAAdAAAAABAh&opi=89978449");
        s.tier.put("S", Arrays.asList(Mario, Link));

        System.out.println(s.toString());
    }
}
