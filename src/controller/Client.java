package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Database;
import model.Reservation;
import model.Service;
import model.User;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Client implements Initializable {
    @FXML
    TableView uslugaTablica;

    @FXML
    TableColumn uslugaId;

    @FXML
    TableColumn uslugaNaziv;

    @FXML
    TableColumn uslugaVrsta;

    @FXML
    TableColumn uslugaCijena;

    @FXML
    TextField rDatum;

    @FXML
    TextField rUsluga;

    @FXML
    TextField rKlijent;

    @FXML
    Button rDodaj;

    @FXML
    TableView rezervacijaTablica;

    @FXML
    TableColumn rezervacijaId;

    @FXML
    TableColumn rezervacijaDatum;

    @FXML
    TableColumn rezervacijaKlId;

    @FXML
    TableColumn rezervacijaUslugaId;

    @FXML
    TextField rnDatum;

    @FXML
    TextField rnUsluga;

    @FXML
    TextField rnKlijent;

    @FXML
    Button rUredi;

    @FXML
    Button rBrisi;

    @FXML
    Button odjavaBtn0;

    @FXML
    Button odjavaBtn1;

    public static User logiraniKorisnik;
    Reservation selectedReservation = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.uslugaId.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.uslugaNaziv.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.uslugaVrsta.setCellValueFactory(new PropertyValueFactory<>("type"));
        this.uslugaCijena.setCellValueFactory(new PropertyValueFactory<>("price"));

        this.rezervacijaId.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.rezervacijaDatum.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.rezervacijaKlId.setCellValueFactory(new PropertyValueFactory<>("userID"));
        this.rezervacijaUslugaId.setCellValueFactory(new PropertyValueFactory<>("serviceID"));

        this.popuniUsluge();
        this.popuniRezervacije();

    }
    private void popuniUsluge () {
        ObservableList<Service> services= (ObservableList<Service>) Service.select();
        this.uslugaTablica.setItems(services);
    }
    private void popuniRezervacije () {

        int klijent= Login.logiraniKorisnik.getID();

        ObservableList<Reservation> reservations= (ObservableList<Reservation>) Reservation.selectById(klijent);
        this.rezervacijaTablica.setItems(reservations);

    }

    @FXML
    public void dodajRez(ActionEvent ev) {
        String datum = this.rDatum.getText();
        String klijent = this.rKlijent.getText();

        Integer usluga = Integer.valueOf(this.rUsluga.getText());
        int klijentId = 0;
        if (datum.equals("") || klijent.equals("") || usluga.equals("")) {
            return;
        }

        try {
            PreparedStatement stmn = Database.CONNECTION.prepareStatement("SELECT ID FROM korisnik WHERE korisnicko_ime=?");
            stmn.setString(1, klijent);
            ResultSet r = stmn.executeQuery();


            if (r.next()) {
                Client.logiraniKorisnik = User.get(r.getInt(1));
                klijentId=logiraniKorisnik.getID();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

            Reservation r = new Reservation(0, datum, false, 0," ", klijentId,usluga);
            Reservation.add(r);


            this.rDatum.setText("");
            this.rKlijent.setText("");
            this.rUsluga.setText("");
            this.popuniRezervacije ();

        }

    @FXML
    public void odaberiRezervaciju(MouseEvent ev) {
        this.selectedReservation = (Reservation) this.rezervacijaTablica.getSelectionModel().getSelectedItem();
        if(this.selectedReservation != null) {
            this.rnDatum.setText(this.selectedReservation.getDate());
            this.rnKlijent.setText(String.valueOf(this.selectedReservation.getUserID()));
            this.rnUsluga.setText(String.valueOf(this.selectedReservation.getServiceID()));
        }
    }
    @FXML
    public void urediRezervaciju(ActionEvent ev) {
        String datum = this.rnDatum.getText();
        Integer klijent = Integer.valueOf(this.rnKlijent.getText());
        Integer usluga = Integer.valueOf(this.rnUsluga.getText());

        if (datum.equals("") || klijent.equals("") || usluga.equals("")) {
            return;
        }

            this.selectedReservation.setDate(datum);
            this.selectedReservation.setUserID(klijent);
            this.selectedReservation.setServiceID(usluga);


            Reservation.update(this.selectedReservation);
            this.selectedReservation = null;


        this.rnDatum.setText("");
        this.rnKlijent.setText("");
        this.rnUsluga.setText("");

        this.popuniRezervacije();
    }
    @FXML
    public void brisiRezervaciju (ActionEvent ev) {
        Reservation reservation = (Reservation) this.rezervacijaTablica.getSelectionModel().getSelectedItem();
        Reservation.remove(reservation);
        this.popuniRezervacije();
    }

    @FXML
    public void odjava(ActionEvent ev){
        Utils u = new Utils();
        u.showNewWindow("login", ev);
    }


}


