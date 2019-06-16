package controller;

import facade.Facade;
import facade.Starter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.ClientRequest;
import model.Landscaper;
import model.PurchaseRequest;
import model.User;

import java.sql.SQLException;

public class LandscaperController {

    private Facade facade = Starter.facade;

    public Label viewLabel;
    public TextField uidField;
    public Label cReqsLabel;
    public TableView cReqsTable;
    public TableColumn CREQS_reqIDCol;
    public TableColumn CREQS_plantCol;
    public TableColumn CREQS_typeCol;
    public TableColumn CREQS_statusCol;
    public Button gardeningButton;
    public Label pReqsLabel;
    public TableView pReqsTable;
    public TableColumn PREQS_reqIDCol;
    public TableColumn PREQS_cReqIDCol;
    public TableColumn PREQS_plantCol;
    public TableColumn PREQS_statusCol;
    public Button checkPurchaseButton;
    public Button signOutButton;
    public Label firstNameLabel;
    public Label secondNameLabel;
    public Label errorMsg;
    public TextField creqToGarden;
    public TextField preqToCheckField;
    public Label gardeningLabel;
    public Label checkPurchaseLabel;

    private User user;
    private Integer reqToGarden;
    private Integer preqToCheck;

    public LandscaperController() throws SQLException, ClassNotFoundException {

    }

    public void gardeningButtonOnClicked(MouseEvent mouseEvent) throws SQLException {
        if (creqToGarden.getText().isEmpty()) {
            errorMsg.setText("Specify client request ID.");
            return;
        }
        String strcReqToGarden = creqToGarden.getText();
        reqToGarden = Integer.parseInt(strcReqToGarden);
        facade.updateCReqStatus(reqToGarden, ClientRequest.Status.done);
        try{
            Starter.showGardeningView(reqToGarden);
        } catch (Exception e) {
            errorMsg.setText(e.getMessage());
        }
    }

    public void checkPurchaseButtonOnClicked(MouseEvent mouseEvent) {
        if (preqToCheckField.getText().isEmpty()) {
            errorMsg.setText("Specify purchase request ID.");
            return;
        }
        String strpReqToCheck = preqToCheckField.getText();
        preqToCheck = Integer.parseInt(strpReqToCheck);
        try{
            Starter.CheckPurchaseView(preqToCheck);
        } catch (Exception e) {
            errorMsg.setText(e.getMessage());
        }
    }

    public void setData(Landscaper landscaperByUserID) {
        user = landscaperByUserID;
        try {
            uidField.setText(user.getUID().toString());
            firstNameLabel.setText(user.getFirstName());
            secondNameLabel.setText(user.getSecondName());
            ObservableList<ClientRequest> creqs = FXCollections.observableArrayList(facade.filterCReqsByLandscaperID(user.getUID()));
            CREQS_statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
            CREQS_reqIDCol.setCellValueFactory(new PropertyValueFactory<> ("cReqID"));
            CREQS_typeCol.setCellValueFactory(new PropertyValueFactory<> ("type"));
            CREQS_plantCol.setCellValueFactory(new PropertyValueFactory<> ("plantName"));
            cReqsTable.setItems(creqs);

            ObservableList<PurchaseRequest> preqs = FXCollections.observableArrayList(facade.filterPReqsByLandscaperID(user.getUID()));
            PREQS_reqIDCol.setCellValueFactory(new PropertyValueFactory<>("pReqID"));
            PREQS_cReqIDCol.setCellValueFactory(new PropertyValueFactory<> ("cReqID"));
            PREQS_plantCol.setCellValueFactory(new PropertyValueFactory<>("plantName"));
            PREQS_statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
            pReqsTable.setItems(preqs);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void signOutButtonOnClicked(MouseEvent mouseEvent) {
        Starter.showAuthView();
    }
}
