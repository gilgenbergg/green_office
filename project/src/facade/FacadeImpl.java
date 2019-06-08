package facade;

import data.*;
import model.User;

import java.sql.SQLException;

public class FacadeImpl implements Facade {
    AuthMapper authMapper = new AuthMapper();
    CReqsMapper cReqs = new CReqsMapper();
    InstructionsMapper instructionsMapper = new InstructionsMapper();
    PlantsMapper plantsBase = new PlantsMapper();
    PReqsMapper pReqs = new PReqsMapper();
    ResourcesMapper resourcesMapper = new ResourcesMapper();
    UsersMapper users = new UsersMapper();

    public FacadeImpl() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void authorize(User user, String login, String password) throws Exception {
        user.signIn(user, login, password);
    }

    @Override
    public void register(String login, String password, String firstName, String secondName, String role) throws Exception {
        User.Role parsedRole = users.parseRole(role);
        User user = new User(null, firstName, secondName, parsedRole, null, login, password);
        users.addUser(user);
    }
}
