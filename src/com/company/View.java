package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class View {
    private Controller controller;
    private JLabel label;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void create(int width, int height) {
        JFrame frame = new JFrame();
        frame.setSize(width, height);
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.BLACK);

        label = new JLabel();
        label.setBounds(0, 0, width, height);
        frame.add(label);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                controller.handleKeyPress(e.getKeyCode());
            }
        });



        frame.setVisible(true);
    }

    public void setImageLabel(BufferedImage image) {
        label.setIcon(new ImageIcon(image));
    }
}
