package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class User {
    private int ID;
    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;
    private String role;

    public User() {
    }

    public User(int ID, String name, String surname, String email, String username, String password, String role) {
        this.ID = ID;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) throws Exception {
        if (role.toLowerCase().equals("admin") || role.toLowerCase().equals("zaposlenik") ||
                role.toLowerCase().equals("klijent")) {
            this.role = role.toUpperCase();
        }
        else{
            throw  new Exception("Molim vas unesite ispravnu ulogu korisnika.");
        }
    }

    public static User add(User u){
        try {
            PreparedStatement stmnt = Database.CONNECTION.prepareStatement("INSERT INTO korisnik VALUES (null,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            stmnt.setString(1,u.getName());
            stmnt.setString(2,u.getSurname());
            stmnt.setString(3,u.getEmail());
            stmnt.setString(4,u.getUsername());
            stmnt.setString(5,u.getPassword());
            stmnt.setString(6,u.getRole());
            stmnt.executeUpdate();

            ResultSet rs = stmnt.getGeneratedKeys();
            if(rs.next()){
                u.setID(rs.getInt(1));
            }
            return  u;
        } catch (SQLException e) {
            System.out.println("Neuspješno dodavanje korisnika u bazu: " + e.getMessage());
            return  null;
        }
    }

    public static boolean remove(User u){
        try {
            PreparedStatement stmnt = Database.CONNECTION.prepareStatement("DELETE FROM korisnik WHERE id=?");
            stmnt.setInt(1,u.getID());
            stmnt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Neuspješno brisanje korisnika iz baze: " + e.getMessage());
            return  false;
        }
    }

    public static boolean update(User u){
        try {
            PreparedStatement stmnt = Database.CONNECTION.prepareStatement
                    ("UPDATE korisnik SET ime=?, prezime=?, email=?, korisnicko_ime=?, lozinka=?, uloga=? WHERE id=?");
            stmnt.setString(1,u.getName());
            stmnt.setString(2,u.getSurname());
            stmnt.setString(3,u.getEmail());
            stmnt.setString(4,u.getUsername());
            stmnt.setString(5,u.getPassword());
            stmnt.setString(6,u.getRole());
            stmnt.setInt(7,u.getID());
            stmnt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Neuspješno uređivanje korisnika: " + e.getMessage());
            return  false;
        }
    }

    public static List<User> select(){
        ObservableList<User> users = FXCollections.observableArrayList();
        try {
            Statement stmnt = Database.CONNECTION.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT * FROM korisnik");

            while(rs.next()){
                users.add(new User(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7)
                ));
            }
            return users;
        } catch (SQLException e) {
            System.out.println("Neuspješno izvlačenje korisnika iz baze: " + e.getMessage());
            return users;
        }
    }
    public static List<User> selectByRole(String uloga){
        ObservableList<User> users = FXCollections.observableArrayList();
        try {
            PreparedStatement stmnt = Database.CONNECTION.prepareStatement("SELECT * FROM korisnik WHERE uloga=?");
            stmnt.setString(1, uloga);
            ResultSet rs = stmnt.executeQuery();

            while(rs.next()){
                users.add(new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7)
                ));
            }
            return users;
        } catch (SQLException e) {
            System.out.println("Neuspješno izvlačenje korisnika iz baze: " + e.getMessage());
            return users;
        }
    }

    public static User get (int ID) {
        try {
            PreparedStatement stmnt = Database.CONNECTION.prepareStatement("SELECT * FROM korisnik WHERE id=?");
            stmnt.setInt(1, ID);
            ResultSet rs = stmnt.executeQuery();


            if (rs.next()){
                return new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7)
                );
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Neuspješno izvlačenje korisnika iz baze: " + e.getMessage());
            return null;
        }
    }

}
