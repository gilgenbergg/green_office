package model;

import data.AuthMapper;

import java.sql.SQLException;

public class User {
    private static AuthMapper authMapper;

    static {
        try {
            authMapper = new AuthMapper();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Integer uID;
    private Role role;
    private String firstName;
    private String secondName;
    private String login;
    private String password;
    private Integer authDataID;


    public User(Integer uID, String firstName, String secondName, Role role, Integer authDataID) {
       this.uID = uID;
       this.role = role;
       this.firstName = firstName;
       this.secondName = secondName;
       this.authDataID = authDataID;
    }

    public User(Integer uID, String firstName, String secondName, Role role, Integer authDataID, String login, String password) {
        this.uID = uID;
        this.role = role;
        this.firstName = firstName;
        this.secondName = secondName;
        this.login = login;
        this.password = password;
        this.authDataID = authDataID;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public enum Role {
        admin, landscaper, client
    }

    public Integer getUID() {
        return uID;
    }

    public Role getRole() {
        return role;
    }

    public Role parseRole(String roleFromBase) {
        Role parsedRole = null;
        switch (roleFromBase) {
            case "client":
                parsedRole = User.Role.client;
                break;
            case "admin":
                parsedRole = User.Role.admin;
                break;
            case "landscaper":
                parsedRole = User.Role.landscaper;
                break;
        }
        return parsedRole;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getLogin() throws SQLException {
        //return authMapper.findItemByUID(this.uID).getLogin();
        return login;
    }

    public void setuID(Integer uID) {
        this.uID = uID;
    }

    public String getPassword() throws SQLException {
        //return authMapper.findItemByUID(this.uID).getPassword();
        return password;
    }

    public Integer getAuthDataID() {
        return authDataID;
    }

    public void setAuthDataID(Integer authDataID) {
        this.authDataID = authDataID;
    }

    public void setFirstName (String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName (String secondName) {
        this.secondName = secondName;
    }

    public boolean signIn (User user, String receivedLogin, String receivedPassword) throws SQLException {
        Integer baseUID = user.getUID();
        login = authMapper.findItemByUID(baseUID).getLogin();
        password = authMapper.findItemByUID(baseUID).getPassword();
        return correctLogin(login, password, receivedLogin, receivedPassword);
    }

    private boolean correctLogin(String loginInBase, String passwordInBase, String receivedLogin, String receivedPassword) {
        return ((loginInBase.equals(receivedLogin)) && (passwordInBase.equals(receivedPassword)));
    }
}

