package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;

public class Service {
    private int ID;
    private String name;
    private String type;
    private float price;
    private Image image;

    public Service() {
    }

    public Service(int ID, String name, String type, float price, Image image) {
        this.ID = ID;
        this.name = name;
        this.type = type;
        this.price = price;
        this.image = image;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public static Service add(Service s){
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(SwingFXUtils.fromFXImage((Image) s.getImage(), null), "jpg", os);
            InputStream fis = new ByteArrayInputStream(os.toByteArray());

            PreparedStatement stmnt = Database.CONNECTION.prepareStatement("INSERT INTO usluga VALUES (null,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stmnt.setString(1,s.getName());
            stmnt.setString(2,s.getType());
            stmnt.setFloat(3,s.getPrice());
            stmnt.setBinaryStream(4, fis);
            stmnt.executeUpdate();

            ResultSet rs = stmnt.getGeneratedKeys();
            if(rs.next()){
                s.setID(rs.getInt(1));
            }
            return  s;
        } catch (SQLException e) {
            System.out.println("Neuspješno dodavanje usluge u bazu: " + e.getMessage());
            return  null;
        }
        catch (IOException e) {
            System.out.println("Neuspješno dodavanje usluge: " + e.getMessage());
            return null;
        }
    }
    public static boolean remove(Service s){
        try {
            PreparedStatement stmnt = Database.CONNECTION.prepareStatement("DELETE FROM usluga WHERE id=?");
            stmnt.setInt(1,s.getID());
            stmnt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Neuspješno brisanje usluge iz baze: " + e.getMessage());
            return  false;
        }
    }
    public static boolean update(Service s){
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(SwingFXUtils.fromFXImage((Image) s.getImage(), null), "jpg", os);
            InputStream fis = new ByteArrayInputStream(os.toByteArray());

            PreparedStatement stmnt = Database.CONNECTION.prepareStatement
                    ("UPDATE usluga SET naziv_usluge=?, vrsta_usluge=?, cijena=?, slika=? WHERE id=?");
            stmnt.setString(1,s.getName());
            stmnt.setString(2,s.getType());
            stmnt.setFloat(3,s.getPrice());
            stmnt.setBinaryStream(4, fis);;
            stmnt.setInt(5,s.getID());
            stmnt.executeUpdate();
            return true;
        } catch (SQLException | IOException e ) {
            System.out.println("Neuspješno uređivanje usluge: " + e.getMessage());
            return  false;
        }
    }
    public static List<Service> select(){
        ObservableList<Service> services = FXCollections.observableArrayList();
        try {
            Statement stmnt = Database.CONNECTION.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT * FROM usluga");

            while(rs.next()){
                Image fxSlika = null;
                try {
                    BufferedImage bImage = ImageIO.read(rs.getBinaryStream(5));
                    fxSlika = SwingFXUtils.toFXImage(bImage, null);
                } catch (NullPointerException | IOException ex) {
                    fxSlika = null;
                }
                services.add(new Service(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getFloat(4),
                        fxSlika
                ));
            }
            return services;
        } catch (SQLException e) {
            System.out.println("Neuspješno izvlačenje usluga iz baze: " + e.getMessage());
            return null;
        }
    }

}
