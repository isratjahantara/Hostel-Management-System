package labproject;

import com.mysql.jdbc.Connection;
import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mddilshad
 * 
 */
public class DBConn {
    Connection con;
    PreparedStatement statement;
    Statement stmt;
    boolean flag = false;
    DBConn(){
        flag = false;
        con = null;
        statement = null;
        stmt = null;
    }
    
    void reset(){
        flag = false;
        con = null;
        statement = null;
        stmt = null;
    }
    void createConn(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://192.168.10.10:3306/mddilsh3_hostel", "homestead", "secret");
            System.out.println("Database connection successful");
            flag = true;
        }
        catch(ClassNotFoundException | SQLException ex){
            System.out.println("database not connenected");
        }
    }
    
    boolean adminLoginCheck(String id,String password){
        createConn();
        boolean f = false;
        try{
            String query = "SELECT id,cast( aes_decrypt(password,'admin') as char(150)) as password FROM mddilsh3_hostel.admin";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                
                    System.out.println("here");
                if(id.equals(Integer.toString(rs.getInt("id"))) && password.equals(rs.getString("password"))){
                    f = true;
                }
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
            System.out.println("database not connenected");
        }
        reset();
        return f;
    }
    
    
    boolean customerLoginCheck(String gmail,String password){
        createConn();
        boolean f = false;
        try{
            String query = "SELECT name,email,cast( aes_decrypt(password,'admin') as char(150)) as password,image_dir,addmissionDate FROM mddilsh3_hostel.customer";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                
                    System.out.println("here");
                if(gmail.equals(rs.getString("email")) && password.equals(rs.getString("password"))){
                    f = true;
                }
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
            System.out.println("database not connenected");
        }
        reset();
        return f;
    }
    
    String customerPicLocation(String gmail,String password){
        createConn();
        String f = "";
        try{
            String query = "SELECT name,email,cast( aes_decrypt(password,'admin') as char(150)) as password,image_dir,addmissionDate FROM mddilsh3_hostel.customer";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                if(gmail.equals(rs.getString("email")) && password.equals(rs.getString("password"))){
                    f = rs.getString("image_dir");
                }
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        reset();
        return f;
    }
    
    String getCustomerName(String gmail,String password){
        createConn();
        String f = "";
        try{
            String query = "SELECT name,email,cast( aes_decrypt(password,'admin') as char(150)) as password,image_dir,addmissionDate FROM mddilsh3_hostel.customer";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                if(gmail.equals(rs.getString("email")) && password.equals(rs.getString("password"))){
                    f = rs.getString("name");
                }
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        reset();
        return f;
    }
    
    String getCustomerEmail(String gmail,String password){
        createConn();
        String f = "";
        try{
            String query = "SELECT name,email,cast( aes_decrypt(password,'admin') as char(150)) as password,image_dir,addmissionDate FROM mddilsh3_hostel.customer";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                if(gmail.equals(rs.getString("email")) && password.equals(rs.getString("password"))){
                    f = rs.getString("email");
                }
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        reset();
        return f;
    }
    String getCustomerDate(String gmail,String password){
        createConn();
        String f = "";
        try{
            String query = "SELECT name,email,cast( aes_decrypt(password,'admin') as char(150)) as password,image_dir,addmissionDate FROM mddilsh3_hostel.customer";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                if(gmail.equals(rs.getString("email")) && password.equals(rs.getString("password"))){
                    f = rs.getString("addmissionDate");
                }
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        reset();
        return f;
    }
    
    void insertIntoCusTable(String name,String gmail,String password,String pic){
        createConn();
        String query = "";
        System.out.println(password);
        if(pic == null){
            System.out.println("oh no pic was not found");
        }
        if(pic != null){
            System.out.println("here pic is not null");
            query = "insert into mddilsh3_hostel.customer(name,password,email,image_dir,addmissionDate)values('"+name+"',aes_encrypt('"+(String)password+"','admin'),'"+gmail+"','"+pic+"',CURDATE());";
            System.out.println(query);
        }
        if(flag == true && !"".equals(query)){
            try{
                stmt = con.createStatement();
                stmt.executeUpdate(query);
                System.out.println("here after executing query");
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }else{
            if(flag == true){
                System.out.println("photo upload error");
            }
        }
        reset();
    }
    void clearNotices(){
        createConn();
        try{
            String query = "delete from mddilsh3_hostel.notices";
            PreparedStatement st = con.prepareStatement(query);
            st.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        reset();
    }
    JTable addCustomerRowsInAdmin(){
        createConn();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Name", "Email", "Image_Dir"}, 0);
        try{
            String query = "Select name,email,image_dir from mddilsh3_hostel.customer";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            model.addRow(new Object[]{"name","email","image_location"});
            while(rs.next()){
                String n = rs.getString("name");
                String e = rs.getString("email");
                String i = rs.getString("image_dir");
                model.addRow(new Object[]{n,e,i});
            }
        
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        JTable t;
        t = new JTable();
        t.setModel(model);
        return t;
        
    }
    void insertIntoNotice(String s){
        createConn();
        String query = "insert into mddilsh3_hostel.notices(msg,date) values('"+s+"',CURDATE())";
        try{
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("here after executing query");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        reset();
    }
    void viewNotices(JPanel p){
        createConn();
        String query = "select date,msg from mddilsh3_hostel.notices";
         try{
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(!rs.next()){
                p.add(new JLabel("**************No notice Today.*************"));
            }
            while(rs.next()){
                
                String n = rs.getString("date");
                String e = rs.getString("msg");
                p.add(new JLabel("Date: "+n+"\n"));
                p.add(new JLabel("Notice:\t"+ e));
                p.add(new JLabel("\n\n\n"));
                      
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        reset();
    }
    void deleteCustomer(String name,String gmail){
        createConn();
        try{
            String query = "delete from mddilsh3_hostel.customer where name like '"+name+"' and email like '"+gmail+"' ;";
            PreparedStatement st = con.prepareStatement(query);
            st.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        reset();
    }
    
}
