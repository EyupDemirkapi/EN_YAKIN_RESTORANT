module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.enyakinrestorant to javafx.fxml;
    exports com.example.enyakinrestorant;
}