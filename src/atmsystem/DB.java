package atmsystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DB {

    private static String url;
    private static String user;
    private static String password;
    private static DB instance;

    private DB() throws IOException {
        load_db_info();
    }

    private void load_db_info() throws IOException {
        Properties properties = new Properties();

        FileInputStream fis = new FileInputStream("resources/config.properties");

        properties.load(fis);   

        url = properties.getProperty("db.url");
        user = properties.getProperty("db.username");
        password = properties.getProperty("db.password");
    }

    public static DB get_instance() throws IOException{
        if (instance == null) {
            instance = new DB();
        }

        return instance;
    }
    
    public Connection get_connection() throws SQLException{
        Connection conn = DriverManager.getConnection(url, user, password);
        
        return conn;
    }
    
    

}
