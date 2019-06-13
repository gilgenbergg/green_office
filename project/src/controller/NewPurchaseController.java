package controller;

import data.*;
import facade.Starter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.*;

import java.sql.SQLException;
import java.util.List;

import static service.TheShop.checkItem;

public class NewPurchaseController {
    public Label viewLabel;
    public Label assignedCReqLabel;
    public Label plantIDLabel;
    public Label landscaperIDLabel;
    public Label boughtListLabel;
    public ComboBox<Integer> cReqIDField;
    public ComboBox<Integer> plantIDField;
    public ComboBox<Integer> landscaperIDField;
    public TableView alreadyBoughtTable;
    public Label statusLabel;
    public RadioButton isInProgressRadio;
    public RadioButton isInCheckRadio;
    public RadioButton isDoneRadio;
    public Button newPReqButton;
    public Button buyResourceButton;
    public TextField toAssignResource;
    public Label errorMsg;
    public TableColumn resourceNameCol;
    public Label plantLabel;
    public Button backButton;

    PReqsMapper preqsBase = new PReqsMapper();
    UsersMapper usersBase = new UsersMapper();
    ResourcesMapper resourcesBase = new ResourcesMapper();
    PlantsMapper plantsBase = new PlantsMapper();
    CReqsMapper cReqsBase = new CReqsMapper();


    private String chosenStatus = "";
    private Integer adminID;
    private Integer assignedcReqID;
    private Integer assignedPlantID;
    private Integer assignedLandscaperID;
    private ObservableList<Resource> boughtArray = FXCollections.observableArrayList();

    public NewPurchaseController() throws SQLException, ClassNotFoundException {
    }

    public void newPReqButtonOnClicked(MouseEvent mouseEvent) {
        PurchaseRequest.Status status = preqsBase.parseStatusFromDB(chosenStatus);

        if ((cReqIDField.getValue() == null) || (plantIDField.getValue() == null) ||
                (landscaperIDField.getValue() == null) ||
                ((!isInProgressRadio.isSelected()) && (!isInCheckRadio.isSelected()) && (!isDoneRadio.isSelected()))) {
                        errorMsg.setText("Please fill all the fields.");
                        return;
        }
        try{
            PurchaseRequest pReq = new PurchaseRequest(null, assignedcReqID, assignedPlantID,
                    plantsBase.findItemByPlantID(assignedPlantID).getType(), assignedLandscaperID,
                    this.adminID, status);
            preqsBase.addPReq(pReq);
            Starter.showAdminView(usersBase.getAdminByUserID(adminID));
        } catch (Exception e) {
            errorMsg.setText(e.getMessage());
        }
    }

    public void buyResourceOnClicked(MouseEvent mouseEvent) throws SQLException {
        if (toAssignResource.getText().isEmpty()) {
            errorMsg.setText("Specify what do you want to buy.");
            return;
        }
        String resource = toAssignResource.getText();
        boolean available = checkItem(resource);
        if (!available) {
            errorMsg.setText("Item is not available in the shop.");
            return;
        }
        if (plantIDField.getValue() == null) {
            errorMsg.setText("Specify plantID.");
            return;
        }
        Resource newResource = new Resource(null, resource, assignedPlantID);
        Resource addedRes = resourcesBase.addNewItem(newResource);
        boughtArray.add(addedRes);
        resourceNameCol.setCellValueFactory(new PropertyValueFactory<>("resource"));
        alreadyBoughtTable.setItems(boughtArray);
        toAssignResource.setText("");
        errorMsg.setText("");
    }

    public void setData(Integer adminID) throws SQLException, ClassNotFoundException {
        this.adminID = adminID;
        ObservableList<Integer> plantsIDS = FXCollections.observableArrayList();
        List<Plant> allPlants = plantsBase.allPlants();
        for (Plant item:
                allPlants) {
            plantsIDS.add(item.getPlantID());
        }
        plantIDField.setItems(plantsIDS.sorted());

        ObservableList<Integer> landscapersIDS = FXCollections.observableArrayList();
        List<Landscaper> allLandscapers = usersBase.allLandscapers();
        for (Landscaper item:
             allLandscapers) {
            landscapersIDS.add(item.getUID());
        }
        landscaperIDField.setItems(landscapersIDS.sorted());

        cReqIDField.setItems(null);
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

    public void cReqChosen(ActionEvent actionEvent) {
        assignedcReqID = cReqIDField.getValue();
    }

    public void plantIDChosen(ActionEvent actionEvent) throws SQLException {
        assignedPlantID = plantIDField.getValue();
        plantLabel.setText(plantsBase.findItemByPlantID(assignedPlantID).getType());
        ObservableList<Integer> cReqsIDS = FXCollections.observableArrayList();
        List<ClientRequest> allCReqs = cReqsBase.filterByPlantID(assignedPlantID);
        for (ClientRequest item:
                allCReqs) {
            cReqsIDS.add(item.getCReqID());
        }
        cReqIDField.setItems(cReqsIDS.sorted());
    }

    public void landscaperIDChosen(ActionEvent actionEvent) {
        assignedLandscaperID = landscaperIDField.getValue();
    }

    public void backButtonOnCLicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Starter.showAdminView(usersBase.getAdminByUserID(adminID));
    }
}
