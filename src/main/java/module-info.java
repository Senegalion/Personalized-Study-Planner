module org.example.personalizedstudyplanner {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;

    opens org.example.personalizedstudyplanner to javafx.fxml;
    exports org.example.personalizedstudyplanner;
}