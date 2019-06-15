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
import model.Reservation;
import model.Service;
import model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class Employee implements Initializable {

    @FXML
    Button odjavaBtn;

    @FXML
    TableView rezervacijaTablica;

    @FXML
    TableColumn rezervacijaId;

    @FXML
    TableColumn rezervacijaDatum;

    @FXML
    TableColumn rezervacijaUsluga;

    @FXML
    TableColumn rezervacijaKlijent;

    @FXML
    TextField rDatum;

    @FXML
    TextField rUsluga;

    @FXML
    TextField rKlijent;

    @FXML
    TextField placeno;

    @FXML
    Button dodajRezBtn;

    @FXML
    Button izbrisiRezBtn;
    @FXML
    TableView klijentTablica;

    @FXML
    TableColumn klijentId;

    @FXML
    TableColumn klijentIme;

    @FXML
    TableColumn klijentPrezime;

    @FXML
    TableColumn klijentEmail;

    @FXML
    TableColumn klijentKime;

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

    User selectedClient = null;
    Service selectedService = null;
    Reservation selectedReservation = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.rezervacijaId.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.rezervacijaDatum.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.rezervacijaKlijent.setCellValueFactory(new PropertyValueFactory<>("userID"));
        this.rezervacijaUsluga.setCellValueFactory(new PropertyValueFactory<>("serviceID"));

        this.klijentId.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.klijentIme.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.klijentPrezime.setCellValueFactory(new PropertyValueFactory<>("surname"));
        this.klijentEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.klijentKime.setCellValueFactory(new PropertyValueFactory<>("username"));

        this.uslugaId.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.uslugaNaziv.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.uslugaVrsta.setCellValueFactory(new PropertyValueFactory<>("type"));
        this.uslugaCijena.setCellValueFactory(new PropertyValueFactory<>("price"));

        this.popuniRezervacije();
        this.popuniKlijente();
        this.popuniUsluge();

    }
    private void popuniRezervacije() {
        ObservableList<Reservation> reservations= (ObservableList<Reservation>) Reservation.select();
        this.rezervacijaTablica.setItems(reservations);
    }

    private void popuniKlijente () {
        ObservableList<User> users = (ObservableList<User>) User.selectByRole("KLIJENT");
        this.klijentTablica.setItems(users);
    }
    private void popuniUsluge () {
        ObservableList<Service> services= (ObservableList<Service>) Service.select();
        this.uslugaTablica.setItems(services);
    }

    @FXML
    public void odjava(ActionEvent ev){
        Utils u = new Utils();
        u.showNewWindow("login", ev);
    }

    @FXML
    public void odaberiRezervaciju(MouseEvent ev) {
        this.dodajRezBtn.setText("Uredi");
        this.selectedReservation = (Reservation) this.rezervacijaTablica.getSelectionModel().getSelectedItem();
        if(this.selectedReservation != null) {
            this.rDatum.setText(this.selectedReservation.getDate());
            this.rKlijent.setText(String.valueOf(this.selectedReservation.getUserID()));
            this.rUsluga.setText(String.valueOf(this.selectedReservation.getServiceID()));
        }
    }
    @FXML
    public void dodajRez (ActionEvent ev) {
        String datum = this.rDatum.getText();
        Integer klijent = Integer.valueOf(this.rKlijent.getText());
        Integer usluga = Integer.valueOf(this.rUsluga.getText());

        if (datum.equals("") || klijent.equals("") || usluga.equals("")) {
            return;
        }
        if (this.selectedReservation != null) {
            this.selectedReservation.setDate(datum);
            this.selectedReservation.setUserID(klijent);
            this.selectedReservation.setServiceID(usluga);


            Reservation.update(this.selectedReservation);
            this.selectedReservation = null;
            this.dodajRezBtn.setText("Dodaj");
        } else {
            Reservation r = new Reservation(0, datum, false, 0," ", klijent,usluga);
            Reservation.add(r);
        }


        this.rDatum.setText("");
        this.rKlijent.setText("");
        this.rUsluga.setText("");

        this.popuniRezervacije();
    }
    @FXML
    public void izbrisiRez (ActionEvent ev) {
        Reservation reservation = (Reservation) this.rezervacijaTablica.getSelectionModel().getSelectedItem();
        Reservation.remove(reservation);
        this.popuniRezervacije();
    }

    @FXML
    public void skloni (MouseEvent ev) {
        this.selectedReservation = null;
        this.rDatum.setText("");
        this.rKlijent.setText("");
        this.rUsluga.setText("");

        this.popuniRezervacije();
    }
}
