package org.Bjornbak;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("GUI");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DrawPanel drawPanel = new DrawPanel();
        mainFrame.add(drawPanel);
        mainFrame.pack();
        mainFrame.setVisible(true);

    }

}