package com.example.damas;

import javafx.fxml.FXML;

import java.awt.*;

public class ComoJogarController {
    @FXML
    private Label explanationText;

    @FXML
    public void initialize() {
        explanationText.setText("Explicação detalhada de como jogar...");
    }
}