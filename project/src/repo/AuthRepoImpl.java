package repo;

import model.AuthData;

import java.util.ArrayList;
import java.util.List;

public class AuthRepoImpl implements AuthRepository {

    private List<AuthData> data = testUsers();

    public AuthData findItemByUID(Integer receivedUID) {
        AuthData found = null;
        for (int i=0; i<data.size(); i++) {
            if (data.get(i).getUID().equals(receivedUID)) {
                found = data.get(i);
            }
        }
        return found;
    }

    public String findLoginByUID(Integer receivedUID) {
        String found = null;
        for (int i=0; i<data.size(); i++) {
            if (data.get(i).getUID().equals(receivedUID)) {
                found = data.get(i).getLogin();
            }
        }
        return found;
    }

    public String findPasswordByUID(Integer receivedUID) {
        String found = null;
        for (int i=0; i<data.size(); i++) {
            if (data.get(i).getUID().equals(receivedUID)) {
                found = data.get(i).getPassword();
            }
        }
        return found;
    }

    @Override
    public boolean add(AuthData item) {
        return data.add(item);
    }

    @Override
    public void remove(AuthData item) {
        data.remove(item);
    }

    private List<AuthData> testUsers() {
        List<AuthData> testData = new ArrayList<>();
        Integer uID;
        String login;
        String password;
        AuthData auth1 = new AuthData(
                uID = 1,
                login = "l_admin",
                password = "Qwerty123"
        );
        testData.add(auth1);
        AuthData auth2 = new AuthData(
                uID = 2,
                login = "l_client",
                password = "Qwerty123"
        );
        testData.add(auth2);
        AuthData auth3 = new AuthData(
                uID = 3,
                login = "l_landscaper",
                password = "Qwerty123"
        );
        testData.add(auth3);
        AuthData auth4 = new AuthData(
                uID = 4,
                login = "fox",
                password = "12345"
        );
        testData.add(auth4);
        AuthData auth5 = new AuthData(
                uID = 5,
                login = "kornilova",
                password = "kornilova"
        );
        testData.add(auth5);
        return testData;
    }
}
