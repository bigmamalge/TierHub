package org.example.tierhub;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ControlleurParamTierlist {
    @FXML
    private TextField imageLink;
    @FXML
    private ImageView imagePreview;
    @FXML
    private Label erreurTxt;
    @FXML
    private TextField nom;
    @FXML
    private TextField itemSize;

    FileChooser fileChooser = new FileChooser();

    @FXML
    private void initialize() {
        erreurTxt.setText("");
    }
    @FXML
    private void chargerImage() {
        if(imageLink.getText().equals("")) {
            erreurTxt.setText("Erreur : Veuillez renseigner un lien pour charger");
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

    @FXML
    private void quitter(){
        Stage stage = (Stage) imageLink.getScene().getWindow();
        stage.close();
    }

    public String getNom(){
        return nom.getText();
    }
    public Image getImage(){
        return imagePreview.getImage();
    }

    public void setNom(String nom){
        this.nom.setText(nom);
    }
    public void setImage(Image image){
        this.imagePreview.setImage(image);
    }

    public void setSize(int size){
        itemSize.setText(Integer.toString(size));
    }

    public int getSize(){
        return Integer.parseInt(itemSize.getText());
    }
}
