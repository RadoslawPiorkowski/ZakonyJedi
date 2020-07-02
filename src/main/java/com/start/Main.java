package com.start;

import com.gui.PanelZakonu;
import com.zasoby.Jedi;
import com.zasoby.Szfrowanie;

import javax.swing.*;


public class Main {

    public static void main(String[] args) {

        JFrame okno = new JFrame("System zarządzania Jedi");
        okno.add(new PanelZakonu(okno));
        okno.setResizable(false);

        okno.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        okno.setVisible(true);
        okno.pack();


        System.out.println(Szfrowanie.szyfrowanieJedi(Jedi.listaJedi));
    }
}
