package controller;

import data.CReqsMapper;
import data.UsersMapper;
import facade.Starter;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.ClientRequest;

import java.sql.SQLException;

public class NewCReqController {
    public Label viewLabel;
    public Label typeLabel;
    public Label plantIDLabel;
    public RadioButton isPlannedRadio;
    public RadioButton isFirstRadio;
    public Button createCReqButton;
    public Label errorMsg;
    public TextField plantIDField;

    private String chosenType = "";
    private Integer clientID;

    CReqsMapper cReqsBase = new CReqsMapper();
    UsersMapper users  = new UsersMapper();

    public NewCReqController() throws SQLException, ClassNotFoundException {
    }

    public void createCReqButtonOnClicked(MouseEvent mouseEvent) {
        Integer plantID = null;
        if (!(plantIDField.getText().isEmpty())) {
            plantID = Integer.parseInt(plantIDField.getText());
        }
        ClientRequest.Type type = cReqsBase.parseTypeFromDB(chosenType);
        if (chosenType.isEmpty()) {
            errorMsg.setText("Please choose the type.");
            return;
        }
        if ((chosenType.equals("planned")) && (plantIDField.getText().isEmpty())) {
            errorMsg.setText("For planned requests plantID field must be filled.");
            return;
        }
        try{
           ClientRequest creq = new ClientRequest(null, plantID, this.clientID, null, null, null, type);
            cReqsBase.addCReq(creq);
            Starter.showClientView(users.getClientByUserID(clientID));
        } catch (Exception e) {
            errorMsg.setText(e.getMessage());
        }
    }

    public void setData(Integer uid) {
        this.clientID = uid;
    }

    public void isPlanned(MouseEvent mouseEvent) {
        isFirstRadio.setSelected(false);
        this.chosenType = isPlannedRadio.getText();
    }

    public void isFirst(MouseEvent mouseEvent) {
        isPlannedRadio.setSelected(false);
        this.chosenType = isFirstRadio.getText();
    }
}
