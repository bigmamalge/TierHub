package org.example.tierhub;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Controller {
    @FXML
    private HBox boiteDeEnBas;

    @FXML
    private Button btn;

    @FXML
    private Button colour;

    private ImageView img;

    @FXML
    private void addImage(){
        ImageView uneImg = new ImageView("https://cdn.discordapp.com/attachments/1278720637170352258/1505653311381045268/Capture_decran_2025-09-17_193308.png?ex=6a0b686b&is=6a0a16eb&hm=1c6725cdebeaecd1fd5e8d7612774aaee257a0eafa9e71ecf3114794f82bad4e&");
        uneImg.setFitWidth(70);
        uneImg.setFitHeight(70);

        boiteDeEnBas.getChildren().add(uneImg);
        uneImg.setOnMouseClicked(event -> test(event));


    }

    @FXML
    private void changeCSS(ActionEvent event){
        Scene scene = (Scene) btn.getScene();
        scene.getStylesheets().add(getClass().getResource("application-light.css").toExternalForm());
    }

    @Override
    public void initialize(URL url, RessourceBundle rb){
        colour.getScene().getRoot().getStylesheets().add(getClass().getResource("application-light.css").toString());
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
