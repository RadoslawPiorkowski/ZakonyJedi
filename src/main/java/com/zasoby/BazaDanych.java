package com.zasoby;

import java.sql.*;

public abstract class BazaDanych {


    public static String urlBazy = "jdbc:postgresql:Jedi";
    public static String uzytkownik = "postgres";
    public static String hasloBazy = "425@hejBudowa";

    public static Statement getStatmentZBazyDanych() throws SQLException {

        Connection connection = DriverManager.getConnection(urlBazy, uzytkownik, hasloBazy);

        return connection.createStatement();
    }

}
