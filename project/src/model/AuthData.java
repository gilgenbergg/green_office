package model;

public class AuthData {

    private Integer uID;
    private String login;
    private String password;

    public AuthData(Integer uID, String login, String password) {
        this.uID = uID;
        this.login = login;
        this.password = password;
    }

    public Integer getUID() {
        return uID;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
