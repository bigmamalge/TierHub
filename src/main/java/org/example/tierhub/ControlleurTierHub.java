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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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

        preparerDossiersEtModeles();

        chargerDossier("sauvegardes");
        afficher("");

        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            afficher(newValue);
        });

        newItem.setOnMouseClicked(event -> {
            if(modelmenu) {
                chargerDossier("sauvegardes");
                afficher("");
                String cheminRessource = getClass().getResource("/org/example/tierhub/images/PlusCircle.png").toExternalForm();
                image.setImage(new Image(cheminRessource));
                modelmenu = false;
                label.setText("Créer une TierList");
                recherche.setText("");
            }
            else{
                chargerDossier("modeles");
                afficher("");
                String cheminRessource = getClass().getResource("/org/example/tierhub/images/Clair/down-arrow.png").toExternalForm();
                image.setImage(new Image(cheminRessource));
                modelmenu = true;
                label.setText("Retour Selection TierList");
                recherche.setText("");
            }

        });
    }

    private void preparerDossiersEtModeles() {

        File dossierSauvegardes = new File("sauvegardes");
        dossierSauvegardes.mkdirs();

        String[] savesDeBase = {
                "Calisthenics Moves.json",
                "Hybrid Theory Linkin Park.json",
                "Les couleurs.json",
                "Les Fruits !.json",
                "Top 50 jeux 2025.json"
        };

        for (String nomFichier : savesDeBase) {
            File saveExterne = new File(dossierSauvegardes, nomFichier);
            if (!saveExterne.exists()) {
                try {
                    InputStream saveInterne = getClass().getResourceAsStream("/org/example/tierhub/save/" + nomFichier);
                    if (saveInterne != null) {
                        Files.copy(saveInterne, saveExterne.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (Exception e) {
                    System.err.println("Erreur sauvegarde : " + e.getMessage());
                }
            }
        }

        File dossierModeles = new File("modeles");
        dossierModeles.mkdirs();

        String[] modelesDeBase = {
                "model_vide.json",
                "Tier_Config_Defaut.json"
        };

        for (String nomFichier : modelesDeBase) {
            File modeleExterne = new File(dossierModeles, nomFichier);
            if (!modeleExterne.exists()) {
                try {
                    InputStream modeleInterne = getClass().getResourceAsStream("/org/example/tierhub/save/model/" + nomFichier);
                    if (modeleInterne != null) {
                        Files.copy(modeleInterne, modeleExterne.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (Exception e) {
                    System.err.println("Erreur modèle : " + e.getMessage());
                }
            }
        }
    }

    private void chargerDossier(String path){
        tiersLists = new ArrayList<>();
        File dossier = new File(path);

        if (!dossier.exists()) return;

        File[] fichiers = dossier.listFiles((dir, nom) -> nom.toLowerCase().endsWith(".json"));
        if  (fichiers != null) {
            for (File file : fichiers) {
                TierList tl = transform.getJson(file.getAbsolutePath());
                if (tl != null && tl.getImg() != null) {
                    Image img = tl.getImg().getJavaFXImage();
                    tiersLists.add(new TierListPreview(tl.getName(), img));
                }
            }
        }
    }

    private void afficher(String recherche){

        tierListGroup.getChildren().clear();
        for  (TierListPreview tl : tiersLists){
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            vbox.setPrefSize(100,130);
            vbox.setCursor(Cursor.HAND);
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