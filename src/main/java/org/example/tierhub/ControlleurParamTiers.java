package org.example.tierhub;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ControlleurParamTiers {
    @FXML
    ColorPicker colorPicker;

    @FXML
    Label label;

    @FXML
    TextField textfield;

    public void setColor(Color color) {
        colorPicker.setValue(color);
    }
    public void setname(String name) {
        label.setText(name);
        textfield.setText(name);
    }

    public Color getColor() {
        return colorPicker.getValue();
    }
    public String getname() {
        return textfield.getText();
    }

    @FXML
    private void quitter(){
        Stage stage = (Stage) label.getScene().getWindow();
        stage.close();
    }

}
