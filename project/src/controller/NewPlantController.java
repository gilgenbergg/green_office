package controller;

import facade.Facade;
import facade.Starter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Client;
import model.Plant;

import java.sql.SQLException;
import java.util.List;

public class NewPlantController {

    private Facade facade = Starter.facade;

    public ComboBox clientIDSelector;
    public Label clientFirstNameLabel;
    public Label clientSecondNameLabel;
    public Label plantTypeLabel;
    public TextField plantTypeTextField;
    public Button newPlantAdded;
    public Label errorMsg;
    public Button backButton;

    private Integer clientID;
    private Integer adminID;

    public NewPlantController() throws SQLException, ClassNotFoundException {
    }

    public void clientIDChosen(ActionEvent actionEvent) {
        clientID = Integer.parseInt(clientIDSelector.getValue().toString());
    }

    public void newPlantOnClicked(MouseEvent mouseEvent) {
        if (plantTypeTextField.getText() == null) {
            errorMsg.setText("Please, specify the plant type.");
            return;
        }
        if (clientIDSelector.getValue() == null) {
            errorMsg.setText("Select owner of a new plant.");
            return;
        }
        try{
            String plantType = plantTypeTextField.getText();
            Plant plant = new Plant(null, plantType, null, null,
                    null, null, clientID);
            facade.addNewPlant(plant);
            Starter.showAdminView(facade.getAdminByUserID(adminID));
        } catch (Exception e) {
            errorMsg.setText(e.getMessage());
        }
    }

    public void setData(Integer adminID) throws SQLException, ClassNotFoundException {
        this.adminID = adminID;
        ObservableList<Integer> clientsIDS = FXCollections.observableArrayList();
        List<Client> allClients = facade.allClients();
        for (Client item:
                allClients) {
            clientsIDS.add(item.getUID());
        }
        clientIDSelector.setItems(clientsIDS);
    }

    public void backButtonOnCLicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Starter.showAdminView(facade.getAdminByUserID(adminID));
    }
}
