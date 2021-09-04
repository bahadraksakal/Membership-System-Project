package com.usersPackage;

import com.sun.xml.rpc.wsdl.document.schema.BuiltInTypes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;


public class DB_user {
    
    private static final String username="root";
    private static final String passworld="*******";
    private static final String dbUrl="jdbc:mysql://localhost:3306/database_tablo?useSSL=false";
    private static ResultSet resultSet;
    private static boolean flagUyari=false;
    
    private static Connection DB_user_CONNECT() throws SQLException{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");            
        } catch (ClassNotFoundException ex) {
            System.out.println("class not found hatası aldık");;
        }
        return DriverManager.getConnection(dbUrl,username,passworld);
    }
    public static void showError(SQLException exception){
        System.out.println("Error helper: "+ exception.getMessage());
        System.out.println("Error Code helper: "+ exception.getErrorCode());
    }
    public static boolean set_DB_users(String username_n , String passworld_n , String mail_n){
        Connection connection;
        PreparedStatement statement; 
        ResultSet resultSet_2;
        try {
            connection=DB_user_CONNECT();
            String komut ="INSERT INTO `database_tablo`.`uye` (`usarname`, `passworld`, `email`) VALUES (?,?,?);";
            statement=connection.prepareStatement(komut);
            statement.setString(1, username_n);
            statement.setString(2, passworld_n);
            statement.setString(3,mail_n);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
            return true;
        }
        return false;
    }
    public static String get_DB_users_validation(String username , String passworld){
        
        Connection connection;
        PreparedStatement statement;        
        boolean onayFlag=false;
        try {
            connection = DB_user_CONNECT();
            String komut = "SELECT * FROM uye WHERE usarname = ? AND passworld = ?";
            statement=connection.prepareStatement(komut);
            statement.setString(1, username);
            statement.setString(2, passworld);
            resultSet=statement.executeQuery();
            if (resultSet.next()) {
                onayFlag=true;
                flagUyari=false;
                
            }else{
                onayFlag=false;
                resultSet=null;
                flagUyari=true;
            } 
        } catch (SQLException e) {
            showError(e);
        }
        
        return (onayFlag? "uye_page":"index");
    }

    public static boolean isFlagUyari() {
        return flagUyari;
    }

    public static void setFlagUyari(boolean flagUyari) {
        DB_user.flagUyari = flagUyari;
    }
    public static ResultSet getDB_USERS(){
        
        return resultSet;
    }
    public static String kill(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index";
    }
    public static List<userBean> users_list(){
        List<userBean> users_list = new ArrayList<>();
        Connection connection;
        Statement statement; 
        ResultSet resultSet_2;
        try {
            connection = DB_user_CONNECT();
            String komut = "SELECT * FROM database_tablo.uye";
            statement=connection.createStatement();
            resultSet_2=statement.executeQuery(komut);
            while (resultSet_2.next()) {
                userBean uye = new userBean();
                System.out.println("----------  : "+resultSet_2.getString(2));
                uye.setID(resultSet_2.getInt(1));
                uye.setAd(resultSet_2.getString(2));
                uye.setSifre(resultSet_2.getString(3));
                uye.setMail(resultSet_2.getString(4));
                users_list.add(uye);
            }
        }catch (SQLException e) {
            showError(e);
        }
        return users_list;
    }
    public static void uyeyi_duzenle(int uye_ID_s , String username_s , String passworld_s , String mail_s){
        
        Connection connection;
        PreparedStatement statement;
        try {
            connection = DB_user_CONNECT();
            String komut = "UPDATE `database_tablo`.`uye` SET `email` = ? , `passworld` = ? , `usarname` = ? WHERE (`iduye` = ?);";
            statement=connection.prepareStatement(komut);
            statement.setString(1, mail_s);
            statement.setString(3, username_s);
            statement.setString(2, passworld_s);
            statement.setInt(4, uye_ID_s);      
            statement.executeUpdate();
        }catch (SQLException e) {
            showError(e);
            
        }
        
    }
    public static void uye_delete(int uye_ID_s){
                Connection connection;
        PreparedStatement statement;
        try {
            connection = DB_user_CONNECT();
            String komut = "DELETE FROM `database_tablo`.`uye` WHERE (`iduye` = ?);";
            statement=connection.prepareStatement(komut);
            statement.setInt(1, uye_ID_s);
            statement.executeUpdate();
        }catch (SQLException e) {
            showError(e);            
        }
    }
    
}
