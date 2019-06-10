package controller;

import data.PReqsMapper;
import data.ResourcesMapper;
import data.UsersMapper;
import facade.Starter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.PurchaseRequest;
import model.Resource;

import java.sql.SQLException;

public class NewPurchaseController {
    public Label viewLabel;
    public Label assignedCReqLabel;
    public Label plantIDLabel;
    public Label landscaperIDLabel;
    public Label boughtListLabel;
    public TextField cReqIDField;
    public TextField plantIDField;
    public TextField landscaperIDField;
    public TableView alreadyBoughtTable;
    public Label statusLabel;
    public RadioButton isInProgressRadio;
    public RadioButton isInCheckRadio;
    public RadioButton isDoneRadio;
    public Button newPReqButton;
    public Button buyResourceButton;
    public TextField toAssignResourceID;
    public Label errorMsg;
    public TableColumn resourceNameCol;

    PReqsMapper preqsBase = new PReqsMapper();
    UsersMapper usersBase = new UsersMapper();
    ResourcesMapper resourcesBase = new ResourcesMapper();

    private String chosenStatus = "";
    private Integer adminID;
    private ObservableList<Resource> boughtArray = FXCollections.observableArrayList();

    public NewPurchaseController() throws SQLException, ClassNotFoundException {
    }

    public void newPReqButtonOnClicked(MouseEvent mouseEvent) {
        Integer assignedCReq = Integer.parseInt(cReqIDField.getText());
        Integer plantID = Integer.parseInt(plantIDField.getText());
        Integer landscaperID = Integer.parseInt(landscaperIDField.getText());
        PurchaseRequest.Status status = preqsBase.parseStatusFromDB(chosenStatus);

        if ((cReqIDField.getText().isEmpty()) || (plantIDField.getText().isEmpty()) ||
                (landscaperIDField.getText().isEmpty()) ||
                ((!isInProgressRadio.isSelected()) && (!isInCheckRadio.isSelected()) && (!isDoneRadio.isSelected()))) {
                        errorMsg.setText("Please fill all the fields.");
                        return;
        }
        try{
            PurchaseRequest pReq = new PurchaseRequest(null, assignedCReq, plantID, landscaperID, this.adminID, status);
            preqsBase.addPReq(pReq);
            Starter.showAdminView(usersBase.getAdminByUserID(adminID));
        } catch (Exception e) {
            errorMsg.setText(e.getMessage());
        }
    }

    public void buyResourceOnClicked(MouseEvent mouseEvent) throws SQLException {
        if (toAssignResourceID.getText().isEmpty()) {
            errorMsg.setText("In order to buy the resource, specify its ID.");
            return;
        }
        Integer resourceToBuyID = Integer.parseInt(toAssignResourceID.getText());
        Resource curRes = resourcesBase.getResourceByID(resourceToBuyID);
        boughtArray.add(curRes);
        resourceNameCol.setCellValueFactory(new PropertyValueFactory<>("resource"));
        alreadyBoughtTable.setItems(boughtArray);
        toAssignResourceID.setText("");
    }

    public void setData(Integer adminID) {
        this.adminID = adminID;
    }

    public void isInProgressClicked(MouseEvent mouseEvent) {
        isInProgressRadio.setSelected(true);
        isInCheckRadio.setSelected(false);
        isDoneRadio.setSelected(false);
        this.chosenStatus = isInProgressRadio.getText();
    }

    public void isInCheckClicked(MouseEvent mouseEvent) {
        isInProgressRadio.setSelected(false);
        isInCheckRadio.setSelected(true);
        isDoneRadio.setSelected(false);
        this.chosenStatus = isInCheckRadio.getText();
    }

    public void isDoneClicked(MouseEvent mouseEvent) {
        isInProgressRadio.setSelected(false);
        isInCheckRadio.setSelected(false);
        isDoneRadio.setSelected(true);
        this.chosenStatus = isDoneRadio.getText();
    }
}
