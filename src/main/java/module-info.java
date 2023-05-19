module at.ac.fhcampuswien.fhmdb {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.jfoenix;
    requires okhttp3;
    requires com.google.gson;




    opens at.ac.fhcampuswien.fhmdb to javafx.fxml;
    exports at.ac.fhcampuswien.fhmdb;

    exports at.ac.fhcampuswien.fhmdb.models;

    opens at.ac.fhcampuswien.fhmdb.DataTier to javafx.fxml;
    exports at.ac.fhcampuswien.fhmdb.DataTier;

    opens at.ac.fhcampuswien.fhmdb.ApplicationTier.controllers to javafx.fxml;
    exports at.ac.fhcampuswien.fhmdb.ApplicationTier.controllers;
}