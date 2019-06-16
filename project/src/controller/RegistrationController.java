package controller;

import facade.Facade;
import facade.Starter;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.AuthData;
import model.User;

import java.sql.SQLException;

public class RegistrationController {

    private Facade facade = Starter.facade;

    public Label mainLabel;
    public Label toFillLabel;
    public Label firstNameLabel;
    public Label secondNameLabel;
    public Label loginLabel;
    public Label passwordLabel;
    public Label roleLabel;
    public RadioButton isClientRadio;
    public RadioButton isAdminRadio;
    public RadioButton isLandscaperRadio;
    public Button registerButton;
    public TextField firstNameField;
    public TextField secondNameField;
    public TextField loginField;
    public TextField passwordField;
    public Label errorMsg;
    public Button backButton;

    private String chosenRole = "";

    public RegistrationController() throws SQLException, ClassNotFoundException {
    }

    public void registerButtonOnClicked(MouseEvent mouseEvent) throws SQLException {
        String firstName = firstNameField.getText();
        String secondName = secondNameField.getText();
        String login = loginField.getText();
        String password = passwordField.getText();
        User.Role role = facade.parseRole(chosenRole);

        if (firstNameField.getText().isEmpty()) {
            errorMsg.setText("Please provide first name.");
            return;
        }
        if (secondNameField.getText().isEmpty()) {
            errorMsg.setText("Please provide second name.");
            return;
        }
        if (loginField.getText().isEmpty()) {
            errorMsg.setText("Please provide a login.");
            return;
        }
        if (passwordField.getText().isEmpty()) {
            errorMsg.setText("Please provide a password.");
            return;
        }
        AuthData searched = facade.findItemByLogin(loginField.getText());
        if ((searched != null) && (searched.getLogin().equals(loginField.getText()))) {
            errorMsg.setText("Login is already used.");
            return;
        }
        if (!isClientRadio.isSelected() && !isAdminRadio.isSelected() && !isLandscaperRadio.isSelected()) {
            errorMsg.setText("Please specify your role.");
            return;
        }
        if (!isClientRadio.isSelected() && !isAdminRadio.isSelected() && !isLandscaperRadio.isSelected()) {
            errorMsg.setText("Please specify your role.");
            return;
        }
        try{
            User user = new User(null, firstName, secondName, role, null, login, password);
            facade.addUser(user);
            Starter.showAuthView();
        } catch (Exception e) {
            errorMsg.setText(e.getMessage());
        }
    }

    public void isClient(MouseEvent mouseEvent) {
        isAdminRadio.setSelected(false);
        isLandscaperRadio.setSelected(false);
        this.chosenRole = isClientRadio.getText();
    }

    public void isAdmin(MouseEvent mouseEvent) {
        isClientRadio.setSelected(false);
        isLandscaperRadio.setSelected(false);
        this.chosenRole = isAdminRadio.getText();
    }

    public void isLandscaper(MouseEvent mouseEvent) {
        isClientRadio.setSelected(false);
        isAdminRadio.setSelected(false);
        this.chosenRole = isLandscaperRadio.getText();
    }

    public void backButtonOnCLicked(MouseEvent mouseEvent) {
        Starter.showAuthView();
    }
}
