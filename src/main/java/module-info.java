module org.example.tierhub {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jdk.jshell;
    requires javafx.swing;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;
    requires org.json;


    opens org.example.tierhub to javafx.fxml;
    opens org.example.model to com.fasterxml.jackson.databind;
    exports org.example.tierhub;
}