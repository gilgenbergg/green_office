package controller;

import facade.Facade;
import facade.Starter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.PurchaseRequest;

import java.sql.SQLException;

public class CheckPurchaseController {

    private Facade facade = Starter.facade;

    public Label viewLabel;
    public TextField pReqIDField;
    public Label plantIDLabel;
    public TextField plantIDField;
    public Label alreadyBoughtLabel;
    public Label requiredResLabel;
    public ListView<String> alreadyBoughtList;
    public ListView<String> requiredResourcesList;
    public Button approvedPurchase;
    public Button nonApprovedPurchase;
    public Button backButton;

    private Integer preqID;
    private Integer plantID;
    private Integer landscaperID;

    public CheckPurchaseController() throws SQLException, ClassNotFoundException {
    }

    public void approvedOnClicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        facade.updatePReqStatus(preqID, PurchaseRequest.Status.approved);
        Starter.showLandscaperView(facade.getLandscaperByUserID(landscaperID));
    }

    public void nonApprovedOnClicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        facade.updatePReqStatus(preqID, PurchaseRequest.Status.inProgress);
        Starter.showLandscaperView(facade.getLandscaperByUserID(landscaperID));
    }

    public void backButtonOnCLicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Starter.showLandscaperView(facade.getLandscaperByUserID(landscaperID));
    }

    public void setData(Integer pReqID) throws SQLException {
        preqID = pReqID;
        pReqIDField.setText(pReqID.toString());
        PurchaseRequest preq = facade.findPReqByID(preqID);
        landscaperID = preq.getLandscaperID();
        plantID = preq.getPlantID();
        plantIDField.setText(plantID.toString());

        ObservableList<String> resources = FXCollections.observableArrayList(facade.getTypesByPlantID(plantID));
        alreadyBoughtList.setItems(resources.sorted());

        String plantType = facade.findItemByPlantID(plantID).getType();
        ObservableList<String> requiredResources = FXCollections.observableArrayList(facade.findRequiredByPlantType(plantType));
        requiredResourcesList.setItems(requiredResources);
    }
}
