package com.testy;

import com.gui.*;
import com.zasoby.*;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.Assert.*;


/**
 * Testy dla Zakonow Jedi
 * @version 0.0.1 2020-07-03
 * @author Radosław Piórkowski
 */

public class JediTesty {

    public JediTesty() {
    }


    /**
     * Sprawdza poprawnosc szyfrowania Rycerzy Jedi
     */

    JTextField inputNazwaJedi;
    JComboBox<String> kolory;
    JSlider poziomMocy;
    String strona;

    ArrayList<Jedi> listaAdeptow = new ArrayList<Jedi>();
    JTextField inputNazwaZak;


    @Test
    public void testowanieSzyfrowaniaJedi() {

        for (Jedi j: Jedi.listaJedi) {
            String tekstPoczatkowy = j.toString();
            String tekstZaszyfrowany = Szfrowanie.szyfrowanieTekstu(tekstPoczatkowy);
            String tekstOdszyfrowany = Szfrowanie.deszyfrowanieTekstu(tekstZaszyfrowany);
            assertEquals(tekstPoczatkowy, tekstOdszyfrowany);
        }
    }

    /**
     * Sprawdza poprawnosc szyfrowania Zaknow Jedi
     */


    @Test
    public void testowanieSzyfrowaniaZakonu() {

        for (Zakon z: Zakon.getLista()) {
            String tekstPoczatkowy = z.toString();
            String tekstZaszyfrowany = Szfrowanie.szyfrowanieTekstu(tekstPoczatkowy);
            String tekstOdszyfrowany = Szfrowanie.deszyfrowanieTekstu(tekstZaszyfrowany);
            assertEquals(tekstPoczatkowy, tekstOdszyfrowany);
        }
    }


    /**
     * Sprawdza tworzenie Rycerza Jedi z za długim imieniem
     */

    @Test
    public void tworzenieJediDlugi () {

        inputNazwaJedi.setText("JIOPAJNwejiwooakwpkmd0842");
        kolory.setSelectedIndex(1);
        poziomMocy.setValue(155);
        strona = "ciemny";

        assertFalse(stworzJedi());
    }

    /**
     * Sprawdza tworzenie Rycerza Jedi z za długim imieniem
     */

    @Test
    public void tworzenieJediKrotki () {

        inputNazwaJedi.setText("");
        kolory.setSelectedIndex(1);
        poziomMocy.setValue(155);
        strona = "ciemny";

        assertFalse(stworzJedi());
    }

    /**
     * Sprawdza poprawne tworzenie Rycerza Jedi
     */

    @Test
    public void tworzenieJediDobry () {

        inputNazwaJedi.setText("Borys");
        kolory.setSelectedIndex(1);
        poziomMocy.setValue(155);
        strona = "ciemny";

        assertTrue(stworzJedi());
    }

    /**
     * Sprawdza tworzenie Zakonu Jedi z za długa nazwa
     */


    @Test
    public void tworzenieZakonuDlugi () {

        listaAdeptow = new ArrayList<Jedi>();
        inputNazwaZak.setText("Wielkie miecze swietlne niszczace kosmos");


        assertFalse(stworzZakon());
    }

    /**
     * Sprawdza tworzenie Zakonu Jedi z za krotka nazwa
     */

    @Test
    public void tworzenieZakonuKrotki () {

        listaAdeptow = new ArrayList<Jedi>();
        inputNazwaZak.setText("");

        assertFalse(stworzZakon());
    }

    /**
     * Sprawdza poprawne tworzenie Zakonu
     */

    @Test
    public void tworzenieZakonu () {

        listaAdeptow = new ArrayList<Jedi>();
        inputNazwaZak.setText("Ludzie Borysa");

        assertTrue(stworzZakon());
    }

    /**
     * Przystosowane metody tworzace Jedi i Zakony
     */

    public boolean stworzJedi() {
        if (Jedi.czyJediIstnieje(inputNazwaJedi.getText())) {
            JOptionPane.showMessageDialog(null,"Rycerz Jedi już istneje w bazie!", "Błąd rejstracji", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (inputNazwaJedi.getText().length() < 1 || inputNazwaJedi.getText().length() > 20) {
            JOptionPane.showMessageDialog(null,"Zła nazwa Rycerza Jedi!", "Błąd rejstracji", JOptionPane.WARNING_MESSAGE);
            return false;
        } else
            try {
                return true;
                //Tworzy Jedi
            } catch (NullPointerException ex){
                JOptionPane.showMessageDialog(null,"Źle zdefiniowany rycerz Jedi!", "Błąd rejstracji", JOptionPane.WARNING_MESSAGE);
                return false;
            }
    }

    public boolean stworzZakon() {
        Connection connection = null;

        if (inputNazwaZak.getText().isEmpty() || inputNazwaZak.getText().length() > 20) {
            JOptionPane.showMessageDialog(null,"Brak lub niewłaściwa nazwa zakonu!", "Błąd rejstracji", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (Zakon.czyZakonIstnieje(inputNazwaZak.getText())) {
            JOptionPane.showMessageDialog(null,"Zakon o takiej nazwie już istnieje!", "Błąd rejstracji", JOptionPane.WARNING_MESSAGE);
            return false;
        } else {
            return true;
            //Tworzy zakon
        }
    }

}
