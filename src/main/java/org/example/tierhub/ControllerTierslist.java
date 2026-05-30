package org.example.tierhub;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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

import java.io.File;
import java.io.IOException;
import java.util.Random;


public class ControllerTierslist {
    @FXML
    private TilePane boiteDeEnBas;
    @FXML
    private VBox boiteDeCat;
    @FXML
    ImageView trashcan;


    private Node item;

    FileChooser fileChooser = new FileChooser();

    @FXML
    private void initialize(){
        fileChooser.setTitle("Ouvrir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichiers Image", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

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

        String cheminRessource = getClass().getResource("trash-can.png").toExternalForm();
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

    }

    @FXML
    private void addItem(){
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
        newItem.setTitle("newItem");

        ControlleurParamItem controlleur = loader.getController();

        newItem.showAndWait();

        controlleur.getSortie();
    }


    @FXML
    private void addImage(){
        File fichierimg = fileChooser.showOpenDialog(null);
        if (fichierimg == null){
            return;
        }
        String chemin = fichierimg.toURI().toString();
        ImageView uneImg = new ImageView(chemin);
        uneImg.setFitWidth(70);
        uneImg.setFitHeight(70);

        boiteDeEnBas.getChildren().add(uneImg);

        setDragable(uneImg);


    }

    @FXML
    private void addLabel(){
        StackPane blockLabel = new StackPane();
        blockLabel.setPrefSize(70,70);
        Label label = new Label("test");
        blockLabel.getChildren().add(label);

        boiteDeEnBas.getChildren().add(blockLabel);

        setDragable(blockLabel);

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

        newCat(String.format("#%06X", rdm.nextInt(0xFFFFFF + 1)));
    }

    private void newCat(String color){
        HBox ligne = new HBox();
        ligne.setPrefHeight(70);

        HBox paramPan = new HBox();
        paramPan.setAlignment(Pos.CENTER);
        String cheminRessource = getClass().getResource("engrenage.png").toExternalForm();
        ImageView engrenage = new ImageView(cheminRessource);
        engrenage.setFitHeight(40);
        engrenage.setFitWidth(40);
        paramPan.getChildren().add(engrenage);

        VBox flechePan = new VBox();
        flechePan.setAlignment(Pos.CENTER);
        cheminRessource = getClass().getResource("flecheHaut.png").toExternalForm();
        ImageView flecheHaut = new ImageView(cheminRessource);
        flecheHaut.setFitHeight(20);
        flecheHaut.setFitWidth(20);


        cheminRessource = getClass().getResource("flecheBas.png").toExternalForm();
        ImageView flecheBas = new ImageView(cheminRessource);
        flecheBas.setFitHeight(20);
        flecheBas.setFitWidth(20);

        flechePan.getChildren().add(flecheHaut);
        flechePan.getChildren().add(flecheBas);
        paramPan.getChildren().add(flechePan);

        ligne.getChildren().add(paramPan);


        StackPane titrePan = new StackPane();
        titrePan.setPrefWidth(100);
        Label titre = new Label();
        titre.setText("NomCat");
        titrePan.getChildren().add(titre);
        titrePan.setBackground(Background.fill(Color.web(color)));

        ligne.getChildren().add(titrePan);

        TilePane cat  = new TilePane();
        HBox.setHgrow(cat, Priority.ALWAYS);
        cat.setStyle("-fx-background-color: #c7c8c9 ; -fx-border-color: #7c7c7d;");


        ligne.getChildren().add(cat);

        boiteDeCat.getChildren().add(ligne);

        flecheHaut.setOnMouseClicked(event -> {
            int pos = boiteDeCat.getChildren().indexOf(ligne);
            if(pos > 0){
                boiteDeCat.getChildren().remove(ligne);
                boiteDeCat.getChildren().add(pos - 1, ligne);
            }

        });
        flecheBas.setOnMouseClicked(event -> {
            int pos = boiteDeCat.getChildren().indexOf(ligne);
            if(pos < boiteDeCat.getChildren().size() -1){
                boiteDeCat.getChildren().remove(ligne);
                boiteDeCat.getChildren().add(pos + 1, ligne);
            }
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
            setting.setTitle("Setting");

            ControlleurParamTiers controlleur = loader.getController();

            controlleur.setColor((Color) titrePan.getBackground().getFills().get(0).getFill());// A faire, ps : la flm
            controlleur.setname(titre.getText());

            setting.showAndWait();

            titrePan.setBackground(Background.fill(controlleur.getColor()));
            titre.setText(controlleur.getname());
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



}
