module com.example.cardgame {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.cardgame to javafx.fxml;
    exports com.example.cardgame;
}