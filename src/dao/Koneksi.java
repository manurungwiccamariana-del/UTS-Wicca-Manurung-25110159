package dao;
import java.sql.*;
import javax.swing.JOptionPane;
public class Koneksi {
    public static Connection getConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/db_inventori","root","");
            return conn;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Koneksi Gagal: "+e.getMessage());
            return null;
        }
    }
}