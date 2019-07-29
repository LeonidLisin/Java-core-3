package Lesson_6.Server;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class AuthRegService {

    private static Connection connection;
    private static Statement stmt;
    private static Logger logger;


    public static void connect() throws SQLException {
        try {
            // обращение к драйверу
            Class.forName("org.sqlite.JDBC");
            // установка подключения
            connection = DriverManager.getConnection("jdbc:sqlite:DBUsers.db");
            // создание Statement для возможности оправки запросов
            stmt = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getNickByLoginAndPass(String login, String pass) {
        // формирование запроса
        String sql = String.format("SELECT nickname FROM main where login = '%s' and password = '%s'", login, pass);

        try {
            // оправка запроса и получение ответа
            ResultSet rs = stmt.executeQuery(sql);

            // если есть строка возвращаем результат если нет то вернеться null
            if(rs.next()) {
                return rs.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean isNickAndLoginFree (String login, String nick){
        String sql = "SELECT login, nickname FROM main";

        try {
            // оправка запроса и получение ответа
            ResultSet rs = stmt.executeQuery(sql);

            // если ник или пароль занят, возвращаем false
            while(rs.next()) {
                String l = rs.getString("login");
                String n = rs.getString("nickname");
                if ((l.equals(login) || n.equals(nick))) return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void regNewUser(String login, String pass, String nick){
        String sql1 = String.format("INSERT INTO main (login, password, nickname) VALUES ('%s', '%s', '%s')", login, pass, nick);
        String sql2 = String.format("CREATE TABLE '%s' ('nick' CHAR(64) NOT NULL)", nick);

        try {
            stmt.executeUpdate(sql1);
            stmt.execute(sql2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addToBlackList(String nick, String nickToBan){
        String sql = String.format("INSERT INTO '%s' (nick) VALUES ('%s')", nick, nickToBan);

        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveToDB (String nick, String msg){
        String sql = String.format("INSERT INTO history (nick, message) VALUES ('%s', '%s')", nick, msg);
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String[]> readHistoryFromDB(){

        ArrayList<String[]> history = new ArrayList<>();
        String sql = "SELECT nick, message FROM history";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                String[] h = new String[2];
                h[0] = rs.getString("nick");
                h[1] = rs.getString("message");
                history.add(h);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return history;
    }

    public static ArrayList<String> getBannedList(String nick){
        ArrayList<String> bannedList = new ArrayList<>();
        String sql = String.format("SELECT * FROM '%s'", nick);

        try {
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                bannedList.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bannedList;
    }


    public static void disconnect() {
        try {
            // закрываем соединение
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
