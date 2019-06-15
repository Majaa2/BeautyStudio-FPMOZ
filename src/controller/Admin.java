package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import model.Reservation;
import model.Resvs;
import model.Service;
import model.User;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Admin implements Initializable {

    @FXML
    Button odjavaBtn1;

    @FXML
    Button odjavaBtn2;

    @FXML
    Button odjavaBtn3;

    @FXML
    Button odjavaBtn4;

    @FXML
    TableView zaposlenikTablica;

    @FXML
    TableColumn zaposlenikId;

    @FXML
    TableColumn zaposlenikIme;

    @FXML
    TableColumn zaposlenikPrezime;

    @FXML
    TableColumn zaposlenikEmail;

    @FXML
    TableColumn zaposlenikKime;

    @FXML
    TableColumn zaposlenikLozinka;

    @FXML
    TextField zIme;

    @FXML
    TextField zPrezime;

    @FXML
    TextField zEmail;

    @FXML
    TextField zKime;

    @FXML
    TextField zLozinka;

    @FXML
    Button dodajBtn;

    @FXML
    Button izbrisiBtn;

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
    TableColumn klijentLozinka;

    @FXML
    TextField klIme;

    @FXML
    TextField klPrezime;

    @FXML
    TextField klEmail;

    @FXML
    TextField klKime;

    @FXML
    TextField klLozinka;

    @FXML
    Button dodajklBtn;

    @FXML
    Button izbrisikl;

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
    TextField uNaziv;

    @FXML
    TextField uVrsta;

    @FXML
    TextField uCijena;

    @FXML
    ImageView uSlika;

    @FXML
    Button dodajSlikuBtn;

    @FXML
    Button dodajUsluguBtn;

    @FXML
    Button izbrisiUsluguBtn;

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
    Button dodajRezBtn;

    @FXML
    Button izbrisiRezBtn;

    @FXML
    TableView statistikaTablica;

    @FXML
    TableColumn nazivKlijenta;

    @FXML
    TableColumn brojRezervacija;

    User selectedEmployee = null;
    User selectedClient = null;
    Service selectedService = null;
    Reservation selectedReservation = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.zaposlenikId.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.zaposlenikIme.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.zaposlenikPrezime.setCellValueFactory(new PropertyValueFactory<>("surname"));
        this.zaposlenikEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.zaposlenikKime.setCellValueFactory(new PropertyValueFactory<>("username"));
        this.zaposlenikLozinka.setCellValueFactory(new PropertyValueFactory<>("password"));

        this.klijentId.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.klijentIme.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.klijentPrezime.setCellValueFactory(new PropertyValueFactory<>("surname"));
        this.klijentEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.klijentKime.setCellValueFactory(new PropertyValueFactory<>("username"));
        this.klijentLozinka.setCellValueFactory(new PropertyValueFactory<>("password"));

        this.uslugaId.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.uslugaNaziv.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.uslugaVrsta.setCellValueFactory(new PropertyValueFactory<>("type"));
        this.uslugaCijena.setCellValueFactory(new PropertyValueFactory<>("price"));


        this.rezervacijaId.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.rezervacijaDatum.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.rezervacijaKlijent.setCellValueFactory(new PropertyValueFactory<>("userID"));
        this.rezervacijaUsluga.setCellValueFactory(new PropertyValueFactory<>("serviceID"));


        this.nazivKlijenta.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.brojRezervacija.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        this.popuniZaposlenike();
        this.popuniKlijente();
        this.popuniUsluge();
        this.popuniRezervacije();
        this.popuniStatistiku();
    }
    private void popuniZaposlenike () {
        ObservableList<User> users = (ObservableList<User>) User.selectByRole("ZAPOSLENIK");
        this.zaposlenikTablica.setItems(users);
    }
    private void popuniKlijente () {
        ObservableList<User> users = (ObservableList<User>) User.selectByRole("KLIJENT");
        this.klijentTablica.setItems(users);
    }
    private void popuniUsluge () {
        ObservableList<Service> services= (ObservableList<Service>) Service.select();
        this.uslugaTablica.setItems(services);
    }
    private void popuniRezervacije() {
        ObservableList<Reservation> reservations= (ObservableList<Reservation>) Reservation.select();
        this.rezervacijaTablica.setItems(reservations);
    }

    private void popuniStatistiku() {
        ObservableList<Resvs> resvs= FXCollections.observableArrayList();
        //Map<String, Integer> map = new HashMap<>();
        ObservableList<User> users = (ObservableList<User>) User.selectByRole("KLIJENT");
        ObservableList<Reservation> reservations = (ObservableList<Reservation>) Reservation.select();

        for (User u : users) {
            int sum=0;
            for(Reservation r:reservations){
                if(u.getID()==r.getUserID()){
                    sum++;
                }

            }
            Resvs re = new Resvs(u.getName(),sum);
            resvs.add(re);
        }

        this.statistikaTablica.setItems(resvs);
    }
    @FXML
    public void odjava(ActionEvent  ev){
        Utils u = new Utils();
        u.showNewWindow("login", ev);
    }

    @FXML
    public void odaberiZaposlenika(MouseEvent ev) {
        this.dodajBtn.setText("Uredi");
        this.selectedEmployee = (User) this.zaposlenikTablica.getSelectionModel().getSelectedItem();
        if (this.selectedEmployee  != null) {
            this.zIme.setText(this.selectedEmployee.getName());
            this.zPrezime.setText(this.selectedEmployee.getSurname());
            this.zEmail.setText(this.selectedEmployee.getEmail());
            this.zKime.setText(this.selectedEmployee.getUsername());
            this.zLozinka.setText(this.selectedEmployee.getPassword());
        }
    }
    @FXML
    public void odaberiKlijenta(MouseEvent ev) {
        this.dodajklBtn.setText("Uredi");
        this.selectedClient = (User) this.klijentTablica.getSelectionModel().getSelectedItem();
        if (this.selectedClient  != null) {
            this.klIme.setText(this.selectedClient.getName());
            this.klPrezime.setText(this.selectedClient.getSurname());
            this.klEmail.setText(this.selectedClient.getEmail());
            this.klKime.setText(this.selectedClient.getUsername());
            this.klLozinka.setText(this.selectedClient.getPassword());
        }
    }

    @FXML
    public void odaberiUslugu(MouseEvent ev) {
        this.dodajUsluguBtn.setText("Uredi");
        this.selectedService = (Service) this.uslugaTablica.getSelectionModel().getSelectedItem();
        if(this.selectedService != null) {
            this.uNaziv.setText(this.selectedService.getName());
            this.uVrsta.setText(this.selectedService.getType());
            this.uCijena.setText(String.valueOf(this.selectedService.getPrice()));
            this.uSlika.setImage(this.selectedService.getImage());
        }
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
    public void dodajZaposlenika (ActionEvent ev) {
        String ime = this.zIme.getText();
        String prezime = this.zPrezime.getText();
        String mail = this.zEmail.getText();
        String korisnicko_ime = this.zKime.getText();
        String lozinka = this.zLozinka.getText();
        if (ime.equals("") || prezime.equals("") || mail.equals("") || korisnicko_ime.equals("") || lozinka.equals("")) {
            return;
        }

        if (this.selectedEmployee != null) {
            this.selectedEmployee.setName(ime);
            this.selectedEmployee.setSurname(prezime);
            this.selectedEmployee.setEmail(mail);
            this.selectedEmployee.setUsername(korisnicko_ime);
            this.selectedEmployee.setPassword(lozinka);
            User.update(this.selectedEmployee);
            this.selectedEmployee = null;
            this.dodajBtn.setText("Dodaj");
        } else {
            User u = new User(0, ime, prezime,mail, korisnicko_ime, lozinka, "ZAPOSLENIK");
            User.add(u);
        }
        this.popuniZaposlenike();

        this.zIme.setText("");
        this.zPrezime.setText("");
        this.zEmail.setText("");
        this.zKime.setText("");
        this.zLozinka.setText("");
    }

    @FXML
    public void dodajKlijenta(ActionEvent ev) {
        String ime = this.klIme.getText();
        String prezime = this.klPrezime.getText();
        String mail = this.klEmail.getText();
        String korisnicko_ime = this.klKime.getText();
        String lozinka = this.klLozinka.getText();
        if (ime.equals("") || prezime.equals("") || mail.equals("") || korisnicko_ime.equals("") || lozinka.equals("")) {
            return;
        }

        if (this.selectedClient != null) {
            this.selectedClient.setName(ime);
            this.selectedClient.setSurname(prezime);
            this.selectedClient.setEmail(mail);
            this.selectedClient.setUsername(korisnicko_ime);
            this.selectedClient.setPassword(lozinka);
            User.update(this.selectedClient);
            this.selectedClient = null;
            this.dodajklBtn.setText("Dodaj");
        } else {
            User u = new User(0, ime, prezime,mail, korisnicko_ime, lozinka, "KLIJENT");
            User.add(u);
        }
        this.popuniKlijente();

        this.klIme.setText("");
        this.klPrezime.setText("");
        this.klEmail.setText("");
        this.klKime.setText("");
        this.klLozinka.setText("");
    }

    @FXML
    public void dodajUslugu (ActionEvent ev) {
        String naziv = this.uNaziv.getText();
        String vrsta = this.uVrsta.getText();
        float cijena = Float.valueOf(this.uCijena.getText());
        Image slika = this.uSlika.getImage();
        if (naziv.equals("") || vrsta.equals("")) {
            return;
        }
        if (this.selectedService != null) {
            this.selectedService.setName(naziv);
            this.selectedService.setType(vrsta);
            this.selectedService.setPrice(cijena);

            if(slika != null) {
                this.selectedService.setImage(slika);
            }
            Service.update(this.selectedService);
            this.selectedService = null;
            this.dodajUsluguBtn.setText("Dodaj");
        } else {
            Service s = new Service(0, naziv, vrsta, cijena, slika);
            Service.add(s);
        }


        this.uNaziv.setText("");
        this.uVrsta.setText("");
        this.uCijena.setText("");
        this.uSlika.setImage(null);
        this.popuniUsluge();
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
        this.popuniStatistiku();
    }

    @FXML
    public void dodajSliku(ActionEvent evt) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.jpg"));
        File datoteka = fc.showOpenDialog(null);
        Image binarnaSlika = new Image(datoteka.toURI().toString());
        this.uSlika.setImage(binarnaSlika);
    }


    @FXML
    public void izbrisiZaposlenika(ActionEvent ev) {
        User user = (User) this.zaposlenikTablica.getSelectionModel().getSelectedItem();
        User.remove(user);
        this.popuniZaposlenike();
    }

    @FXML
    public void izbrisiKlijenta(ActionEvent ev) {
        User user = (User) this.klijentTablica.getSelectionModel().getSelectedItem();
        User.remove(user);
        this.popuniKlijente();
    }

    @FXML
    public void izbrisiUslugu (ActionEvent ev) {
        Service service = (Service) this.uslugaTablica.getSelectionModel().getSelectedItem();
        Service.remove(service);
        this.popuniUsluge();
    }
    @FXML
    public void izbrisiRez (ActionEvent ev) {
        Reservation reservation = (Reservation) this.rezervacijaTablica.getSelectionModel().getSelectedItem();
        Reservation.remove(reservation);
        this.popuniRezervacije();
    }

    @FXML
    public void skloni (MouseEvent ev) {
        this.dodajBtn.setText("Dodaj");
        this.dodajklBtn.setText("Dodaj");
        this.dodajUsluguBtn.setText("Dodaj");
        this.selectedEmployee = null;
        this.zIme.setText("");
        this.zPrezime.setText("");
        this.zEmail.setText("");
        this.zKime.setText("");
        this.zLozinka.setText("");

        this.selectedClient = null;
        this.klIme.setText("");
        this.klPrezime.setText("");
        this.klEmail.setText("");
        this.klKime.setText("");
        this.klLozinka.setText("");
        this.selectedService = null;
        this.uNaziv.setText("");
        this.uVrsta.setText("");
        this.uCijena.setText("");
        this.uSlika.setImage(null);

        this.selectedReservation = null;
        this.rDatum.setText("");
        this.rKlijent.setText("");
        this.rUsluga.setText("");

        this.popuniZaposlenike();
        this.popuniKlijente();
        this.popuniUsluge();
        this.popuniRezervacije();
    }


    }
