package org.example.tierhub;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.model.TierList;
import org.example.model.TierListPreview;
import org.example.service.Jackson.Transform;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ControlleurTierHub {
    @FXML
    private FlowPane tierListGroup;
    @FXML
    private Label label;
    @FXML
    private ImageView image;
    @FXML
    private HBox newItem;
    @FXML
    private TextField recherche;

    private List<TierListPreview> tiersLists;
    private boolean modelmenu = false;
    private Transform transform = new Transform();


    @FXML
    private void initialize() {

        chargerDossier("src/main/resources/org/example/tierhub/save");
        afficher("");

        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            afficher(newValue);
        });

        newItem.setOnMouseClicked(event -> {
            if(modelmenu) {
                chargerDossier("src/main/resources/org/example/tierhub/save");
                afficher("");
                String cheminRessource = getClass().getResource("PlusCircle.png").toExternalForm();
                image.setImage(new Image(cheminRessource));
                modelmenu = false;
                label.setText("Créer une TierList");
                recherche.setText("");
            }
            else{
                chargerDossier("src/main/resources/org/example/tierhub/save/model");
                afficher("");
                String cheminRessource = getClass().getResource("flecheBas.png").toExternalForm();
                image.setImage(new Image(cheminRessource));
                modelmenu = true;
                label.setText("Retour Selection TierList");
                recherche.setText("");
            }

        });
    }
    private void chargerDossier(String path){
        tiersLists = new ArrayList<>();
        File dossier = new  File(path);


        File[] fichiers = dossier.listFiles((dir, nom) -> nom.toLowerCase().endsWith(".json"));
        if  (fichiers != null) {
            for (File file : fichiers) {
                TierList tl = transform.getJson(file.getAbsolutePath());
                Image img = tl.getImg().getJavaFXImage();
                tiersLists.add(new TierListPreview(tl.getName(), img));
            }
        }
    }

    private void afficher(String recherche){

        tierListGroup.getChildren().clear();
        for  (TierListPreview tl : tiersLists){
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            vbox.setPrefSize(100,130);
            vbox.setCursor(Cursor.HAND); //peut être ajouté dans le CSS directement
            ImageView iv = new ImageView(tl.getImg());
            iv.setFitWidth(70);
            iv.setFitHeight(70);
            vbox.getChildren().add(iv);
            Label lb = new Label(tl.getName());
            vbox.getChildren().add(lb);

            if(recherche.equals("") || tl.getName().toLowerCase().contains(recherche.toLowerCase())){
                tierListGroup.getChildren().add(vbox);
            }


            vbox.setOnMouseClicked(event -> {
                lancerTl(tl.getName());
            });

        }
    }

    private void lancerTl(String nameTl){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tierlist.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage tierlistPage = new Stage();
        tierlistPage.setScene(scene);
        tierlistPage.initOwner(tierListGroup.getScene().getWindow());
        tierlistPage.initModality(Modality.APPLICATION_MODAL);
        tierlistPage.setTitle(nameTl);

        ControlleurTierslist controlleur = loader.getController();
        if (modelmenu) {
            controlleur.setName("model/" + nameTl);
        }else{
            controlleur.setName(nameTl);
        }
        controlleur.chargerJsonSave();
        tierlistPage.show();
        if (modelmenu){
            controlleur.setName(nameTl);
            controlleur.tierListSetting();
        }

    }






}
