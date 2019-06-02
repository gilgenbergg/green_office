package model;

import data.AuthMapper;
import repo.AuthRepoImpl;

import javax.naming.AuthenticationException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User {
    private static AuthMapper authMapper;

    static {
        try {
            authMapper = new AuthMapper();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Integer uID;
    private Role role;
    private String firstName;
    private String secondName;
    private String login;
    private String password;


    public User(Integer uID, String firstName, String secondName, Role role) {
       this.uID = uID;
       this.role = role;
       this.firstName = firstName;
       this.secondName = secondName;
       this.login = login;
       this.password = password;
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
        return authMapper.findItemByUID(this.uID).getLogin();
    }

    public void setuID(Integer uID) {
        this.uID = uID;
    }

    public String getPassword() throws SQLException {
        return authMapper.findItemByUID(this.uID).getPassword();
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

    private List<User> testUsers() {
        ArrayList<User> allUsers = new ArrayList<>();
        User user1 = new User(
          uID = 1,
          firstName = "Svetlana",
          secondName = "Sagadeeva",
          role = Role.admin
        );
        User user2 = new User(
                uID = 2,
                firstName = "Svetlana",
                secondName = "Sagadeeva",
                role = Role.client
        );
        User user3 = new User(
                uID = 3,
                firstName = "Svetlana",
                secondName = "Sagadeeva",
                role = Role.landscaper
        );
        User user4 = new User(
                uID = 4,
                firstName = "Arina",
                secondName = "Kalinina",
                role = Role.client
        );
        User user5 = new User(
                uID = 5,
                firstName = "Lusien",
                secondName = "Kornilova",
                role = Role.client
        );
        allUsers.add(user1);
        allUsers.add(user2);
        allUsers.add(user3);
        allUsers.add(user4);
        allUsers.add(user5);

        return allUsers;
    }

}

