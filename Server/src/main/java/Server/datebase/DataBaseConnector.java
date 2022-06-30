package Server.datebase;


import com.jcraft.jsch.Session;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DataBaseConnector {

    private static String dbUser;
    private static String dbPassword;
    private static Connection con;

    private static Session session;

    public static void connect() {
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/studs",dbUser,dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void init() {
        initFields();

    }

    public static void disconnect() {
        session.disconnect();
    }

    private static void initFields() {

//todo
        dbUser = PropertyManager.getProperty("dbUser");
        dbPassword = PropertyManager.getProperty("dbPassword");
    }

    public static Connection getConnection() {
         connect();
        return con;
    }
}