package com.dove.view.viewCardapio;

import com.dove.controller.CardapioController;

import javax.swing.*;
import java.awt.*;

public class CardapioFrame extends JFrame {
    private CardapioController controller;
    private CardapioPanel cardapioPanel;

    public CardapioFrame() {
        controller = new CardapioController();
        cardapioPanel = new CardapioPanel(controller);

        setTitle("Gerenciamento de Cardápios");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(cardapioPanel, BorderLayout.CENTER);
    }
}
