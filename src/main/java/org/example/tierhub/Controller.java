package org.example.tierhub;

import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Controller {
    @FXML
    private HBox boiteDeEnBas;

    @FXML
    private Button btn;

    @FXML
    private void addImage(){
        ImageView img = new ImageView("https://cdn.discordapp.com/attachments/1278720637170352258/1505653311381045268/Capture_decran_2025-09-17_193308.png?ex=6a0b686b&is=6a0a16eb&hm=1c6725cdebeaecd1fd5e8d7612774aaee257a0eafa9e71ecf3114794f82bad4e&");
        boiteDeEnBas.getChildren().add(img);
    }
}
