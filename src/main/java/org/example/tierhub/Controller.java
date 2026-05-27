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
import jdk.jshell.execution.Util;


public class Controller {
    @FXML
    private HBox boiteDeEnBas;
    @FXML
    private HBox boitetest;
    @FXML
    private VBox boiteDeLigne;

    @FXML
    private Button btn;

    private ImageView img;
    private double mouseAnchorX;
    private double mouseAnchorY;

    @FXML
    private void initialize(){
        //acceptation du drag
        for(Node node : boiteDeLigne.getChildren()){
            if (node instanceof HBox){
                HBox hbox = (HBox) node;

                hbox.setOnDragOver(event -> {
                    event.acceptTransferModes(TransferMode.MOVE);
                    event.consume();
                });

                hbox.setOnDragDropped(event -> {
                    if(img != null){
                        HBox base = (HBox) img.getParent();
                        base.getChildren().remove(img);

                        hbox.getChildren().add(img);
                        event.setDropCompleted(true);
                        event.consume();
                    }else{
                        event.setDropCompleted(false);
                    }

                });
            }
        }
    }

    @FXML
    private void addImage(){
        ImageView uneImg = new ImageView("data:image/jpeg;base64,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");
        uneImg.setFitWidth(70);
        uneImg.setFitHeight(70);

        boiteDeEnBas.getChildren().add(uneImg);


        uneImg.setOnDragDetected(event -> {
            Dragboard db = uneImg.startDragAndDrop(TransferMode.MOVE);

            ClipboardContent content = new ClipboardContent();
            content.putString("img : "+uneImg.hashCode());
            db.setContent(content);

            img = uneImg;
            event.consume();
        });

    }

    @FXML
    private void test(MouseEvent event){
        ImageView uneImg = (ImageView) event.getSource();
        uneImg.setFitWidth(100);
        uneImg.setFitHeight(100);
        img  = uneImg;
    }

    @FXML
    private void clickZone(MouseEvent event){
        HBox source = (HBox) img.getParent();

        source.getChildren().remove(img);

        HBox destination = (HBox) event.getSource();

        destination.getChildren().add(img);
    }


}
