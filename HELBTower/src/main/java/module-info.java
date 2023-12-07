module com.example.helbtower {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
                            
    opens com.example.helbtower to javafx.fxml;
    exports com.example.helbtower;
}