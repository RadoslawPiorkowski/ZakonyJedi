package com.zasoby;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Zakon {

    private static List<Zakon> listaZakonow = new ArrayList<Zakon>();

    static {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql:Jedi", "postgres", "425@hejBudowa");
            Statement statement = connection.createStatement();
            ResultSet data = statement.executeQuery("SELECT * FROM Zakony");

            while (data.next())
                new Zakon(data.getInt("ID_Zakonu"), data.getString("Nazwa"), data.getInt("Ilosc_Czlonkow"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int idZakonu;
    private String nazwa;
    private int iloscCzlonkow;

    public Zakon(int idZakonu, String nazwa, int iloscCzlonkow) {
        this.idZakonu = idZakonu;
        this.nazwa = nazwa;
        this.iloscCzlonkow = iloscCzlonkow;
        listaZakonow.add(this);
    }

    public int getIdZakonu() {
        return idZakonu;
    }

    public String getNazwa() {
        return nazwa;
    }

    public int getIloscCzlonkow() {
        return iloscCzlonkow;
    }

    public static String getListaZakonow() {

        String zakony = "";

        for (int i = 1; i < listaZakonow.size(); i++) {
            zakony += listaZakonow.get(i).getIdZakonu() - 1 + "# " + listaZakonow.get(i).getNazwa() + " członków: " + listaZakonow.get(i).getIloscCzlonkow() + "\n";
        }
        return zakony;
    }

    public static void wyczyscListeZakonow() {
        if (listaZakonow.size() > 0)
            listaZakonow.subList(0, listaZakonow.size()).clear();
    }

    public static boolean czyZakonIstnieje(String nazwa) {
        for (Zakon z: listaZakonow)
            if (z.getNazwa().equals(nazwa)) {
                return true;
            }
        return false;
    }

    public static  int getNastepneID () {
        return listaZakonow.size();
    }

}
