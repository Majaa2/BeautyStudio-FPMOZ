package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Database;
import model.User;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    TextField korisnikTxt;

    @FXML
    TextField lozinkaTxt;

    @FXML
    Button prijavaBtn;

    @FXML
    Button registracijaBtn;

    @FXML
    Button uslugeBtn;

    public static User logiraniKorisnik;

    @FXML
    public void loginUser(ActionEvent a) {
        String username= korisnikTxt.getText();
        String password = lozinkaTxt.getText();
        try {
            PreparedStatement stmnt = Database.CONNECTION.prepareStatement("SELECT * FROM korisnik WHERE korisnicko_ime=? AND lozinka=?");
            stmnt.setString(1, username);
            stmnt.setString(2, password);
            ResultSet rs = stmnt.executeQuery();




            if (rs.next()) {
                Login.logiraniKorisnik = User.get(rs.getInt(1));
                Utils u = new Utils();
                if (logiraniKorisnik.getRole().equals("ZAPOSLENIK")) {
                    u.showNewWindow("employee", a);
                } else if(logiraniKorisnik.getRole().equals("KLIJENT")){
                    u.showNewWindow("client", a);
                }
                else{
                    u.showNewWindow("admin", a);
                }
            } else {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void registrationUser(ActionEvent a) {
        Utils u = new Utils();
        u.showNewWindow("registration", a);
    }

    public void pregledUsluga(ActionEvent a) {
        Utils u = new Utils();
        u.showNewWindow("services", a);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
