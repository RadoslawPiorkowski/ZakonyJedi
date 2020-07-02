package com.gui;

import com.zasoby.BazaDanych;
import com.zasoby.Jedi;
import com.zasoby.Szfrowanie;
import com.zasoby.Zakon;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class PanelZakonu extends JPanel {

    ArrayList<Jedi> listaAdeptow = new ArrayList<Jedi>();

    JFrame ramkaGlowna;

    JTextArea listaZakonow;
    JTextField inputNazwaZak;
    JList<Jedi> dostepniJedi;
    DefaultListModel<Jedi> model;
    JButton importZakonow;
    JButton eksportZak;
    JTextField plikZakonow;

    JTextArea listaJedi;
    JTextField inputNazwaJedi;
    JComboBox<String> kolory;
    JSlider poziomMocy;
    ButtonGroup strona;
    JButton importJedi;
    JButton eksportJedi;
    JTextField jediPath;



    public PanelZakonu(JFrame ramka) {
        this.ramkaGlowna = ramka;

        setLayout(null);

        //JTextArea
        listaZakonow = Budowa.stworzTextArea(ramka,20,50,360,300);
        listaJedi = Budowa.stworzTextArea(ramka, 450,50,360,300);
        wypiszZakony();
        wypiszJedi();

        //JLabele
        Budowa.stworzJLabel(ramka, "Zakony Jedi", 160, 20, 100, 25);
        Budowa.stworzJLabel(ramka, "Rejestracja Zakonu Jedi", 135,355,200,25);
        Budowa.stworzJLabel(ramka, "Nazwa:", 30,385,100,25);
        Budowa.stworzJLabel(ramka, "Jedi", 610,20,100,25);
        Budowa.stworzJLabel(ramka, "Rejestracja Jedi", 580,355,200,25);
        Budowa.stworzJLabel(ramka, "Nazwa:", 460,385,100,25);
        Budowa.stworzJLabel(ramka, "Kolor miecza:",460,425,100,25 );
        Budowa.stworzJLabel(ramka, "Moc:", 460,495,100,25);
        Budowa.stworzJLabel(ramka, "Strona mocy:", 460,580,100,25);

        //JTextField
        inputNazwaZak = Budowa.stworzTextField(ramka, 150,385,220,25);
        plikZakonow = Budowa.stworzTextFieldBezEdycji(ramka,150,640,220,25);
        inputNazwaJedi = Budowa.stworzTextField(ramka,580,385,220,25);
        jediPath = Budowa.stworzTextFieldBezEdycji(ramka,580,640,220,25);

        //JButton
        Budowa.stworzButton(ramka, "Wybierz", 30, 420, 100,25, new DodanieZakonu());
        importZakonow = Budowa.stworzButton(ramka, "Import", 30,620,100,25,new AkcjaOdczytuZPliku(), true);
        eksportZak = Budowa.stworzButton(ramka, "Eksport", 30,655,100,25, new AkcjaZapisuDoPliku(), true);
        Budowa.stworzButton(ramka, "Zarejestruj", 150,700,100,20, new StworzZakon());
        Budowa.stworzButton(ramka, "Wyczysc", 260,700,100,20, new WyczyscZakon());
        importJedi = Budowa.stworzButton(ramka,"Import",460,620,100,25, new AkcjaOdczytuZPliku(), true);
        eksportJedi = Budowa.stworzButton(ramka,"Eksport",460,655,100,25,new AkcjaZapisuDoPliku(), true);
        Budowa.stworzButton(ramka, "Zarejestruj", 500,700,100,20, new RejestracjaJedi());
        Budowa.stworzButton(ramka, "Wyczysc", 690,700,100,20, new WyczyscJedi());

        //JSeparator
        Budowa.stworzSeparatorPionowy(ramka,415,10,5,750);

        //JSlider
        poziomMocy = Budowa.stworzSlider(ramka,580,495,220,50,50,200);

        //JButtonGrop
        strona = new ButtonGroup();
        Budowa.JRadioButton(ramka, "ciemna",580,580,100,25, strona);
        Budowa.JRadioButton(ramka, "jasna",690,580,100,25, strona);

        //JComboBox
        kolory = Budowa.stworzComboKolorow(ramka, 580,425,220,25);

        //JList
        dostepniJedi = new JList<Jedi>();
        dostepniJedi.setBounds(150, 420, 220, 200);
        dostepniJedi.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        model = new DefaultListModel<>();
        getDostepniJedi();
        dostepniJedi.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        add(dostepniJedi);
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(830, 770);
    }


    class DodanieZakonu implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                listaAdeptow.add(dostepniJedi.getSelectedValue());
                dostepniJedi.getSelectedValue().setIdZakonu(Zakon.getNastepneID());
                if (!model.isEmpty())
                    model.remove(dostepniJedi.getSelectedIndex());
                wypiszJedi();
                powolajJediDoBazy();
            } catch (NullPointerException ex) {
                System.out.println("Nie wybrano Jedi");
            }
        }
    }


    class AkcjaOdczytuZPliku implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Object o = e.getSource();

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File plik = fileChooser.getSelectedFile();
                if (o == importJedi) {
                    jediPath.setText(plik.getPath());
                    try {
                        odczytJediZPliku();
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                } else if(o == importZakonow) {
                    plikZakonow.setText(plik.getPath());
                    try {
                        odczytZakonowZPliku();
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }


    class AkcjaZapisuDoPliku implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Object o = e.getSource();

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File plik = fileChooser.getSelectedFile();
                if (o == eksportJedi) {
                    jediPath.setText(plik.getPath());
                    try {
                        zapisDoPlikuJedi();
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                } else if(o == eksportZak) {
                    plikZakonow.setText(plik.getPath());
                    try {
                        zapisDoPlikuZakonow();
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        }
    }


    class StworzZakon implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            if (inputNazwaZak.getText().isEmpty() || inputNazwaZak.getText().length() > 20) {
                JOptionPane.showMessageDialog(null,"Brak lub niewłaściwa nazwa zakonu!", "Błąd rejstracji", JOptionPane.WARNING_MESSAGE);
            } else if (Zakon.czyZakonIstnieje(inputNazwaZak.getText())) {
                JOptionPane.showMessageDialog(null,"Zakon o takiej nazwie już istnieje!", "Błąd rejstracji", JOptionPane.WARNING_MESSAGE);
            } else
                try {
                    BazaDanych.getStatmentZBazyDanych().execute("INSERT INTO Zakony (Nazwa, Ilosc_Czlonkow) VALUES " +
                            "('" + inputNazwaZak.getText() + "', " + listaAdeptow.size() + ");");

                    Zakon.wyczyscListeZakonow();
                    ResultSet data = BazaDanych.getStatmentZBazyDanych().executeQuery("SELECT * FROM Zakony");
                    while (data.next())
                        new Zakon(data.getInt("ID_Zakonu"), data.getString("Nazwa"), data.getInt("Ilosc_Czlonkow"));

                    wypiszZakony();
                    powolajJediDoBazy();
                    inputNazwaZak.setText("");

                    if (listaAdeptow.size() > 0)
                        listaAdeptow.subList(0, listaAdeptow.size()).clear();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }


    class WyczyscZakon implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            inputNazwaZak.setText(null);

            for (Jedi j : listaAdeptow)
                j.setIdZakonu(1);

            if (listaAdeptow.size() > 1)
                listaAdeptow.subList(1, listaAdeptow.size()).clear();

            wypiszJedi();
            getDostepniJedi();
            powolajJediDoBazy();
        }
    }



    class RejestracjaJedi implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (Jedi.czyJediIstnieje(inputNazwaJedi.getText())) {
                JOptionPane.showMessageDialog(null,"Rycerz Jedi już istneje w bazie!", "Błąd rejstracji", JOptionPane.WARNING_MESSAGE);
            } else if (inputNazwaJedi.getText().length() < 1 || inputNazwaJedi.getText().length() > 20) {
                JOptionPane.showMessageDialog(null,"Zła nazwa Rycerza Jedi!", "Błąd rejstracji", JOptionPane.WARNING_MESSAGE);
            } else
                try {
                    new Jedi(inputNazwaJedi.getText(), Objects.requireNonNull(kolory.getSelectedItem()).toString(), poziomMocy.getValue(), strona.getSelection().getActionCommand(), 1);
                    wypiszJedi();
                    getDostepniJedi();
                    powolajJediDoBazy();
                    inputNazwaJedi.setText("");
                } catch (NullPointerException ex){
                    JOptionPane.showMessageDialog(null,"Źle zdefiniowany rycerz Jedi!", "Błąd rejstracji", JOptionPane.WARNING_MESSAGE);
                }
        }
    }

    class WyczyscJedi implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            inputNazwaJedi.setText(null);
            kolory.setSelectedIndex(0);
            poziomMocy.setValue(0);
            strona.clearSelection();
        }
    }


    public void wypiszZakony () {
        listaZakonow.setText(Zakon.getListaZakonow());
    }

    public void wypiszJedi() {
        listaJedi.setText(Jedi.getListaJedi());
    }

    public void getDostepniJedi() {
        model.removeAllElements();
        dostepniJedi.setModel(model);
        for (Jedi j : Jedi.getJediBezZakonu())
            model.addElement(j);
    }

    public void powolajJediDoBazy () {

            try {
                BazaDanych.getStatmentZBazyDanych().execute("DELETE FROM JEDI");

                for (Jedi j : Jedi.listaJedi)
                    BazaDanych.getStatmentZBazyDanych().execute("INSERT INTO Jedi (Imie, Kolor_Miecza, Poziom_Mocy, Strona_Mocy, Zakon_ID) VALUES "
                            + "('" + j.getImie() + "' , '" + j.getKolorMiecza() + "', " + j.getPoziomMocy() + ", '" + j.getStronaMocy() + "', " + j.getIdZakonu() + ");");

            } catch (SQLException e) {
                e.printStackTrace();
            }
            wypiszJedi();
            getDostepniJedi();
    }


    public void odczytZakonowZPliku () throws FileNotFoundException {

        File file = new File(plikZakonow.getText());
        Scanner scanner = new Scanner(file);
        String lista = "";

        while (scanner.hasNextLine()) {
            lista += (Szfrowanie.deszyfrowanieTekstu(scanner.nextLine()));
            lista += "\n";
        }
        listaZakonow.setText(lista);
        scanner.close();
    }

    public void zapisDoPlikuZakonow () throws FileNotFoundException {

        File file = new File(plikZakonow.getText());
        PrintWriter zapis = new PrintWriter(file);

        zapis.print(Szfrowanie.szyfrowanieZakonu(Zakon.getLista()));

        zapis.close();
    }


    public void odczytJediZPliku () throws FileNotFoundException {

        File file = new File(jediPath.getText());
        Scanner scanner = new Scanner(file);
        String lista = "";

        while (scanner.hasNextLine()) {
            lista += (Szfrowanie.deszyfrowanieTekstu(scanner.nextLine()));
            lista += "\n";
        }
        listaJedi.setText(lista);
        scanner.close();
    }


    public void zapisDoPlikuJedi () throws FileNotFoundException {

        File file = new File(jediPath.getText());
        PrintWriter zapis = new PrintWriter(file);

        zapis.print(Szfrowanie.szyfrowanieJedi(Jedi.listaJedi));

        zapis.close();
    }
}