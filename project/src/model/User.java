package model;

import repo.AuthRepoImpl;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;

public class User {
    private AuthRepoImpl authrepo = new AuthRepoImpl();

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

    public enum Role {
        admin, landscaper, client
    }

    public Integer getUID() {
        return uID;
    }

    public Role getRole() {
        return role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getLogin() {
        return authrepo.findLoginByUID(this.uID);
    }

    public String getPassword() {
        return authrepo.findPasswordByUID(this.uID);
    }

    public void setFirstName (String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName (String secondName) {
        this.secondName = secondName;
    }

    public boolean signIn (User user, String receivedLogin, String receivedPassword) {
        Integer baseUID = user.getUID();
        login = authrepo.findLoginByUID(baseUID);
        password = authrepo.findPasswordByUID(baseUID);
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

