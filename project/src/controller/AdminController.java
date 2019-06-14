package controller;

import facade.Facade;
import facade.Starter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.ClientRequest;
import model.PurchaseRequest;
import model.User;

import java.sql.SQLException;

public class AdminController {
    public Label viewLabel;
    public Label uidField;
    public Label cReqsLabel;
    public TableView cReqsTable;
    public TableColumn CREQS_reqIDCol;
    public TableColumn CREQS_plantCol;
    public TableColumn CREQS_typeCol;
    public TableColumn CREQS_statusCol;
    public Button toCReqEditorButton;
    public Label purchasesLabel;
    public TableView pReqsTable;
    public TableColumn PREQS_reqIDCol;
    public TableColumn PREQS_cReqID;
    public TableColumn PREQS_plantCol;
    public TableColumn PREQS_statusCol;
    public Button newPurchaseButton;
    public Label reqIDtoEditLabel;
    public TextField reqIDtoEditField;
    public Label errorMsg;
    public Label firstNameLabel;
    public Label secondNameLabel;
    public Button newPlantButton;
    public Button signOutButton;

    private Integer cReqIDToEdit;
    private User user;
    private Integer uid;
    Facade facade = Starter.facade;

    public AdminController() throws SQLException, ClassNotFoundException {
    }

    public void toCreqEditorOnClicked(MouseEvent mouseEvent) {
        String strcReqID = reqIDtoEditField.getText();
        cReqIDToEdit = Integer.parseInt(strcReqID);
        if (reqIDtoEditField.getText().isEmpty()) {
            errorMsg.setText("Specify client request ID.");
            return;
        }
        try{
            Starter.CReqEditorView(cReqIDToEdit, uid);
        } catch (Exception e) {
            errorMsg.setText(e.getMessage());
        }
    }

    public void newPurchaseOnClicked(MouseEvent mouseEvent) {
        try{
            Starter.NewPurchaseView(user.getUID());
        } catch (Exception e) {
            errorMsg.setText(e.getMessage());
        }
    }

    public void setData(Integer adminID) throws SQLException {
        user = facade.findItemByUID(adminID);
        uid = adminID;
        try {
            uidField.setText(user.getUID().toString());
            firstNameLabel.setText(user.getFirstName());
            secondNameLabel.setText(user.getSecondName());
            ObservableList<ClientRequest> creqs = FXCollections.observableArrayList(facade.filterByAdminID(user.getUID()));
            CREQS_statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
            CREQS_reqIDCol.setCellValueFactory(new PropertyValueFactory<> ("cReqID"));
            CREQS_typeCol.setCellValueFactory(new PropertyValueFactory<> ("type"));
            CREQS_plantCol.setCellValueFactory(new PropertyValueFactory<> ("plantName"));
            cReqsTable.setItems(creqs);

            ObservableList<PurchaseRequest> preqs = FXCollections.observableArrayList(facade.filterByUserID(user.getUID()));
            PREQS_reqIDCol.setCellValueFactory(new PropertyValueFactory<>("pReqID"));
            PREQS_cReqID.setCellValueFactory(new PropertyValueFactory<> ("cReqID"));
            PREQS_plantCol.setCellValueFactory(new PropertyValueFactory<>("plantName"));
            PREQS_statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
            pReqsTable.setItems(preqs);

            firstNameLabel.setText(user.getFirstName());
            secondNameLabel.setText(user.getSecondName());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void newPlantOnClicked(MouseEvent mouseEvent) {
        Starter.showNewPlantView(user.getUID());
    }

    public void signOutOnClicked(MouseEvent mouseEvent) {
        Starter.showAuthView();
    }
}
