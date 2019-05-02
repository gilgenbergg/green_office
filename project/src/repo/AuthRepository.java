package repo;

import model.AuthData;

public interface AuthRepository{

    AuthData findItemByUID(Integer receivedUID);

    String findLoginByUID(Integer receivedUID);

    String findPasswordByUID(Integer receivedUID);

    boolean add(AuthData item);

    void remove(AuthData item);
}
