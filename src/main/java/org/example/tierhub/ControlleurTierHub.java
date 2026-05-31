package org.example.tierhub;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.example.model.TierList;
import org.example.model.TierListPreview;
import org.example.service.Jackson.Transform;

import java.io.File;
import java.util.*;

public class ControlleurTierHub {
    @FXML
    private FlowPane tierListGroup;

    private Transform transform = new Transform();

    List<TierListPreview> tiersLists;


    @FXML
    private void initialize() {
        tiersLists = new ArrayList<>();
        File dossier = new  File("src/main/resources/org/example/tierhub/save");


        File[] fichiers = dossier.listFiles((dir, nom) -> nom.toLowerCase().endsWith(".json"));
        if  (fichiers != null) {
            for (File file : fichiers) {
                TierList tl = transform.getJson(file.getAbsolutePath());
                Image img = tl.getImg().getJavaFXImage();
                tiersLists.add(new TierListPreview(tl.getName(), img));
            }
        }

        afficher();
    }

    private void afficher(){
        for  (TierListPreview tl : tiersLists){
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            vbox.setPrefSize(100,130);
            ImageView iv = new ImageView(tl.getImg());
            iv.setFitWidth(70);
            iv.setFitHeight(70);
            vbox.getChildren().add(iv);
            Label lb = new Label(tl.getName());
            vbox.getChildren().add(lb);

            tierListGroup.getChildren().add(vbox);
        }
    }





}
