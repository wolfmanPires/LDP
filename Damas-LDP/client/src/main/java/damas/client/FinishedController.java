package damas.client;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class FinishedController {
    public Text result;

    @FXML
    void rstrt () {
        ClientMain.game.dialog.hide ();
        ClientMain.game.game_window.hide ();
        ClientMain.game.reset ();
        try {
            ClientMain.game.stop ();
            ClientMain.game.start (ClientMain.staticStage);
        } catch (Exception e) {
            System.out.println ("Jogo nao reiniciou (FinishedController)");
            e.printStackTrace (System.out);
        }
    }

    @FXML
    void quit () {
        System.exit (0);
    }
}
