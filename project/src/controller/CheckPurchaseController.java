package controller;

import data.PReqsMapper;
import data.PlantsMapper;
import data.ResourcesMapper;
import data.UsersMapper;
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

    private PlantsMapper plantsBase = new PlantsMapper();
    private PReqsMapper preqsBase = new PReqsMapper();
    private ResourcesMapper resourcesBase = new ResourcesMapper();
    private UsersMapper usersBase = new UsersMapper();
    private Integer preqID;
    private Integer plantID;
    private Integer landscaperID;

    public CheckPurchaseController() throws SQLException, ClassNotFoundException {
    }

    public void approvedOnClicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        preqsBase.updateStatus(preqID, PurchaseRequest.Status.approved);
        Starter.showLandscaperView(usersBase.getLandscaperByUserID(landscaperID));
    }

    public void nonApprovedOnClicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        preqsBase.updateStatus(preqID, PurchaseRequest.Status.inProgress);
        Starter.showLandscaperView(usersBase.getLandscaperByUserID(landscaperID));
    }

    public void backButtonOnCLicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Starter.showLandscaperView(usersBase.getLandscaperByUserID(landscaperID));
    }

    public void setData(Integer pReqID) throws SQLException {
        preqID = pReqID;
        pReqIDField.setText(pReqID.toString());
        PurchaseRequest preq = preqsBase.findItemByID(preqID);
        landscaperID = preq.getLandscaperID();
        plantID = preq.getPlantID();
        plantIDField.setText(plantID.toString());

        ObservableList<String> resources = FXCollections.observableArrayList(resourcesBase.getTypesByPlantID(plantID));
        alreadyBoughtList.setItems(resources.sorted());

        //TODO: setting required resources, searching in specified table by plant type
    }
}
