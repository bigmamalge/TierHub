module org.example.tierhub {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jdk.jshell;


    opens org.example.tierhub to javafx.fxml;
    exports org.example.tierhub;
}