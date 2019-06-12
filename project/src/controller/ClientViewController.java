package controller;

import data.CReqsMapper;
import data.PlantsMapper;
import facade.Facade;
import facade.Starter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Client;
import model.ClientRequest;
import model.Plant;
import model.User;

import java.io.IOException;
import java.sql.SQLException;

public class ClientViewController {

    public Button signOutButton;
    public Label firstNameLabel;
    public Label secondNameLabel;
    private Facade facade = Starter.facade;
    User user;
    private PlantsMapper plantsBase = new PlantsMapper();
    private CReqsMapper creqsBase = new CReqsMapper();

    public Label viewLabel;
    public TextField uidField;
    public Label plantsLabel;
    public Label requestsLabel;
    public Button newCReqButton;
    public TableView<Plant> plantsTable;
    public TableView<ClientRequest> cReqsTable;
    public TableColumn<Plant, Integer> PLANTS_plantIDCol;
    public TableColumn<Plant, String> PLANTS_typeCol;
    public TableColumn<Plant, String> PLANTS_lastInspectionCol;
    public TableColumn<Plant, String> PLANTS_nextInspectionCol;
    public TableColumn<ClientRequest, Integer> CREQS_requestIDCol;
    public TableColumn<ClientRequest, String> CREQS_statusCol;
    public TableColumn<ClientRequest, String> CREQS_typeCol;
    public TableColumn<ClientRequest, String> CREQS_plantCol;

    public ClientViewController() throws SQLException, ClassNotFoundException {
    }


    public void newCReqOnClicked(MouseEvent mouseEvent) throws IOException {
        Starter.newCReqView(user.getUID());
    }

    public void setData(Client clientByUserID) {
        user = clientByUserID;
        try {
            uidField.setText(user.getUID().toString());
            firstNameLabel.setText(user.getFirstName());
            secondNameLabel.setText(user.getSecondName());
            ObservableList<Plant> plants = FXCollections.observableArrayList(plantsBase.filterPlantsByUserID(user.getUID()));
            PLANTS_typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            PLANTS_plantIDCol.setCellValueFactory(new PropertyValueFactory<> ("plantID"));
            PLANTS_lastInspectionCol.setCellValueFactory(new PropertyValueFactory<>("lastInspection"));
            PLANTS_nextInspectionCol.setCellValueFactory(new PropertyValueFactory<>("nextInspection"));
            plantsTable.setItems(plants);
            ObservableList<ClientRequest> creqs = FXCollections.observableArrayList(creqsBase.filterByUID(user.getUID()));
            CREQS_statusCol.setCellValueFactory(new PropertyValueFactory<> ("status"));
            CREQS_requestIDCol.setCellValueFactory(new PropertyValueFactory<> ("cReqID"));
            CREQS_typeCol.setCellValueFactory(new PropertyValueFactory<> ("type"));
            CREQS_plantCol.setCellValueFactory(new PropertyValueFactory<> ("plantName"));
            cReqsTable.setItems(creqs);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void signOutOnClicked(MouseEvent mouseEvent) {
        Starter.showAuthView();
    }
}
