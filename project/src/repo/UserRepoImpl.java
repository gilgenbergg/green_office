package repo;

import model.Admin;
import model.Client;
import model.User;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class UserRepoImpl implements UserRepo {

    private List<User> data = testUsers();
    private PlantRepoImpl plantsBase = new PlantRepoImpl();
    private List<Client> allClients = allClients();
    private List<Admin> allAdmins = allAdmins();

    public UserRepoImpl() throws ParseException {
    }

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

    public List<User> filterByRole(User.Role role) {
        List<User> filtered = new ArrayList<>();
        for (User item:
             data) {
            if (item.getRole().equals(role)) {
                filtered.add(item);
            }
        }
        return filtered;
    }

    public List<Client> allClients() throws ParseException {
        List<User> usersThatAreClients = filterByRole(User.Role.client);
        List<Client> clients = new ArrayList<>();
        for (User item:
                usersThatAreClients) {
            Client client = new Client(plantsBase.filterPlantsByUserID(item.getUID()), item);
            clients.add(client);
        }
        return clients;
    }

    public List<Admin> allAdmins() throws ParseException {
        List<User> usersThatAreAdmins = filterByRole(User.Role.admin);
        List<Admin> admins = new ArrayList<>();
        for (User item:
                usersThatAreAdmins) {
            Admin admin = new Admin(item.getUID(), item);
            admins.add(admin);
        }
        return admins;
    }

    public Client getClientByUserID(Integer uID) throws ParseException {
        Client found = null;
        for (Client item:
                allClients) {
            if (item.getUID().equals(uID)) {
                found = item;
            }
        }
        return found;
    }

    public Admin getAdminByUserID(Integer uID) throws ParseException {
        Admin found = null;
        for (Admin item:
                allAdmins) {
            if (item.getUID().equals(uID)) {
                found = item;
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
