package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//DB 연결 및 자원 정리 유틸
//SQLite DB 연결/종료 유틸리티(Connection getConnection() 등)
public class DbUtil {
    private static Connection con=null;

    public static Connection getConnection() {
        if (con == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                con= DriverManager.getConnection("jdbc:sqlite:target/mymenulist.db");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return con;
    }

    public static void closeConnection() {
        if (con != null) {
            try{
                con.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

}
