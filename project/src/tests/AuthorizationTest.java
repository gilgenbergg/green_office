package tests;

import data.*;
import model.AuthData;
import model.User;
import org.junit.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.*;

public class AuthorizationTest {

    private AuthMapper authrepo = new AuthMapper();
    private ResourcesMapper resourcesBase = new ResourcesMapper();
    private PlantsMapper plantsBase = new PlantsMapper(resourcesBase);
    private UsersMapper userRepo = new UsersMapper(authrepo, plantsBase);

    public AuthorizationTest() throws ParseException, SQLException, ClassNotFoundException {}

    //User signing in tests
    @Test
    public void LoginIncorrectTest() throws SQLException, ClassNotFoundException {
        DBinit.initConection();
        User user1 = userRepo.findItemByUID(1);
        boolean res = user1.signIn(user1, "invalidLogin", "Qwerty123");
        boolean expected = false;
        assertEquals(expected, res);
    }

    @Test
    public void PasswordIncorrectTest() throws SQLException, ClassNotFoundException {
        DBinit.initConection();
        User user1 = userRepo.findItemByUID(1);
        boolean res = user1.signIn(user1, "l_admin", "12345");
        boolean expected = false;
        assertEquals(expected, res);
    }

    @Test
    public void BothParamsInvalidTest() throws SQLException, ClassNotFoundException {
        DBinit.initConection();
        User user1 = userRepo.findItemByUID(1);
        boolean res = user1.signIn(user1, "lana", "12345");
        boolean expected = false;
        assertEquals(expected, res);
    }

    @Test
    public void ValidInputTest() throws SQLException, ClassNotFoundException {
        DBinit.initConection();
        User user1 = userRepo.findItemByUID(1);
        boolean res = user1.signIn(user1, "l_admin", "Qwerty123");
        boolean expected = true;
        assertEquals(expected, res);
    }

    //AuthRepository tests
    @Test
    public void FindLoginByUIDTest() throws SQLException, ClassNotFoundException {
        DBinit.initConection();
        Integer UID = 1;
        String res = authrepo.findItemByUID(1).getLogin();
        String expected = "l_admin";
        assertEquals(expected, res);
    }

    @Test
    public void FindPasswordByUIDTest() throws SQLException, ClassNotFoundException {
        DBinit.initConection();
        Integer UID = 1;
        String res = authrepo.findItemByUID(1).getPassword();
        String expected = "Qwerty123";
        assertEquals(expected, res);
    }

    @Test
    public void AllItemsTest() throws SQLException, ClassNotFoundException {
        DBinit.initConection();
        List<AuthData> res = authrepo.allItems();
        assertFalse(res.isEmpty());
    }

    @Test
    public void NewItemTest() throws SQLException, ClassNotFoundException {
        DBinit.initConection();
        Integer id = authrepo.allItems().size();
        AuthData insertion = new AuthData(id, "insertionTest", "blabla");
        boolean res = authrepo.addAuthData(insertion);
        assertTrue(res);
    }
}
