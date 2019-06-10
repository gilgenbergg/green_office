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

public class ClientReqEditorController {
    public TextField cReqIDField;
    public Label viewLabel;
    public Label typeLabel;
    public RadioButton isPlannedRadio;
    public RadioButton isFirstRadio;
    public Label plantIDLabel;
    public TextField plantIDField;
    public Label landscaperIDLabel;
    public TextField landscaperIDField;
    public Label statusLabel;
    public RadioButton isNewOneRadio;
    public RadioButton isInPurchaseRadio;
    public RadioButton isInProgressRadio;
    public RadioButton isGardeningRadio;
    public RadioButton isDoneRadio;
    public Button finishEditionButton;
    public Label errorMsg;

    private CReqsMapper cReqsBase = new CReqsMapper();
    private UsersMapper usersBase = new UsersMapper();
    String chosenStatus = "";
    String chosenType = "";
    Integer cReqIDSetup;

    public ClientReqEditorController() throws SQLException, ClassNotFoundException {
    }

    public void finishEditionClicked(MouseEvent mouseEvent) throws SQLException {
        if ((landscaperIDField.getText().isEmpty()) || (plantIDField.getText().isEmpty())) {
            errorMsg.setText("Please fill empty fields.");
            return;
        }
        try {
            cReqsBase.updatePlant(Integer.parseInt(plantIDField.getText()), cReqsBase.findItemByID(cReqIDSetup));
            cReqsBase.updateLandscaperID(cReqIDSetup, Integer.parseInt(landscaperIDField.getText()));
            cReqsBase.updateStatus(cReqIDSetup, cReqsBase.parseStatusFromDB(chosenStatus));
            cReqsBase.updateType(cReqIDSetup, cReqsBase.parseTypeFromDB(chosenType));
            Integer adminID = (cReqsBase.findItemByID(cReqIDSetup)).getAdminID();
            Starter.showAdminView(usersBase.getAdminByUserID(adminID));
        } catch (Exception e) {
            errorMsg.setText(e.getMessage());
        }
    }

    public void setData(Integer cReqID) throws SQLException {
        cReqIDSetup = cReqID;
        cReqIDField.setText(cReqID.toString());
        ClientRequest cReq = cReqsBase.findItemByID(cReqID);
        landscaperIDField.setText(cReq.getLandscaperID().toString());
        if (cReq.getType() == ClientRequest.Type.firstOne) {
            isFirstRadio.setSelected(true);
            this.chosenType = isFirstRadio.getText();
        }
        if (cReq.getType() == ClientRequest.Type.planned) {
            isPlannedRadio.setSelected(true);
            this.chosenType = isPlannedRadio.getText();
        }
        if (cReq.getLandscaperID() != null) {
            landscaperIDField.setText(cReq.getLandscaperID().toString());
        }
        if (cReq.getPlantID() != null) {
            plantIDField.setText(cReq.getPlantID().toString());
        }
        if (cReq.getStatus() == ClientRequest.Status.newOne) {
            isNewOneRadio.setSelected(true);
            this.chosenStatus = isNewOneRadio.getText();
        }
        if (cReq.getStatus() == ClientRequest.Status.inProgress) {
            isInProgressRadio.setSelected(true);
            this.chosenStatus = isInProgressRadio.getText();
        }
        if (cReq.getStatus() == ClientRequest.Status.inPurchase) {
            isInPurchaseRadio.setSelected(true);
            this.chosenStatus = isInPurchaseRadio.getText();
        }
        if (cReq.getStatus() == ClientRequest.Status.gardening) {
            isGardeningRadio.setSelected(true);
            this.chosenStatus = isGardeningRadio.getText();
        }
        if (cReq.getStatus() == ClientRequest.Status.done) {
            isDoneRadio.setSelected(true);
            this.chosenStatus = isDoneRadio.getText();
        }
    }

    public void isPlannedClicked(MouseEvent mouseEvent) {
        isPlannedRadio.setSelected(true);
        isFirstRadio.setSelected(false);
        this.chosenType = isPlannedRadio.getText();
    }

    public void isFirstClicked(MouseEvent mouseEvent) {
        isFirstRadio.setSelected(true);
        isPlannedRadio.setSelected(false);
        this.chosenType = isFirstRadio.getText();
    }

    public void isNewOneClicked(MouseEvent mouseEvent) {
        isNewOneRadio.setSelected(true);
        isInProgressRadio.setSelected(false);
        isInPurchaseRadio.setSelected(false);
        isGardeningRadio.setSelected(false);
        isDoneRadio.setSelected(false);
        this.chosenStatus = isNewOneRadio.getText();
    }

    public void isInPurchaseClicked(MouseEvent mouseEvent) {
        isInPurchaseRadio.setSelected(true);
        isInProgressRadio.setSelected(false);
        isNewOneRadio.setSelected(false);
        isGardeningRadio.setSelected(false);
        isDoneRadio.setSelected(false);
        this.chosenStatus = isInPurchaseRadio.getText();
    }

    public void isInProgressClicked(MouseEvent mouseEvent) {
        isInProgressRadio.setSelected(true);
        isNewOneRadio.setSelected(false);
        isInPurchaseRadio.setSelected(false);
        isGardeningRadio.setSelected(false);
        isDoneRadio.setSelected(false);
        this.chosenStatus = isInProgressRadio.getText();
    }

    public void isGardeningClicked(MouseEvent mouseEvent) {
        isGardeningRadio.setSelected(true);
        isInPurchaseRadio.setSelected(false);
        isInProgressRadio.setSelected(false);
        isNewOneRadio.setSelected(false);
        isDoneRadio.setSelected(false);
        this.chosenStatus = isGardeningRadio.getText();
    }

    public void isDoneClicked(MouseEvent mouseEvent) {
        isDoneRadio.setSelected(true);
        isNewOneRadio.setSelected(false);
        isInProgressRadio.setSelected(false);
        isInPurchaseRadio.setSelected(false);
        isGardeningRadio.setSelected(false);
        this.chosenStatus = isDoneRadio.getText();
    }
}
