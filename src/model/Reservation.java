package model;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import static javax.swing.DropMode.ON;

public class Reservation {
    private int ID;
    private String date;
    private Boolean payed;
    private int mark;
    private String review;
    private int userID;
    private int serviceID;


    public Reservation(int ID, String date, Boolean payed, int mark, String review, int userID, int serviceID) {
        this.ID = ID;
        this.date = date;
        this.payed = payed;
        this.mark = mark;
        this.review = review;
        this.userID = userID;
        this.serviceID = serviceID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getPayed() {
        return payed;
    }

    public void setPayed(Boolean payed) {
        this.payed = payed;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public static Reservation add(Reservation r){
        try {
            PreparedStatement stmnt = Database.CONNECTION.prepareStatement("INSERT INTO rez_termina VALUES (null,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stmnt.setString(1, r.getDate());
            stmnt.setBoolean(2,r.getPayed());
            stmnt.setInt(3,r.getMark());
            stmnt.setString(4,r.getReview());
            stmnt.setInt(5,r.getUserID());
            stmnt.setInt(6,r.getServiceID());
            stmnt.executeUpdate();

            ResultSet rs = stmnt.getGeneratedKeys();
            if(rs.next()){
                r.setID(rs.getInt(1));
            }
            return  r;
        } catch (SQLException e) {
            System.out.println("Neuspješno dodavanje rezervacije u bazu: " + e.getMessage());
            return  null;
        }
    }
    public static boolean remove(Reservation r){
        try {
            PreparedStatement stmnt = Database.CONNECTION.prepareStatement("DELETE FROM rez_termina WHERE id=?");
            stmnt.setInt(1,r.getID());
            stmnt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Neuspješno brisanje rezervacije iz baze: " + e.getMessage());
            return  false;
        }
    }

    public static boolean update(Reservation r){
        try {
            PreparedStatement stmnt = Database.CONNECTION.prepareStatement
                    ("UPDATE rez_termina SET datum=?, placeno=?, ocjena_usluge=?, komentar=?, fk_korisnik=?, fk_uslugaID=? WHERE id=?");
            stmnt.setString(1, r.getDate());
            stmnt.setBoolean(2,r.getPayed());
            stmnt.setInt(3,r.getMark());
            stmnt.setString(4,r.getReview());
            stmnt.setInt(5,r.getUserID());
            stmnt.setInt(6,r.getServiceID());
            stmnt.setInt(7,r.getID());
            stmnt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Neuspješno uređivanje rezervacije: " + e.getMessage());
            return  false;
        }
    }

    public static List<Reservation> select(){
        ObservableList<Reservation> reservations = FXCollections.observableArrayList();
        try {
            Statement stmnt = Database.CONNECTION.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT * FROM rez_termina");

            while(rs.next()){
                reservations.add(new Reservation(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getBoolean(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getInt(7)
                ));
            }
            return reservations;
        } catch (SQLException e) {
            System.out.println("Neuspješno izvlačenje korisnika iz baze: " + e.getMessage());
            return reservations;
        }
    }
    public static List<Reservation> selectById(int id){
        ObservableList<Reservation> reservations = FXCollections.observableArrayList();
        try {
            PreparedStatement stmnt = Database.CONNECTION.prepareStatement("SELECT * FROM rez_termina WHERE fk_korisnik=?");
            stmnt.setInt(1, id);
            ResultSet rs = stmnt.executeQuery();


            while(rs.next()){
                reservations.add(new Reservation(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getBoolean(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getInt(7)
                ));
            }
            return reservations;
        } catch (SQLException e) {
            System.out.println("Neuspješno izvlačenje korisnika iz baze: " + e.getMessage());
            return reservations;
        }
    }

}
