package com.zasoby;

import java.util.ArrayList;

public class Szfrowanie {

    public static String szyfrowanieJedi (ArrayList<Jedi> lista ) {
        String zakodowane = "";
        String[] jedi = new String[5];

        for (Jedi j: lista){
            jedi[0] = j.getImie();
            jedi[1] = j.getKolorMiecza();
            jedi[2] = j.getPoziomMocy() + "";
            jedi[3] = j.getStronaMocy();
            jedi[4] = j.getIdZakonu() + "";

            for (int i = 0; i < jedi.length; i++) {
                zakodowane += szyfrowanieTekstu(jedi[i]) + "\t";
            }

            zakodowane += "\n";
        }

        return zakodowane;
    }
    
    public static String szyfrowanieZakonu (ArrayList<Zakon> lista) {
        String zakodowane = "";
        String[] zakon = new String[3];

        for (Zakon z: lista){
            zakon[0] = z.getIdZakonu() + "";
            zakon[1] = z.getNazwa();
            zakon[2] = z.getIloscCzlonkow() + "";

            for (int i = 0; i < zakon.length; i++) {
                zakodowane += szyfrowanieTekstu(zakon[i]) + "\t";
            }

            zakodowane += "\n";
        }

        return zakodowane;
    }


    public static String szyfrowanieTekstu (String tekst) {
        String szyfr = "";

        for (int i = 0; i < tekst.length(); i++) {
            if (tekst.charAt(i) == '\t')
                szyfr += '\t';
            else if (tekst.charAt(i) == 10)
                szyfr += 10;
            else
                szyfr += (char) (tekst.charAt(i) - 4);
        }

        return szyfr;
    }


    public static String deszyfrowanieTekstu (String tekst) {
        String antySzyfr = "";

        for (int i = 0; i < tekst.length(); i++) {
            if (tekst.charAt(i) == '\t')
                antySzyfr += ' ';
            else if (tekst.charAt(i) == 10)
                antySzyfr += 10;
            else
                antySzyfr += (char) (tekst.charAt(i) + 4);
        }
        return antySzyfr;
    }

}



