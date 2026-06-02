package org.example.tierhub;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.example.model.*;
import org.example.service.Jackson.Transform;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Random;


public class ControlleurTierslist {
    @FXML
    private TilePane boiteDeEnBas;
    @FXML
    private VBox boiteDeCat;
    @FXML
    private ImageView trashcan;
    @FXML
    private ImageView modify;


    private Node item;
    private boolean LightMode = true;


    private final File fichierJson = new File("tierList.json");
    private final Transform transform = new Transform();

    private int itemSize;

    @FXML
    private Label name;
    @FXML
    private ImageView imgLogo;

    private boolean colorbool = true;
    private String theme = "Clair";

    @FXML
    private void initialize(){
        javafx.application.Platform.runLater(() -> {
            setcolortier();
        });
        boiteDeEnBas.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                changeMode();
                String cheminRessource = getClass().getResource("/org/example/tierhub/images/"+theme+"/engrenage.png").toExternalForm();
                modify.setImage(new Image(cheminRessource));
            }
        });

        itemSize = 100;

        boiteDeEnBas.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.MOVE);
            event.consume();
        });

        boiteDeEnBas.setOnDragDropped(event -> {
            if(item != null){
                TilePane base = (TilePane) item.getParent();
                base.getChildren().remove(item);

                boiteDeEnBas.getChildren().add(item);
                event.setDropCompleted(true);
                event.consume();
            }else{
                event.setDropCompleted(false);
            }

        });

        String cheminRessource = getClass().getResource("/org/example/tierhub/images/trash-can.png").toExternalForm();
        trashcan.setImage(new Image(cheminRessource));

        trashcan.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.MOVE);
            event.consume();
        });

        trashcan.setOnDragDropped(event -> {
            if(item != null){
                TilePane base = (TilePane) item.getParent();
                base.getChildren().remove(item);
                event.setDropCompleted(true);
                event.consume();
            }else{
                event.setDropCompleted(false);
            }

        });


        modify.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.MOVE);
            event.consume();
        });

        modify.setOnDragDropped(event -> {
            if(item != null){
                TilePane base = (TilePane) item.getParent();
                base.getChildren().remove(item);
                if(item instanceof ImageView){
                    ImageView iv = (ImageView) item;
                    addItem(base,iv.getImage());
                }
                else if (item instanceof StackPane){
                    StackPane sp = (StackPane) item;
                    Label lb = (Label) sp.getChildren().get(0);
                    addItem(base, (Color) sp.getBackground().getFills().get(0).getFill(),lb.getText());
                }

                event.setDropCompleted(true);
                event.consume();
            }else{
                event.setDropCompleted(false);
            }

        });

        name.setText("My TierList");
        cheminRessource = getClass().getResource("TierHub.png").toExternalForm();
        imgLogo.setImage(new Image(cheminRessource));

    }

    @FXML
    private void addItem(){
        String cheminRessource = getClass().getResource("TierHub.png").toExternalForm();
        addItem(boiteDeEnBas, new Image(cheminRessource),Color.web("#27F52A"),"");
    }

    private void addItem(TilePane tilePane, Image img){
        addItem(tilePane,img,Color.web("#27F52A"),"");
    }
    private void addItem(TilePane tilePane, Color color, String txt){
        String cheminRessource = getClass().getResource("TierHub.png").toExternalForm();
        addItem(tilePane, new Image(cheminRessource),color,txt);
    }

    private void addItem(TilePane tilePane, Image img, Color color, String txt){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("paramItem.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage newItem = new Stage();
        newItem.setScene(scene);
        newItem.initOwner(boiteDeCat.getScene().getWindow());
        newItem.initModality(Modality.APPLICATION_MODAL);
        newItem.setTitle("Nouvel Item");
        newItem.setResizable(false);

        ControlleurParamItem controlleur = loader.getController();
        controlleur.setImage(img);
        controlleur.setColor(color);
        controlleur.setTitre(txt);

        newItem.showAndWait();

        if (controlleur.getSortie() == 1){
            ImageView uneImg = new ImageView(controlleur.getImage());
            uneImg.setFitWidth(itemSize);
            uneImg.setFitHeight(itemSize);

            tilePane.getChildren().add(uneImg);

            setDragable(uneImg);
        }

        if (controlleur.getSortie() == 2){
            StackPane blockLabel = new StackPane();
            blockLabel.setPrefSize(itemSize, itemSize);
            Label label = new Label(controlleur.getText());
            blockLabel.setBackground(Background.fill(controlleur.getColor()));
            blockLabel.getChildren().add(label);

            tilePane.getChildren().add(blockLabel);

            setDragable(blockLabel);
        }

        if (controlleur.getSortie() == 3){
            ImageView uneImg = new ImageView(controlleur.getImageJeu());
            uneImg.setFitWidth(itemSize);
            uneImg.setFitHeight(itemSize);

            tilePane.getChildren().add(uneImg);

            setDragable(uneImg);
        }
    }


    private void setDragable(Node node){
        node.setOnDragDetected(event -> {
            Dragboard db = node.startDragAndDrop(TransferMode.MOVE);

            ClipboardContent content = new ClipboardContent();
            content.putString("img : "+node.hashCode());
            db.setContent(content);

            Image imagePrevisu = node.snapshot(null, null);

            db.setDragView(imagePrevisu);
            db.setDragViewOffsetX(imagePrevisu.getWidth()/2);
            db.setDragViewOffsetY(imagePrevisu.getWidth() / 2);

            item = node;
            event.consume();
        });
    }

    @FXML
    private void newCat(){
        Random rdm = new Random();

        newCat(String.format("#%06X", rdm.nextInt(0xFFFFFF + 1)),"Nom Cat");
    }

    private void newCat(String color, String name){
        HBox ligne = new HBox();
        ligne.setPrefHeight(itemSize);

        HBox paramPan = new HBox();
        paramPan.setAlignment(Pos.CENTER);
        String cheminRessource = getClass().getResource("/org/example/tierhub/images/gear.png").toExternalForm();
        ImageView engrenage = new ImageView(cheminRessource);
        engrenage.getStyleClass().add("icons");
        engrenage.setFitHeight(40);
        engrenage.setFitWidth(40);
        paramPan.getChildren().add(engrenage);

        VBox flechePan = new VBox();
        flechePan.setAlignment(Pos.CENTER);
        cheminRessource = getClass().getResource("/org/example/tierhub/images/"+theme+"/up-arrow.png").toExternalForm();
        ImageView flecheHaut = new ImageView(cheminRessource);
        flecheHaut.getStyleClass().add("icons");
        flecheHaut.setFitHeight(20);
        flecheHaut.setFitWidth(20);

        cheminRessource = getClass().getResource("/org/example/tierhub/images/"+theme+"/down-arrow.png").toExternalForm();
        ImageView flecheBas = new ImageView(cheminRessource);
        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(-1.0);
        flecheBas.getStyleClass().add("icons");
        flecheBas.setFitHeight(20);
        flecheBas.setFitWidth(20);

        flechePan.getChildren().add(flecheHaut);
        flechePan.getChildren().add(flecheBas);
        paramPan.getChildren().add(flechePan);

        ligne.getChildren().add(paramPan);


        StackPane titrePan = new StackPane();
        titrePan.setPrefWidth(100);
        Label titre = new Label();
        titre.setText(name);
        titrePan.getChildren().add(titre);
        titrePan.setBackground(Background.fill(Color.web(color)));

        ligne.getChildren().add(titrePan);

        TilePane cat  = new TilePane();
        HBox.setHgrow(cat, Priority.ALWAYS);
        //cat.setStyle("-fx-background-color: #c7c8c9 ; -fx-border-color: #7c7c7d;");

        ligne.getChildren().add(cat);

        boiteDeCat.getChildren().add(ligne);
        setcolortier();

        flecheHaut.setOnMouseClicked(event -> {
            int pos = boiteDeCat.getChildren().indexOf(ligne);
            if(pos > 0){
                boiteDeCat.getChildren().remove(ligne);
                boiteDeCat.getChildren().add(pos - 1, ligne);
            }
            setcolortier();

        });
        flecheBas.setOnMouseClicked(event -> {
            int pos = boiteDeCat.getChildren().indexOf(ligne);
            if(pos < boiteDeCat.getChildren().size() -1){
                boiteDeCat.getChildren().remove(ligne);
                boiteDeCat.getChildren().add(pos + 1, ligne);
            }
            setcolortier();
        });

        engrenage.setOnMouseClicked(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("paramTiers.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(loader.load());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage setting = new Stage();
            setting.setScene(scene);
            setting.initOwner(boiteDeCat.getScene().getWindow());
            setting.initModality(Modality.APPLICATION_MODAL);
            setting.setTitle("Paramètres");
            setting.setResizable(false);

            ControlleurParamTiers controlleur = loader.getController();

            controlleur.setColor((Color) titrePan.getBackground().getFills().get(0).getFill());
            controlleur.setname(titre.getText());

            setting.showAndWait();

            titrePan.setBackground(Background.fill(controlleur.getColor()));
            titre.setText(controlleur.getname());

            if(controlleur.isDelete()){
                int pos = boiteDeCat.getChildren().indexOf(ligne);
                boiteDeCat.getChildren().remove(ligne);
            }
        });

        cat.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.MOVE);
            event.consume();
        });

        cat.setOnDragDropped(event -> {
            if(item != null){
                TilePane base = (TilePane) item.getParent();
                base.getChildren().remove(item);

                cat.getChildren().add(item);
                event.setDropCompleted(true);
                event.consume();
            }else{
                event.setDropCompleted(false);
            }

        });
    }

    private void setcolortier(){
        for(Node node : boiteDeCat.getChildren()){
            HBox hbox = (HBox) node;
            TilePane cat = (TilePane) hbox.getChildren().get(2);
            if(colorbool){

                cat.getStyleClass().clear();
                cat.getStyleClass().add("tier1");

            }else{

                cat.getStyleClass().clear();
                cat.getStyleClass().add("tier2");
            }
            VBox vbox = (VBox) ((HBox) hbox.getChildren().get(0)).getChildren().get(1);
            ImageView flecheHaut = (ImageView) vbox.getChildren().get(0);
            ImageView flecheBas = (ImageView) vbox.getChildren().get(1);
            ImageView engrenage = (ImageView) ((HBox) hbox.getChildren().get(0)).getChildren().get(0);




            String cheminRessource = getClass().getResource("/org/example/tierhub/images/"+theme+"/down-arrow.png").toExternalForm();
            String cheminRessource2 = getClass().getResource("/org/example/tierhub/images/"+theme+"/up-arrow.png").toExternalForm();
            String cheminRessource3 = getClass().getResource("/org/example/tierhub/images/"+theme+"/engrenage.png").toExternalForm();
            flecheBas.setImage(new Image(cheminRessource));
            flecheHaut.setImage(new Image(cheminRessource2));
            engrenage.setImage(new Image(cheminRessource3));
            modify.setImage(new Image(cheminRessource3));
            colorbool = !colorbool;

        }


    }


    @FXML
    private void saveAsJson(){
        byte[] dataimageLogo = transform.convertImageEnByte(imgLogo.getImage());
        ItemImage itemImageLogo = new ItemImage(dataimageLogo);
        TierList tierlist = new TierList(new ArrayList<>(),itemImageLogo,name.getText());

        Tier tierDeBase = new Tier(new ArrayList<>(),"Tier de en Bas","pas de couleur");
        for (Node child : boiteDeEnBas.getChildren()) {
            if(child instanceof StackPane){
                StackPane stackPane = (StackPane) child;
                Label label = (Label) stackPane.getChildren().get(0);
                Color color = (Color) stackPane.getBackground().getFills().get(0).getFill();
                String couleur = "#" + color.toString().substring(2,8).toUpperCase();
                ItemTexte itemTexte = new ItemTexte(couleur, label.getText());
                tierDeBase.getItems().add(itemTexte);
            }else if(child instanceof ImageView){
                ImageView imageView = (ImageView) child;
                byte[] dataimage = transform.convertImageEnByte(imageView.getImage());
                ItemImage itemImage = new ItemImage(dataimage);
                tierDeBase.getItems().add(itemImage);
            }
        }
        tierlist.getTiers().add(tierDeBase);

        for (Node node : boiteDeCat.getChildren()) {
            if(node instanceof HBox){
                TilePane tilePane = (TilePane) ((HBox) node).getChildren().get(2);
                StackPane titrePane = (StackPane) ((HBox) node).getChildren().get(1);
                Color titreColor = (Color) titrePane.getBackground().getFills().get(0).getFill();
                String titreCouleur = "#" + titreColor.toString().substring(2,8).toUpperCase();
                Label titre = (Label) titrePane.getChildren().get(0);

                Tier tier = new Tier(new ArrayList<>(),titre.getText(),titreCouleur);

                for (Node child : tilePane.getChildren()) {
                    if(child instanceof StackPane){
                        StackPane stackPane = (StackPane) child;
                        Label label = (Label) stackPane.getChildren().get(0);
                        Color color = (Color) stackPane.getBackground().getFills().get(0).getFill();
                        String couleur = "#" + color.toString().substring(2,8).toUpperCase();
                        ItemTexte itemTexte = new ItemTexte(couleur, label.getText());
                        tier.getItems().add(itemTexte);
                    }else if(child instanceof ImageView){
                        ImageView imageView = (ImageView) child;
                        byte[] dataimage = transform.convertImageEnByte(imageView.getImage());
                        ItemImage itemImage = new ItemImage(dataimage);
                        tier.getItems().add(itemImage);
                    }

                }
                tierlist.getTiers().add(tier);
            }
        }

        transform.setJson("src/main/resources/org/example/tierhub/save/"+ name.getText() +".json", tierlist);

    }


    @FXML
    public void chargerJsonSave(){
        boiteDeEnBas.getChildren().clear();
        boiteDeCat.getChildren().clear();

        TierList tierlist = transform.getJson("src/main/resources/org/example/tierhub/save/"+ name.getText() +".json");
        imgLogo.setImage(tierlist.getImg().getJavaFXImage());
        name.setText(tierlist.getName());

        for(Tier tier : tierlist.getTiers()){
            TilePane tilePane;
            if(tier.equals(tierlist.getTiers().get(0))){
                tilePane = boiteDeEnBas;
            }
            else{
                newCat(tier.getColor(), tier.getName());
                HBox hBox = (HBox) boiteDeCat.getChildren().get(boiteDeCat.getChildren().size() -1);
                tilePane = (TilePane) hBox.getChildren().get(2);
            }
            for(Item item : tier.getItems()){
                if (item instanceof ItemTexte){
                    ItemTexte itemTexte = (ItemTexte) item;
                    addTextJson(tilePane,Color.web(itemTexte.getColor()),itemTexte.getTexte());
                }
                if (item instanceof ItemImage){
                    ItemImage itemImage = (ItemImage) item;
                    Image img = itemImage.getJavaFXImage();
                    addImageJson(tilePane,img);
                }
            }
        }

    }

    private void addImageJson(TilePane tilePane, Image img){
        ImageView uneImg = new ImageView(img);
        uneImg.setFitWidth(itemSize);
        uneImg.setFitHeight(itemSize);

        tilePane.getChildren().add(uneImg);

        setDragable(uneImg);
    }
    private void addTextJson(TilePane tilePane, Color color, String txt){
        StackPane blockLabel = new StackPane();
        blockLabel.setPrefSize(itemSize, itemSize);
        Label label = new Label(txt);
        blockLabel.setBackground(Background.fill(color));
        blockLabel.getChildren().add(label);

        tilePane.getChildren().add(blockLabel);

        setDragable(blockLabel);
    }

    @FXML
    public void tierListSetting(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("paramTierList.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage param = new Stage();
        param.setScene(scene);
        param.initOwner(boiteDeCat.getScene().getWindow());
        param.initModality(Modality.APPLICATION_MODAL);
        param.setTitle("Paramètres de la TierList");
        param.setResizable(false);

        ControlleurParamTierlist controlleur = loader.getController();

        controlleur.setImage(imgLogo.getImage());
        controlleur.setNom(name.getText());
        controlleur.setSize(itemSize);

        param.showAndWait();

        imgLogo.setImage(controlleur.getImage());
        name.setText(controlleur.getNom());
        itemSize = controlleur.getSize();
        setSize();
    }

    public void setName(String name){
        this.name.setText(name);
    }

    public void changeMode(){
        Scene scene = boiteDeCat.getScene();
        String darkCSS = getClass().getResource("css/application-dark.css").toExternalForm();
        String lightCSS = getClass().getResource("css/application-light.css").toExternalForm();

        LightMode = !LightMode;
        scene.getStylesheets().clear();

        if(LightMode){
            scene.getStylesheets().add(darkCSS);
            theme = "Sombre";
        }else{
            scene.getStylesheets().add(lightCSS);
            theme = "Clair";
        }
        setcolortier();
    }

    public void suppTl(){
        Path cheminDuFichier = Paths.get("src/main/resources/org/example/tierhub/save/"+ name.getText() +".json");
        try {
            boolean estSupprime = Files.deleteIfExists(cheminDuFichier);

            if (estSupprime) {
                System.out.println("Le fichier a été supprimé avec succès ! 🗑️");
            } else {
                System.out.println("Le fichier n'existait pas, il n'y a rien à supprimer.");
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        quitter();
    }

    @FXML
    private void quitter(){
        Stage stage = (Stage) boiteDeCat.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void dupliquer(){
        saveAsJson();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tierlist.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage tierlistPage = new Stage();
        tierlistPage.setScene(scene);
        tierlistPage.initOwner(boiteDeCat.getScene().getWindow());
        tierlistPage.initModality(Modality.APPLICATION_MODAL);
        tierlistPage.setTitle(name.getText());

        ControlleurTierslist controlleur = loader.getController();
        controlleur.setName(name.getText());


        controlleur.chargerJsonSave();
        tierlistPage.show();

        controlleur.setName(name.getText()+"-Copy");
        controlleur.tierListSetting();

    }

    @FXML
    private void resetItem(){
        for(Node node : boiteDeCat.getChildren()){
            HBox hbox = (HBox) node;
            TilePane cat = (TilePane) hbox.getChildren().get(2);

            boiteDeEnBas.getChildren().addAll(cat.getChildren());

        }
    }

    private void setSize(){
        for(Node node : boiteDeCat.getChildren()){
            HBox hbox = (HBox) node;
            TilePane cat = (TilePane) hbox.getChildren().get(2);
            hbox.setPrefHeight(itemSize);

            for (Node child : cat.getChildren()) {
                if(child instanceof StackPane){
                    StackPane stackPane = (StackPane) child;
                    stackPane.setPrefSize(itemSize, itemSize);

                }else if(child instanceof ImageView){
                    ImageView imageView = (ImageView) child;
                    imageView.setFitWidth(itemSize);
                    imageView.setFitHeight(itemSize);

                }
            }

        }
    }

    @FXML
    public void exporter(){
        saveAsJson();
        File fichierSource = new File("src/main/resources/org/example/tierhub/save/"+ name.getText() +".json");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter la TierList");

        fileChooser.setInitialFileName(fichierSource.getName());

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Fichier JSON", "*.json")
        );

        Window fenetre = boiteDeEnBas.getScene().getWindow();
        File fichierDestination = fileChooser.showSaveDialog(fenetre);

        if(fichierDestination != null){
            try {
                Files.copy(fichierSource.toPath(), fichierDestination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void importer(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importer une TierList");


        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Fichier JSON", "*.json")
        );

        Window fenetre = boiteDeEnBas.getScene().getWindow();
        File fichierChoisi = fileChooser.showOpenDialog(fenetre);

        if(fichierChoisi != null){
            File dossierSauvegardes = new File("src/main/resources/org/example/tierhub/save");
            File fichierDestination = new File(dossierSauvegardes, fichierChoisi.getName());
            try {
                Files.copy(fichierChoisi.toPath(), fichierDestination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String leNom = fichierDestination.getName();
            leNom = leNom.replace(".json","");
            name.setText(leNom);
            chargerJsonSave();
        }

    }


}