package org.example.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ItemImage.class, name = "image"),
        @JsonSubTypes.Type(value = ItemTexte.class, name = "texte")
})
public abstract class Item {
    public Item(){
    }
}
