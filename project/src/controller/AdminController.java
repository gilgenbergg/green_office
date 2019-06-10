package controller;

import data.CReqsMapper;
import data.PReqsMapper;
import facade.Starter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Admin;
import model.ClientRequest;
import model.PurchaseRequest;
import model.User;

import java.sql.SQLException;

public class AdminController {
    public Label viewLabel;
    public TextField uidField;
    public Label cReqsLabel;
    public TableView cReqsTable;
    public TableColumn CREQS_reqIDCol;
    public TableColumn CREQS_plantIDCol;
    public TableColumn CREQS_typeCol;
    public TableColumn CREQS_statusCol;
    public Button toCReqEditorButton;
    public Label purchasesLabel;
    public TableView pReqsTable;
    public TableColumn PREQS_reqIDCol;
    public TableColumn PREQS_cReqID;
    public TableColumn PREQS_plantIDCol;
    public TableColumn PREQS_statusCol;
    public Button newPurchaseButton;
    public Label reqIDtoEditLabel;
    public TextField reqIDtoEditField;
    public Label errorMsg;

    Integer cReqIDToEdit;
    User user;
    private CReqsMapper creqsBase = new CReqsMapper();
    private PReqsMapper preqsBase = new PReqsMapper();

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
            Starter.CReqEditorView(cReqIDToEdit);
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

    public void setData(Admin adminByUserID) {
        user = adminByUserID;
        try {
            uidField.setText(user.getUID().toString());
            ObservableList<ClientRequest> creqs = FXCollections.observableArrayList(creqsBase.filterByAdminID(user.getUID()));
            CREQS_statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
            CREQS_reqIDCol.setCellValueFactory(new PropertyValueFactory<> ("cReqID"));
            CREQS_typeCol.setCellValueFactory(new PropertyValueFactory<> ("type"));
            CREQS_plantIDCol.setCellValueFactory(new PropertyValueFactory<> ("plantID"));
            cReqsTable.setItems(creqs);

            ObservableList<PurchaseRequest> preqs = FXCollections.observableArrayList(preqsBase.filterByUserID(user.getUID()));
            PREQS_reqIDCol.setCellValueFactory(new PropertyValueFactory<>("pReqID"));
            PREQS_cReqID.setCellValueFactory(new PropertyValueFactory<> ("cReqID"));
            PREQS_plantIDCol.setCellValueFactory(new PropertyValueFactory<>("plantID"));
            PREQS_statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
            pReqsTable.setItems(preqs);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }
}
