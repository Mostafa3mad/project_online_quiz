package com.mycompany.quiz_online1.builder;

import javax.swing.*;
import java.awt.event.ActionListener;

public class CustomFrameBuilder {
    private JFrame frame;
    private String title = "Default Title";
    private int width = 400;
    private int height = 300;
    private boolean menuBar = false;

    public CustomFrameBuilder() {
        frame = new JFrame();
    }

    public CustomFrameBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public CustomFrameBuilder setSize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public CustomFrameBuilder addMenuBar() {
        this.menuBar = true;
        return this;
    }

    public CustomFrameBuilder addButton(String text, int x, int y, int width, int height, ActionListener action) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.addActionListener(action);
        frame.add(button);
        return this;
    }

    public CustomFrameBuilder addLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        frame.add(label);
        return this;
    }

    public CustomFrameBuilder addTextField(int x, int y, int width, int height) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, width, height);
        frame.add(textField);
        return this;
    }

    public JFrame build() {
        frame.setTitle(title);
        frame.setSize(width, height);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if (menuBar) {
            JMenuBar menuBar = new JMenuBar();
            frame.setJMenuBar(menuBar);
        }

        return frame;
    }
}
