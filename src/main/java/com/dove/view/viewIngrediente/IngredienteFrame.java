package com.dove.view.viewIngrediente;

import com.dove.controller.IngredienteController;

import javax.swing.*;
import java.awt.*;

public class IngredienteFrame extends JFrame {

    private IngredienteController controller;
    private IngredientePanel ingredientePanel;

    public IngredienteFrame() {
        controller = new IngredienteController();
        ingredientePanel = new IngredientePanel();

        setTitle("Gerenciamento de Ingredientes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(ingredientePanel, BorderLayout.CENTER);
    }
}
