package facade;

import model.User;

public interface Facade {

    void authorize(User user, String login, String password) throws Exception;
    void register(String login, String password, String firstName, String secondName, String role) throws Exception;

}
