package org.example.tierhub;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import jdk.jshell.execution.Util;

import java.io.File;


public class Controller {
    @FXML
    private HBox boiteDeEnBas;
    @FXML
    private VBox boiteDeLigne;
    @FXML
    private Button btn;

    private ImageView img;

    FileChooser fileChooser = new FileChooser();

    @FXML
    private void initialize(){
        fileChooser.setTitle("Ouvrir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichiers Image", "*.png", "*.jpg", "*.jpeg")
        );
    }

    @FXML
    private void addImage(){
        File fichierimg = fileChooser.showOpenDialog(null);
        String chemin = fichierimg.toURI().toString();
        ImageView uneImg = new ImageView(chemin);
        uneImg.setFitWidth(70);
        uneImg.setFitHeight(70);

        boiteDeEnBas.getChildren().add(uneImg);


        uneImg.setOnDragDetected(event -> {
            Dragboard db = uneImg.startDragAndDrop(TransferMode.MOVE);

            ClipboardContent content = new ClipboardContent();
            content.putString("img : "+uneImg.hashCode());
            db.setContent(content);

            db.setDragView(uneImg.getImage());
            db.setDragViewOffsetX(uneImg.getFitWidth() / 2);
            db.setDragViewOffsetY(uneImg.getFitHeight() / 2);

            img = uneImg;
            event.consume();
        });

    }

    @FXML
    private void newCat(){
        HBox cat = new HBox();
        boiteDeLigne.getChildren().add(cat);
        cat.setMaxWidth(Double.MAX_VALUE);
        cat.setMinHeight(80);
        cat.setStyle("-fx-border-color: gray; -fx-border-width: 1;");



        cat.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.MOVE);
            event.consume();
        });

        cat.setOnDragDropped(event -> {
            if(img != null){
                HBox base = (HBox) img.getParent();
                base.getChildren().remove(img);

                cat.getChildren().add(img);
                event.setDropCompleted(true);
                event.consume();
            }else{
                event.setDropCompleted(false);
            }

        });
    }



}
