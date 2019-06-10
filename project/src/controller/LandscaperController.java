package controller;

import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.Landscaper;
import model.User;

public class LandscaperController {
    public Label viewLabel;
    public TextField uidField;
    public Label cReqsLabel;
    public TableView cReqsTable;
    public TableColumn CREQS_reqIDCol;
    public TableColumn CREQS_plantIDCol;
    public TableColumn CREQS_typeCol;
    public TableColumn CREQS_statusCol;
    public Button gardeningButton;
    public Label pReqsLabel;
    public TableView pReqsTable;
    public TableColumn PREQS_reqIDCol;
    public TableColumn PREQS_pReqIDCol;
    public TableColumn PREQS_plantID_Col;
    public TableColumn PREQS_statusIDCol;
    public Button checkPurchaseButton;

    User user;

    public void gardeningButtonOnClicked(MouseEvent mouseEvent) {
    }

    public void checkPurchaseButtonOnClicked(MouseEvent mouseEvent) {
    }

    public void setData(Landscaper landscaperByUserID) {
        user = landscaperByUserID;
    }
}
