package controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class CheckPurchaseController {
    public Label viewLabel;
    public TextField pReqIDField;
    public Label plantIDLabel;
    public TextField plantIDField;
    public Label alreadyBoughtLabel;
    public Label requiredResLabel;
    public ListView alreadyBoughtList;
    public ListView requiredResourcesList;
    public Button approvedPurchase;
    public Button nonApprovedPurchase;
    public Button backButton;

    public void approvedOnClicked(MouseEvent mouseEvent) {
    }

    public void nonApprovedOnClicked(MouseEvent mouseEvent) {
    }

    public void backButtonOnCLicked(MouseEvent mouseEvent) {
        //back to landscaper view
    }
}
