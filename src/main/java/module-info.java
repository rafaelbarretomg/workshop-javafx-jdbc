module org.example.workshopjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.workshop_javafx_jdbc to javafx.fxml;
    exports org.example.workshop_javafx_jdbc;
}