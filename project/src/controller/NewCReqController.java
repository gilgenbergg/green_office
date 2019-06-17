package controller;

import facade.Facade;
import facade.Starter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import model.ClientRequest;
import model.Plant;

import java.sql.SQLException;
import java.util.List;

public class NewCReqController {

    private Facade facade = Starter.facade;

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

    public NewCReqController() throws SQLException, ClassNotFoundException {
    }

    public void createCReqButtonOnClicked(MouseEvent mouseEvent) throws SQLException {
        Integer plantID = null;
        String plantName = null;
        if (!(plantIDField.getValue() == null)) {
            plantID = Integer.parseInt(plantIDField.getValue().toString());
            plantName = facade.findItemByPlantID(plantID).getType();
        }
        ClientRequest.Type type = facade.parseCReqTypeFromDB(chosenType);
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
            ClientRequest added = facade.addCReq(creq);
            Starter.showClientView(facade.getClientByUserID(clientID));
        } catch (Exception e) {
            errorMsg.setText(e.getMessage());
        }
    }

    public void setData(Integer uid) throws SQLException {
        this.clientID = uid;
        ObservableList<Integer> plantsIDS = FXCollections.observableArrayList();
        List<Plant> allPlants = facade.filterPlantsByUserID(uid);
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
        plantLabel.setText(facade.findItemByPlantID(assignedPlantID).getType());
        ObservableList<Integer> cReqsIDS = FXCollections.observableArrayList();
        List<ClientRequest> allCReqs = facade.filterCReqsByPlantID(assignedPlantID);
        for (ClientRequest item:
                allCReqs) {
            cReqsIDS.add(item.getCReqID());
        }
    }

    public void backButtonOnCLicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Starter.showClientView(facade.getClientByUserID(clientID));
    }
}
