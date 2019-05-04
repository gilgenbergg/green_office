package tests;

import model.AuthData;
import model.User;
import org.junit.Test;
import repo.AuthRepoImpl;
import repo.UserRepoImpl;

import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.*;

public class AuthorizationTest {

    private AuthRepoImpl authrepo = new AuthRepoImpl();
    private UserRepoImpl userRepo = new UserRepoImpl();

    public AuthorizationTest() throws ParseException {}

    //User signing in tests
    @Test
    public void LoginIncorrectTest() {
        User user1 = userRepo.findItemByUID(1);
        boolean res = user1.signIn(user1, "invalidLogin", "Qwerty123");
        boolean expected = false;
        assertEquals(expected, res);
    }

    @Test
    public void PasswordIncorrectTest() {
        User user1 = userRepo.findItemByUID(1);
        boolean res = user1.signIn(user1, "l_admin", "12345");
        boolean expected = false;
        assertEquals(expected, res);
    }

    @Test
    public void BothParamsInvalidTest() {
        User user1 = userRepo.findItemByUID(1);
        boolean res = user1.signIn(user1, "lana", "12345");
        boolean expected = false;
        assertEquals(expected, res);
    }

    @Test
    public void ValidInputTest() {
        User user1 = userRepo.findItemByUID(1);
        boolean res = user1.signIn(user1, "l_admin", "Qwerty123");
        boolean expected = true;
        assertEquals(expected, res);
    }

    //AuthRepository tests
    @Test
    public void FindLoginByUIDTest() {
        Integer UID = 1;
        String res = authrepo.findLoginByUID(1);
        String expected = "l_admin";
        assertEquals(expected, res);
    }

    @Test
    public void FindPasswordByUIDTest() {
        Integer UID = 1;
        String res = authrepo.findPasswordByUID(1);
        String expected = "Qwerty123";
        assertEquals(expected, res);
    }

    @Test
    public void AllItemsTest() {
        List<AuthData> res = authrepo.allItems();
        assertFalse(res.isEmpty());
    }
}
