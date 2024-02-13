module org.example.workshopjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.workshop_javafx_jdbc to javafx.fxml;
    exports org.example.workshop_javafx_jdbc;
    exports org.example.workshop_javafx_jdbc.model.entities;
    opens org.example.workshop_javafx_jdbc.model.entities to javafx.fxml;
    exports org.example.workshop_javafx_jdbc.model.services;
    opens org.example.workshop_javafx_jdbc.model.services to javafx.fxml;
    exports org.example.workshop_javafx_jdbc.gui.util;
    opens org.example.workshop_javafx_jdbc.gui.util to javafx.fxml;
}