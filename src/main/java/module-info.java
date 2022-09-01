module com.example.trilhaclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.trilha to javafx.fxml;
    exports com.example.trilha;
}