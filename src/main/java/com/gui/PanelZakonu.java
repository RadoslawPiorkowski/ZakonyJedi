package com.gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class PanelZakonu extends JPanel {

    JFrame ramkaGlowna;
    JTextField jediPath;
    JTextField plikZakonow;
    JButton importJedi;
    JButton importZakonow;


    public PanelZakonu(JFrame ramka) {
        this.ramkaGlowna = ramka;

        setLayout(null);

        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.VERTICAL);
        separator.setBounds(415, 10, 5, 750);
        add(separator);

        JLabel zakony = new JLabel("Zakony Jedi");
        zakony.setBounds(160, 20, 100, 25);
        add(zakony);

        JTextArea listaZakonow = new JTextArea();
        listaZakonow.setBounds(20, 50, 360, 300);
        listaZakonow.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(listaZakonow);

        JLabel rejestracjaZakonu = new JLabel("Rejestracja Zakonu Jedi");
        rejestracjaZakonu.setBounds(135, 355,200, 25);
        add(rejestracjaZakonu);

        JLabel nazwa = new JLabel("Nazwa:");
        nazwa.setBounds(30, 385, 100, 25);
        add(nazwa);

        JTextField inputNazwaZak = new JTextField();
        inputNazwaZak.setBounds(150, 385, 220, 25);
        add(inputNazwaZak);

        JButton wybierz = new JButton("Wybierz");
        wybierz.setBounds(30, 420, 100, 25);
        add(wybierz);

        JTextArea dostepniJedi = new JTextArea();
        dostepniJedi.setBounds(150, 420, 220, 200);
        dostepniJedi.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(dostepniJedi);

        importZakonow = new JButton("Import");
        importZakonow.setBounds(30, 620, 100, 25);
        importZakonow.addActionListener(new AkcjaPobrania());
        add(importZakonow);

        JButton eksportZak = new JButton("Eksport");
        eksportZak.setBounds(30, 655, 100, 25);
        add(eksportZak);

        plikZakonow = new JTextField();
        plikZakonow.setBounds(150, 640, 220, 25);
        plikZakonow.setEditable(false);
        add(plikZakonow);

        JButton zarejestrujZak = new JButton("Zarejestruj");
        zarejestrujZak.setBounds(150, 700, 100, 20);
        add(zarejestrujZak);

        JButton wyczyscZak = new JButton("Wyczysc");
        wyczyscZak.setBounds(260, 700, 100, 20);
        add(wyczyscZak);


/////////////////////////////////////////////////////////////////////

        JLabel jedi = new JLabel("Jedi");
        jedi.setBounds(610, 20, 100, 25);
        add(jedi);

        JTextArea listaJedi = new JTextArea();
        listaJedi.setBounds(450, 50, 360, 300);
        listaJedi.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(listaJedi);

        JLabel rejestracjaJedi = new JLabel("Rejestracja Jedi");
        rejestracjaJedi.setBounds(580, 355,200, 25);
        add(rejestracjaJedi);

        JLabel nazwaJedi = new JLabel("Nazwa:");
        nazwaJedi.setBounds(460, 385, 100, 25);
        add(nazwaJedi);

        JTextField inputNazwaJedi = new JTextField();
        inputNazwaJedi.setBounds(580, 385, 220, 25);
        add(inputNazwaJedi);

        JLabel kolorMiecza = new JLabel("Kolor miecza:");
        kolorMiecza.setBounds(460, 425, 100, 25);
        add(kolorMiecza);

        JComboBox<String> kolory = new JComboBox<String>();
        kolory.addItem("Zielony");
        kolory.addItem("Niebieski");
        kolory.addItem("Fioletowy");
        kolory.addItem("Czerwony");
        kolory.setBounds(580, 425, 220, 25);
        add(kolory);

        JLabel moc = new JLabel("Moc:");
        moc.setBounds(460, 495, 100, 25);
        add(moc);

        JSlider poziomMocy = new JSlider(JSlider.HORIZONTAL, 0,1000,100);
        poziomMocy.setMinorTickSpacing(50);
        poziomMocy.setMajorTickSpacing(200);
        poziomMocy.setPaintLabels(true);
        poziomMocy.setPaintTicks(true);
        poziomMocy.setBounds(580,495,220,50);
        add(poziomMocy);

        JLabel stronaMocy = new JLabel("Strona mocy:");
        stronaMocy.setBounds(460, 580, 100,25);
        add(stronaMocy);

        JRadioButton ciemna = new JRadioButton("ciemna");
        ciemna.setBounds(580, 580, 100, 25);
        add(ciemna);

        JRadioButton jasna = new JRadioButton("jasna");
        jasna.setBounds(690, 580, 100, 25);
        add(jasna);

        ButtonGroup strona = new ButtonGroup();
        strona.add(ciemna);
        strona.add(jasna);

        importJedi = new JButton("Import");
        importJedi.setBounds(460, 620, 100, 25);
        importJedi.addActionListener(new AkcjaPobrania());
        add(importJedi);

        JButton eksportJedi = new JButton("Eksport");
        eksportJedi.setBounds(460, 655, 100, 25);
        add(eksportJedi);

        jediPath = new JTextField();
        jediPath.setBounds(580, 640, 220, 25);
        jediPath.setEditable(false);
        add(jediPath);

        JButton zarejestrujJedi = new JButton("Zarejestruj");
        zarejestrujJedi.setBounds(580, 700, 100, 20);
        add(zarejestrujJedi);

        JButton wyczyscJedi = new JButton("Wyczysc");
        wyczyscJedi.setBounds(690, 700, 100, 20);
        add(wyczyscJedi);

    }




    class AkcjaPobrania implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Object o = e.getSource();

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File plik = fileChooser.getSelectedFile();
                if (o == importJedi) {
                    jediPath.setText(plik.getPath());
                }else if(o == importZakonow) {
                    plikZakonow.setText(plik.getPath());
                }
            }



        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(830, 770);

    }


}