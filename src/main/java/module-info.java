module org.example.workshopjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.workshop_javafx_jdbc to javafx.fxml;
    exports org.example.workshop_javafx_jdbc;
}