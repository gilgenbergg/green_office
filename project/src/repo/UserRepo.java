package repo;

import model.User;

public interface UserRepo {

    User findItemByUID(Integer receivedUID);

    boolean add(User item);

    void remove(User item);
}
