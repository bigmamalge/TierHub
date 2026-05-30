package org.example.tierhub;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class ControlleurParamItem {
    @FXML
    private TextField imageLink;
    @FXML
    private ImageView imagePreview;
    @FXML
    private Label erreurTxt;

    private int sortie = 0;

    @FXML
    private void initialize() {
        erreurTxt.setText("");
    }
    @FXML
    private void chargerImage() {
        if(imageLink.getText().equals("")) {
            erreurTxt.setText("Erreur : Veuiller renseigner un lien pour charger");
        }else{
            erreurTxt.setText("");
            imagePreview.setImage(new Image(imageLink.getText()));
        }

    }

    public Image getImage() {
        return imagePreview.getImage();
    }

    public int getSortie() {
        return sortie;
    }

    @FXML
    private void quitterImage(){
        sortie = 1;
        Stage stage = (Stage) imageLink.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void quitterTexte(){
        sortie = 2;
        Stage stage = (Stage) imageLink.getScene().getWindow();
        stage.close();
    }

}
