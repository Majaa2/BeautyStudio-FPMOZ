package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class Registration implements Initializable {

    @FXML
    TextField imeTxt;

    @FXML
    TextField prezimeTxt;

    @FXML
    TextField mailTxt;

    @FXML
    TextField kimeTxt;

    @FXML
    TextField lozinkaTxt;

    @FXML
    Button registracijaBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    public void registrationUser (ActionEvent ev) {
        String ime = this.imeTxt.getText();
        String prezime = this.prezimeTxt.getText();
        String mail = this.mailTxt.getText();
        String kime = this.kimeTxt.getText();
        String lozinka = this.lozinkaTxt.getText();

        if (ime.equals("") || prezime.equals("") || mail.equals("") || kime.equals("") || lozinka.equals("")) {
            return;
        }
        User u = new User(0, ime, prezime, mail, kime, lozinka, "KLIJENT");
        User.add(u);

        Utils ut = new Utils();
        ut.showNewWindow("client", ev);
    }


    }

