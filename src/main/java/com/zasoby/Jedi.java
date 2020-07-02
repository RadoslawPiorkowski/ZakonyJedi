package com.zasoby;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Jedi {

    public static ArrayList<Jedi> listaJedi = new ArrayList<Jedi>();

    static {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql:Jedi", "postgres", "425@hejBudowa");
            Statement statement = connection.createStatement();
            ResultSet data = statement.executeQuery("SELECT * FROM Jedi");

            while (data.next())
                new Jedi(data.getString("Imie"), data.getString("Kolor_Miecza"), data.getInt("Poziom_Mocy"), data.getString("Strona_Mocy"), data.getInt("Zakon_ID"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String imie;
    private String kolorMiecza;
    private int poziomMocy;
    private String stronaMocy;
    private int idZakonu;

    public Jedi(String imie, String kolorMiecza, int poziomMocy, String stronaMocy, int idZakonu) {
        this.imie = imie;
        this.kolorMiecza = kolorMiecza;
        this.poziomMocy = poziomMocy;
        this.stronaMocy = stronaMocy;
        this.idZakonu = idZakonu;
        listaJedi.add(this);
    }


    public String getImie() {
        return imie;
    }

    public String getKolorMiecza() {
        return kolorMiecza;
    }

    public int getPoziomMocy() {
        return poziomMocy;
    }

    public String getStronaMocy() {
        return stronaMocy;
    }

    public int getIdZakonu() {
        return idZakonu;
    }

    public void setIdZakonu(int idZakonu) {
        this.idZakonu = idZakonu;
    }

    public static String getListaJedi() {
        String wszyscyJedi = "";

        for (int i = 0; i < listaJedi.size(); i++)
           wszyscyJedi += listaJedi.get(i).getImie() + ", miecz: " + listaJedi.get(i).getKolorMiecza()
                    + ", moc: " + listaJedi.get(i).getPoziomMocy() + "-" + listaJedi.get(i).getStronaMocy()
                    + ", zakon: " + (listaJedi.get(i).getIdZakonu() - 1) +"\n";

        return wszyscyJedi;

    }

    public static ArrayList<Jedi> getJediBezZakonu(){
        ArrayList<Jedi> jediBezZakonu = new ArrayList<Jedi>();

        for (int i = 0; i < listaJedi.size(); i++)
            if (listaJedi.get(i).getIdZakonu() == 1) {
                jediBezZakonu.add(listaJedi.get(i));
            }

        return jediBezZakonu;
    }

    @Override
    public String toString() {
        return imie + ", " + kolorMiecza
                + ", " + poziomMocy + " - " + stronaMocy+ "\n";
    }

    public static boolean czyJediIstnieje(String imie) {
        for (Jedi j: listaJedi)
            if (j.getImie().equals(imie)) {
                return true;
            }
        return false;
    }
}
