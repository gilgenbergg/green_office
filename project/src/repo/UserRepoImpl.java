package repo;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepoImpl implements UserRepo {

    private List<User> data = testUsers();

    static List<User> testUsers() {
        Integer uID;
        User.Role role;
        String firstName;
        String secondName;
        String login;
        String password;
        ArrayList<User> allUsers = new ArrayList<>();
        User user1 = new User(
                uID = 1,
                firstName = "Svetlana",
                secondName = "Sagadeeva",
                role = User.Role.admin
        );
        User user2 = new User(
                uID = 2,
                firstName = "Svetlana",
                secondName = "Sagadeeva",
                role = User.Role.client
        );
        User user3 = new User(
                uID = 3,
                firstName = "Svetlana",
                secondName = "Sagadeeva",
                role = User.Role.landscaper
        );
        User user4 = new User(
                uID = 4,
                firstName = "Arina",
                secondName = "Kalinina",
                role = User.Role.client
        );
        User user5 = new User(
                uID = 5,
                firstName = "Lusien",
                secondName = "Kornilova",
                role = User.Role.client
        );
        allUsers.add(user1);
        allUsers.add(user2);
        allUsers.add(user3);
        allUsers.add(user4);
        allUsers.add(user5);
        return allUsers;
    }

    @Override
    public User findItemByUID(Integer receivedUID) {
        User found = null;
        for (int i=0; i<data.size(); i++) {
            if (data.get(i).getUID().equals(receivedUID)) {
                found = data.get(i);
            }
        }
        return found;
    }

    @Override
    public boolean add(User item) {
        return data.add(item);
    }

    @Override
    public void remove(User item) {
        data.remove(item);
    }
}
