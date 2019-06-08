package controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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

    public void finishEditionClicked(MouseEvent mouseEvent) {
    }
}
