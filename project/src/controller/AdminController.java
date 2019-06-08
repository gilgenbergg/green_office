package controller;

import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class AdminController {
    public Label viewLabel;
    public TextField uidField;
    public Label cReqsLabel;
    public TableView cReqsList;
    public TableColumn CREQS_reqIDCol;
    public TableColumn CREQS_plantIDCol;
    public TableColumn CREQS_typeCol;
    public TableColumn CREQS_statusCol;
    public Button toCReqEditorButton;
    public Label purchasesLabel;
    public TableView pReqsList;
    public TableColumn PREQS_reqIDCol;
    public TableColumn PREQS_cReqID;
    public TableColumn PREQS_plantIDCol;
    public TableColumn PREQS_statusCol;
    public Button newPurchaseButton;

    public void toCreqEditorOnClicked(MouseEvent mouseEvent) {
    }

    public void newPurchaseLOnClicked(MouseEvent mouseEvent) {
    }
}
