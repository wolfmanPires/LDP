module damas.server {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires com.almasb.fxgl.all;

    opens damas.server to javafx.fxml;
    exports damas.server;
}