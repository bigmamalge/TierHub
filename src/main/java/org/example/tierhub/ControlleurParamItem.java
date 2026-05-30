package org.example.tierhub;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class ControlleurParamItem {
    @FXML
    private TextField imageLink;
    @FXML
    private ImageView imagePreview;
    @FXML
    private Label erreurTxt;
    @FXML
    private TextField text;
    @FXML
    private ColorPicker colorPicker;

    FileChooser fileChooser = new FileChooser();

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

    @FXML
    private void chargerExplorateur(){
        File fichierimg = fileChooser.showOpenDialog(null);
        if (fichierimg == null){
            return;
        }
        String chemin = fichierimg.toURI().toString();
        imagePreview.setImage(new Image(chemin));
    }

    public Image getImage() {
        return imagePreview.getImage();
    }

    public int getSortie() {
        return sortie;
    }

    public void setImage(Image image) {
        imagePreview.setImage(image);
    }
    public void setColor(Color color){
        colorPicker.setValue(color);
    }
    public void setTitre(String text){
        this.text.setText(text);
    }

    @FXML
    private void quitterImage(){
        sortie = 1;
        Stage stage = (Stage) imageLink.getScene().getWindow();
        stage.close();
    }


    public String getText(){
        return text.getText();
    }

    public Color getColor(){
        return colorPicker.getValue();
    }

    @FXML
    private void quitterTexte(){
        sortie = 2;
        Stage stage = (Stage) imageLink.getScene().getWindow();
        stage.close();
    }

}
