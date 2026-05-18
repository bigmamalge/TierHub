module org.example.tierhub {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.tierhub to javafx.fxml;
    exports org.example.tierhub;
}