module damas.client {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires com.almasb.fxgl.all;

    opens damas.client to javafx.fxml;
    exports damas.client;
}