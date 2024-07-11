module damas.damasldp {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires com.almasb.fxgl.all;

    opens damas.damasldp to javafx.fxml;
    exports damas.damasldp;
}