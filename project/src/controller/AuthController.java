package controller;

import data.AuthMapper;
import data.UsersMapper;
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

    AuthMapper authMapper = new AuthMapper();
    UsersMapper users = new UsersMapper();

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
        AuthData searched = authMapper.findItemByLogin(loginField.getText());
        if (!searched.getPassword().equals(passwordField.getText())) {
            errorMsg.setText("Incorrect input, try again.");
            return;
        }
        try{
                Integer authDataID = searched.getUID();
                User user = users.findItemByAuthID(authDataID);
                if (user.getRole() == User.Role.client) {
                    Starter.showClientView(users.getClientByUserID(user.getUID()));
                }
                if (user.getRole() == User.Role.admin) {
                    Starter.showAdminView(users.getAdminByUserID(user.getUID()));
                }
                if (user.getRole() == User.Role.landscaper) {
                    Starter.showLandscaperView(users.getLandscaperByUserID(user.getUID()));
                }
                else {
                    errorMsg.setText("error in finding authData");
                }
        } catch (Exception e) {
            errorMsg.setText(e.getMessage());
        }
    }

    public void registerClicked(MouseEvent mouseEvent) {
        Starter.showRegisterView();
    }
}
