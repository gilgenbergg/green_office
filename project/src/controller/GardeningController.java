package controller;

import facade.Facade;
import facade.Starter;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.ClientRequest;
import model.Plant;

import java.sql.SQLException;

public class GardeningController {
    public Label viewLabel;
    public TextField cReqIDField;
    public Label lastInspectionLabel;
    public Label nextInspectionLabel;
    public TextField lastInspectionField;
    public TextField nextInspectionField;
    public Button doneGardeningButton;
    public Button backButton;
    public Label errorMsg;

    private Facade facade = Starter.facade;

    private Integer landscaperID;
    private Integer creqID;

    public GardeningController() throws SQLException, ClassNotFoundException {
    }

    public void doneButtonOnClicked(MouseEvent mouseEvent) {
        String lastInspection = lastInspectionField.getText();
        String nextInspection = nextInspectionField.getText();
        if (lastInspectionField.getText().isEmpty()) {
            errorMsg.setText("Day of the last inspection is current day.");
            return;
        }
        if (nextInspectionField.getText().isEmpty()) {
            errorMsg.setText("Specify day of tne next inspection.");
            return;
        }
        try{
            Integer assignedPlant = facade.findCReqByID(creqID).getPlantID();
            facade.setDateOfLastVisit(assignedPlant, lastInspection);
            facade.setDateOfNextVisit(assignedPlant, nextInspection);
            Starter.showLandscaperView(facade.getLandscaperByUserID(landscaperID));
        } catch (Exception e) {
            errorMsg.setText(e.getMessage());
        }
    }

    public void backButtonOnCLicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Starter.showLandscaperView(facade.getLandscaperByUserID(landscaperID));
    }

    public void setData(Integer cReqID) throws SQLException {
        creqID = cReqID;
        cReqIDField.setText(cReqID.toString());
        ClientRequest cReq = facade.findCReqByID(cReqID);
        Integer plantID = cReq.getPlantID();
        Plant plant = facade.findItemByPlantID(plantID);
        if (plant.getLastInspection() != null) {
            lastInspectionField.setText(plant.getLastInspection());
        }
        if (plant.getNextInspection() != null) {
            nextInspectionField.setText(plant.getNextInspection());
        }
        landscaperID = cReq.getLandscaperID();
    }
}
