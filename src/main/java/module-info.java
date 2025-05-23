module org.example.personalizedstudyplanner {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires java.sql;
    requires org.slf4j;

    opens org.example.personalizedstudyplanner to javafx.fxml;
    exports org.example.personalizedstudyplanner;
    exports org.example.personalizedstudyplanner.controllers;
    opens org.example.personalizedstudyplanner.controllers to javafx.fxml;
}