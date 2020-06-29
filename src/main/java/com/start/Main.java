package com.start;

import com.gui.PanelZakonu;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        JFrame okno = new JFrame("System zarządzania Jedi");
        okno.add(new PanelZakonu(okno));
        okno.setResizable(false);
        okno.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        okno.setVisible(true);
        okno.pack();


    }
}
