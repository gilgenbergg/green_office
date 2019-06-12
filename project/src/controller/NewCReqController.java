package controller;

import data.CReqsMapper;
import data.PlantsMapper;
import data.UsersMapper;
import facade.Starter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.ClientRequest;
import model.Plant;

import java.sql.SQLException;
import java.util.List;

public class NewCReqController {
    public Label viewLabel;
    public Label typeLabel;
    public Label plantIDLabel;
    public RadioButton isPlannedRadio;
    public RadioButton isFirstRadio;
    public Button createCReqButton;
    public Label errorMsg;
    public ComboBox<Integer> plantIDField;
    public Label plantLabel;
    public Button backButton;

    private String chosenType = "";
    private Integer clientID;
    private Integer assignedPlantID;

    CReqsMapper cReqsBase = new CReqsMapper();
    PlantsMapper plantsBase = new PlantsMapper();
    UsersMapper users  = new UsersMapper();

    public NewCReqController() throws SQLException, ClassNotFoundException {
    }

    public void createCReqButtonOnClicked(MouseEvent mouseEvent) throws SQLException {
        Integer plantID = null;
        String plantName = null;
        if (!(plantIDField.getValue() == null)) {
            plantID = Integer.parseInt(plantIDField.getValue().toString());
            plantName = plantsBase.findItemByPlantID(plantID).getType();
        }
        ClientRequest.Type type = cReqsBase.parseTypeFromDB(chosenType);
        if (chosenType.isEmpty()) {
            errorMsg.setText("Please choose the type.");
            return;
        }
        if ((chosenType.equals("planned")) && (plantIDField.getValue() == null)) {
            errorMsg.setText("For planned requests plantID field must be filled.");
            return;
        }
        try{
           ClientRequest creq = new ClientRequest(null, plantID, plantName,
                   this.clientID, null, null, null, type);
            ClientRequest added = cReqsBase.addCReq(creq);
            Starter.showClientView(users.getClientByUserID(clientID));
        } catch (Exception e) {
            errorMsg.setText(e.getMessage());
        }
    }

    public void setData(Integer uid) throws SQLException {
        this.clientID = uid;
        ObservableList<Integer> plantsIDS = FXCollections.observableArrayList();
        List<Plant> allPlants = plantsBase.allPlants();
        for (Plant item:
                allPlants) {
            plantsIDS.add(item.getPlantID());
        }
        plantIDField.setItems(plantsIDS.sorted());
    }

    public void isPlanned(MouseEvent mouseEvent) {
        isFirstRadio.setSelected(false);
        this.chosenType = isPlannedRadio.getText();
    }

    public void isFirst(MouseEvent mouseEvent) {
        isPlannedRadio.setSelected(false);
        this.chosenType = isFirstRadio.getText();
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
    }

    public void backButtonOnCLicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Starter.showClientView(users.getClientByUserID(clientID));
    }
}
