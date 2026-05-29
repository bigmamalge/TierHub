module org.example.tierhub {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;
    requires org.json;


    opens org.example.tierhub to javafx.fxml;
    exports org.example.tierhub;
}