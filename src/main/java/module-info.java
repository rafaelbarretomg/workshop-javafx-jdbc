module org.example.workshopjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.workshopjavafxjdbc to javafx.fxml;
    exports org.example.workshopjavafxjdbc;
}