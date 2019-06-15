package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.Service;
import model.User;

import javax.xml.soap.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class Services implements Initializable {
    @FXML
    GridPane panee;

    @FXML
    Button natragBtn;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Service> services = (ObservableList<Service>) Service.select();
        int i = 0;
        int j = 0;
        for (Service s : services) {
            Button btn = new Button();
            ImageView iw = new ImageView(s.getImage());
            iw.setFitHeight(120);
            iw.setFitWidth(120);
            btn.setGraphic(iw);


            panee.add(btn, i, j);
            i++;
            if (i >= 4) {
                i = 0;
                j++;
            }

        }
    }
    @FXML
    public void natrag(ActionEvent ev){
        Utils u = new Utils();
        u.showNewWindow("login", ev);
    }

}