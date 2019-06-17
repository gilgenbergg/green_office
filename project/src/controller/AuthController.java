package controller;

import facade.Facade;
import facade.Starter;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.AuthData;
import model.User;

import java.sql.SQLException;

public class AuthController {

    private Facade facade = Starter.facade;

    public Label welcomeMsg;
    public Label loginLabel;
    public TextField loginField;
    public Label passwordLabel;
    public PasswordField passwordField;
    public Button signInButton;
    public Label registerMsg;
    public Button registerButton;
    public Label errorMsg;

    public AuthController() throws SQLException, ClassNotFoundException {
    }

    public void signInClicked(MouseEvent mouseEvent) throws SQLException {
        String login = loginField.getText();
        String password = passwordField.getText();
        if (loginField.getText().isEmpty()) {
            errorMsg.setText("Please provide the login.");
            return;
        }
        if (passwordField.getText().isEmpty()) {
            errorMsg.setText("Please provide the password.");
            return;
        }
        AuthData searched = facade.findItemByLogin(loginField.getText());
        if (searched == null) {
            errorMsg.setText("No such user. Check login and password.");
            return;
        }
        if (!searched.getPassword().equals(passwordField.getText())) {
            errorMsg.setText("Incorrect input, try again.");
            return;
        }
        try{
                Integer authDataID = searched.getUID();
                User user = facade.findItemByAuthID(authDataID);
                if (user.getRole() == User.Role.client) {
                    Starter.showClientView(facade.getClientByUserID(user.getUID()));
                }
                if (user.getRole() == User.Role.admin) {
                    Starter.showAdminView(facade.getAdminByUserID(user.getUID()));
                }
                if (user.getRole() == User.Role.landscaper) {
                    Starter.showLandscaperView(facade.getLandscaperByUserID(user.getUID()));
                }
                else {
                    errorMsg.setText("error in finding authData");
                }
        } catch (Exception e) {
            System.out.println(errorMsg);
            errorMsg.setText(e.getMessage());
        }
    }

    public void registerClicked(MouseEvent mouseEvent) {
        Starter.showRegisterView();
    }
}
